package com.example.trackdad

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationUtils{

    val TAG = "LOCATION_UTILS"
    private var fusedLocationProviderClient: FusedLocationProviderClient?= null

    // using singleton pattern to get the locationProviderClient
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE : LocationUtils? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context

        fun getInstance(appContext: Context): LocationUtils {
            context = appContext
            if(INSTANCE == null){
                INSTANCE = LocationUtils()
            }

            return INSTANCE!!
        }
    }

    init {
        if (fusedLocationProviderClient == null)
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        createLocationRequest()
    }

    @SuppressLint("MissingPermission")
    fun getLocation() : FusedLocationProviderClient? {
        return fusedLocationProviderClient
    }

    private fun createLocationRequest() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 2000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            Log.d(TAG,"Location Settings updated")
        }.addOnFailureListener{
            Log.d(TAG,"Location settings failed: ${it.message}")

        }
    }

}