package com.extensions.lifecycleobserver

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.location.LocationManager
import android.os.IBinder
import com.extensions.content.locationManager
import com.extensions.general.Locations
import com.extensions.general.asDateString
import java.util.*
import android.os.Bundle
import android.os.AsyncTask
import com.extensions.threads.runInBackground

class MyLocationUpdateObserver(var context: Context, var isFusedLocationApi: Boolean, var listener: MyLocationString) : LifecycleObserver {
    lateinit var locationService:Locations
    //var mGoogleApiClient :GoogleApiClient? = null
    var mBound = false

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if(checkIfLocationIsEnabled()) {
            if(isFusedLocationApi) {
                /*runInBackground {
                    if(mGoogleApiClient == null) {
                        mGoogleApiClient = GoogleApiClient.Builder(context)
                            .addConnectionCallbacks(object :GoogleApiClient.ConnectionCallbacks() {
                                fun onConnected(bundle :Bundle) {
                                    getLastLocation(context, locationListener)
                                }

                                fun onConnectionSuspended(i :Int) {
                                }
                            })
                            .addOnConnectionFailedListener({connectionResult ->
                                locationListener.onFailure(Exception(connectionResult.getErrorMessage()))

                                if(mGoogleApiClient != null) {
                                    if(mGoogleApiClient.isConnected())
                                        mGoogleApiClient.disconnect()
                                    mGoogleApiClient = null
                                }
                            })
                            .addApi(LocationServices.API)
                            .build()
                    }
                    if(!mGoogleApiClient.isConnected())
                        mGoogleApiClient.connect()
                }*/
            } else {
                val serviceIntent = Intent(context, Locations::class.java)
                context.startService(serviceIntent)
                context.bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE)
            }
        } else
            listener.locationDisabled()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (mBound) {
            locationService.stopUpdates()
            context.unbindService(mConnection)
            mBound = false
        }
    }

    private fun checkIfLocationIsEnabled() :Boolean =
        (context.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || context.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))


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
        locationService.setLocationCallback({ location: Location ->
            listener.getLocation(
                    "${listener.setLocation()} \n Location changed! -> \n lat: ${location.latitude}\n " +
                            "lng: ${location.longitude}\n provider: ${location.provider}\n " +
                            "time: ${Calendar.getInstance().time.asDateString()}")
        })

        if (locationService.currentBestLocation != null) {
            val location = locationService.currentBestLocation
            listener.getLocation(
                    "${listener.setLocation()}\n Location fetch! -> \n lat: ${location?.latitude}\n " +
                            "lng: ${location?.longitude}\n provider: ${location?.provider}\n " +
                            "time: ${Calendar.getInstance().time.asDateString()}")
        } else {
            listener.getLocation("${listener.setLocation()}\nNot fetched...")
        }
    }

    interface MyLocationString {
        fun getLocation(string: String)
        fun setLocation(): String
        fun locationDisabled()
    }
}