package com.example.trackdad

import android.location.Location
import android.os.Bundle
import android.util.Log
import com.google.android.gms.location.*

var batteryLevel: Int? = 100

val locationRequest = LocationRequest.create()?.apply {
    interval = 1000
    fastestInterval = 1000
    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
}


val mLocationListener = object : android.location.LocationListener {
    override fun onLocationChanged(location: Location) {
        val dataModel = DataModel(location.latitude,location.longitude, batteryLevel)
        Log.d("LOCATION_UTILS",dataModel.toString())
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