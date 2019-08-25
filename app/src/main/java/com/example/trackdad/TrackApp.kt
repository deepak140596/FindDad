package com.example.trackdad

import android.app.Application
import android.content.Context
import android.location.LocationManager

class TrackApp : Application(){
    override fun onCreate() {
        super.onCreate()
        globalContext = this
        locationManger = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        JobUtil.scheduleJob(this)
    }
}