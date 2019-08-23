package com.example.trackdad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootBroadcastReceiver  : BroadcastReceiver(){
    override fun onReceive(context: Context?, p1: Intent?) {
        context?.startService(Intent(context, LocationService::class.java))
    }
}