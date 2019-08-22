package com.example.trackdad

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import android.os.BatteryManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.location.LocationManager
import com.google.android.gms.location.LocationServices


class LocationService : LifecycleService(){

    val TAG = "LOCATION_SERVICE"


    private val mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            batteryLevel = level
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"OnCreate")
        this.registerReceiver(this.mBatInfoReceiver,  IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        getLocation()
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")

        this.unregisterReceiver(this.mBatInfoReceiver)
        sendBroadcast(Intent(this,BroadcastRec::class.java))
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        Log.d(TAG,"onBind")
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG,"onStart")
        this.registerReceiver(this.mBatInfoReceiver,  IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        return Service.START_STICKY
    }

    @SuppressLint("MissingPermission")
    fun getLocation(){
        val locationManger = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManger.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0.0f, mLocationListener )

    }



}