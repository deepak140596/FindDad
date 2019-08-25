package com.example.trackdad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

lateinit var locationManger :LocationManager
lateinit var globalContext: Context
var batteryLevel: Int? = -1
var isBatteryReceiverRegistered = false
lateinit var remoteConfig: FirebaseRemoteConfig


val MINIMUM_LATENCY_KEY = "min_latency"
val OVERRIDE_DEADLINE_KEY = "override_deadline"
val MINIMUM_TIME_KEY = "min_location_update_time"
val MINIMUM_DISTANCE_KEY = "min_distance"

val mBatInfoReceiver = object : BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel = level

        if(isBatteryReceiverRegistered) {
            globalContext.unregisterReceiver(this)
            isBatteryReceiverRegistered = false
        }
    }
}


val mLocationListener = object : android.location.LocationListener {
    override fun onLocationChanged(location: Location) {
        val dataModel = DataModel(location.latitude,location.longitude, batteryLevel)
        Log.d("LOCATION_UTILS",dataModel.toString())
        FirebaseUtils.writeDataToFirebase(dataModel)
        locationManger.removeUpdates(this)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        Log.d("","")
     }

    override fun onProviderEnabled(p0: String?) {
        Log.d("","")
    }

    override fun onProviderDisabled(p0: String?) {
        Log.d("","")
    }
}

fun fetchAndActivateRemoteConfigValues(){
    remoteConfig = FirebaseRemoteConfig.getInstance()
    val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setMinimumFetchIntervalInSeconds(7200)
        .build()
    remoteConfig.setConfigSettingsAsync(configSettings)
    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

    remoteConfig.fetchAndActivate()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val updated = task.result
                Log.d("TRACK_DAD", "Config params updated: $updated")

            } else {
                Log.d("TRACK_DAD", "Failed to fetch")
            }
        }
}