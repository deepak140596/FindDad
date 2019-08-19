package com.example.trackdad

import java.text.SimpleDateFormat
import java.util.*

class DataModel (var timestamp: Long? = 0L,
                 var latitude : Double? = 0.0,
                 var longitude: Double? = 0.0,
                 var batteryPercentage: Int? = 0) {

    var date : String = formatTimestamp(timestamp)
    override fun toString(): String {
        return "Time: $date, Latitude: $latitude, Longitude: $longitude, Battery %: $batteryPercentage "
    }

    private fun formatTimestamp(timestamp: Long?) : String{
        val date = Date(timestamp!!)
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date)
    }
}