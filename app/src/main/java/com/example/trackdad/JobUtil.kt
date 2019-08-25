package com.example.trackdad

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context

class JobUtil {

    companion object{

        fun scheduleJob(context: Context){

            fetchAndActivateRemoteConfigValues()
            val minLatency = remoteConfig.getLong(MINIMUM_LATENCY_KEY)
            val overrideDeadline = remoteConfig.getLong(OVERRIDE_DEADLINE_KEY)
            val serviceComponent = ComponentName(context,
                TestJobService::class.java)

            val jobInfoBuilder = JobInfo.Builder(0, serviceComponent)
            jobInfoBuilder.setMinimumLatency(minLatency*1000*60)  /* MINUTES */
            jobInfoBuilder.setOverrideDeadline(overrideDeadline*1000*60)  /* MINUTES */

            val jobScheduler = context.getSystemService(JobScheduler::class.java)
            jobScheduler.schedule(jobInfoBuilder.build())
        }
    }
}