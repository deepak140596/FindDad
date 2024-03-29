package com.example.trackdad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class JobServiceStartReceiver  : BroadcastReceiver() {
    override fun onReceive(context : Context?, p1: Intent?) {
        context?.let {
            JobUtil.scheduleJob(context)
        }
    }
}