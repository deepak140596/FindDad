package com.example.trackdad

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import android.os.BatteryManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter


class LocationService : LifecycleService(){

    val TAG = "LOCATION_SERVICE"
    var batteryLevel: Int? = 100

    override fun onCreate() {
        super.onCreate()

        this.registerReceiver(this.mBatInfoReceiver,  IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        getLocation()
    }

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        return Service.START_STICKY
    }

    override fun onDestroy() {
        this.unregisterReceiver(this.mBatInfoReceiver)
        super.onDestroy()
    }

    private fun getLocation(){
        val handler = Handler()
        val runnable = object : Runnable {
            @SuppressLint("MissingPermission")
            override fun run() {
                //Log.d(TAG, "${System.currentTimeMillis()}")
                val location = LocationUtils.getInstance(applicationContext).getLocation()

                location!!.lastLocation.addOnSuccessListener { loc ->
                    var data : DataModel? = null
                    if(loc != null) {
                        data = DataModel(
                            System.currentTimeMillis(),
                            loc.latitude, loc.longitude,
                            batteryLevel
                        )
                    } else {
                        data = DataModel(System.currentTimeMillis(),0.0,0.0,batteryLevel)
                    }

                    Log.d(TAG, data.toString())

                    //FirebaseUtils().writeDataToFirebase(data)
                }

                handler.postDelayed(this,10000)
            }

        }

        handler.postDelayed(runnable,2000)
    }

    private val mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            batteryLevel = level
        }
    }
}