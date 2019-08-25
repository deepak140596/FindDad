package com.example.trackdad

import android.location.Location
import android.os.Bundle
import android.util.Log

var batteryLevel: Int? = 100


val mLocationListener = object : android.location.LocationListener {
    override fun onLocationChanged(location: Location) {
        val dataModel = DataModel(location.latitude,location.longitude, batteryLevel)
        Log.d("LOCATION_UTILS",dataModel.toString())
        FirebaseUtils.writeDataToFirebase(dataModel)
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