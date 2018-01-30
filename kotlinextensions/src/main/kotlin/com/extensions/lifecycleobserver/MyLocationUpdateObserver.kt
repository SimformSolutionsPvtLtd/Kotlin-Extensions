package com.extensions.lifecycleobserver

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.extensions.general.Locations

class MyLocationUpdateObserver(var context: Context, var isFusedLocationApi: Boolean, var listener: Locations.LocationCallback) : LifecycleObserver {
    lateinit var locationService:Locations
    var mBound = false

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        val serviceIntent = Intent(context, Locations::class.java)
        serviceIntent.putExtra(Locations.ARG_FUSED_LOCATION, isFusedLocationApi)
        context.startService(serviceIntent)
        context.bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (mBound) {
            locationService.stopUpdates()
            context.unbindService(mConnection)
            mBound = false
        }
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as Locations.LocalBinder
            locationService = binder.service
            mBound = true

            if (mBound)
                init()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    private fun init() {
        locationService.setLocationCallback(listener)
    }
}