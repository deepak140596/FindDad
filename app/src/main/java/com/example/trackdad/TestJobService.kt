package com.example.trackdad

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log

class TestJobService : JobService(){
    val TAG = "TEST_JOB_SERVICE"

    override fun onStartJob(p0: JobParameters?): Boolean {
        getLocation()
        JobUtil.scheduleJob(this)
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }

    @SuppressLint("MissingPermission")
    fun getLocation(){

        Log.d(TAG,"Requesting Location")
        if(!isBatteryReceiverRegistered) {
            globalContext.registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            isBatteryReceiverRegistered = true
        }
        val minTime : Long = remoteConfig.getLong(MINIMUM_TIME_KEY)
        val minDist : Float  = (remoteConfig.getDouble(MINIMUM_DISTANCE_KEY)).toFloat()
        locationManger.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            minTime*1000, /* SECONDS */
            minDist , mLocationListener )

        fetchAndActivateRemoteConfigValues()
        getValues()
    }

    fun getValues(){
        Log.d(TAG,"MIN_LATENCY :${remoteConfig.getLong(MINIMUM_LATENCY_KEY)}")
        Log.d(TAG,"OVERRIDE DEADLINE :${remoteConfig.getLong(OVERRIDE_DEADLINE_KEY)}")
        Log.d(TAG,"MIN_TIME :${remoteConfig.getLong(MINIMUM_TIME_KEY)}")
        Log.d(TAG,"MIN_DISTANCE_KEY :${remoteConfig.getLong(MINIMUM_DISTANCE_KEY)}")

    }


}