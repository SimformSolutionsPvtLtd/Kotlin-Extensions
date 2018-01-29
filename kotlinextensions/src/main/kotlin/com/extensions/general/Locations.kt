package com.extensions.general

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Binder
import android.os.Bundle
import android.view.Surface
import com.extensions.content.locationManager
import com.extensions.content.sensorManager
import com.extensions.content.windowManager
import com.extensions.interfaces.F1
import com.extensions.interfaces.F2
import com.extensions.logging.Logger
import java.io.IOException
import java.util.*

@SuppressLint("MissingPermission", "Registered")
class Locations : Service() {
    private val gpsLocationListener = LocationChangeListener()
    private val networkLocationListener = LocationChangeListener()
    private val sensorEventListener = SensorListener()
    private val localBinder = LocalBinder()

    companion object {
        private val TWO_MINUTES = 1000 * 60 * 2
        private val MIN_BEARING_DIFF = 2.0f
        private val FASTEST_INTERVAL_IN_MS = 1000L
    }

    private var bearing: Float = 0f
    private var axisX: Int = 0
    private var axisY: Int = 0
    var currentBestLocation: Location? = null
    private var locationCallback: LocationCallback? = null

    override fun onBind(intent: Intent?) = localBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        getLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopUpdates()
        sensorManager.unregisterListener(sensorEventListener)
    }

    private fun getLocation() {
        val lastKnownGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val lastKnownNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        var bestLastKnownLocation = currentBestLocation

        if (lastKnownGpsLocation != null && isBetterLocation(lastKnownGpsLocation, bestLastKnownLocation)) {
            bestLastKnownLocation = lastKnownGpsLocation
        }

        if (lastKnownNetworkLocation != null && isBetterLocation(lastKnownNetworkLocation, bestLastKnownLocation)) {
            bestLastKnownLocation = lastKnownNetworkLocation
        }

        currentBestLocation = bestLastKnownLocation

        if (locationManager.allProviders.contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, FASTEST_INTERVAL_IN_MS, 0.0f, gpsLocationListener)
        }

        if (locationManager.allProviders.contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, FASTEST_INTERVAL_IN_MS, 0.0f, networkLocationListener)
        }

        bestLastKnownLocation?.bearing = bearing
        locationCallback?.handleNewLocation(currentBestLocation as Location)

        val mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        sensorManager.registerListener(sensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL * 5)
    }

    fun setLocationCallback(callback: (Location) -> Unit) {
        locationCallback = object : LocationCallback, (Location) -> Unit {
            override fun invoke(location: Location) {
                callback.invoke(location)
            }

            override fun handleNewLocation(location: Location) {
                callback.invoke(location)
            }
        }
    }

    fun setLocationCallback(callback:F1<Location>) {
        locationCallback = object : LocationCallback, (Location) -> Unit {
            override fun invoke(location: Location) {
                callback.invoke(location)
            }

            override fun handleNewLocation(location: Location) {
                callback.invoke(location)
            }
        }
    }

    fun stopUpdates() {
        locationManager.removeUpdates(gpsLocationListener)
        locationManager.removeUpdates(networkLocationListener)
        sensorManager.unregisterListener(sensorEventListener)
    }

    private fun Context.getGeoCoderAddress(): List<Address>? {
        val geoCoder = Geocoder(this, Locale.ENGLISH)
        try {
            return geoCoder.getFromLocation(currentBestLocation?.latitude ?: 0.0, currentBestLocation?.longitude ?: 0.0, 1)
        } catch (e: IOException) {
            Logger.e("Impossible to connect to Geocoder" + e.toString())
        }

        return null
    }

    private fun Context.getFirstAddress(): Address? {
        val addresses = getGeoCoderAddress()
        return if (addresses != null && addresses.isNotEmpty())
            addresses[0]
        else
            null
    }

    fun Context.getAddressLine(): String = getFirstAddress()?.getAddressLine(0) ?: ""

    fun Context.getLocality(): String = getFirstAddress()?.locality ?: ""

    fun Context.getPostalCode(): String = getFirstAddress()?.postalCode ?: ""

    fun Context.getCountryName(): String = getFirstAddress()?.countryName ?: ""

    private fun isBetterLocation(location: Location, currentBestLocation: Location?): Boolean {
        if (currentBestLocation == null) {
            // 이전에 저장한 것이 없다면 새로 사용
            return true
        }

        val timeDelta = location.time - currentBestLocation.time
        val isSignificantlyNewer = timeDelta > TWO_MINUTES // 시간차 2분 이상?
        val isSignificantlyOlder = timeDelta < -TWO_MINUTES // 아니면 더 오래되었는지
        val isNewer = timeDelta > 0 // 신규 위치정보 파악

        // 만일 2분이상 차이난다면 새로운거 사용 (유저가 움직이기 때문)
        if (isSignificantlyNewer) {
            return true
        } else if (isSignificantlyOlder) {
            return false
        }

        // Check whether the new location fix is more or less accurate
        val accuracyDelta = (location.accuracy - currentBestLocation.accuracy).toInt()
        val isLessAccurate = accuracyDelta > 0 // 기존거가 더 정확함
        val isMoreAccurate = accuracyDelta < 0 // 신규가 더 정확함
        val isSignificantlyLessAccurate = accuracyDelta > 200 // 200이상 심각하게 차이남
        val isFromSameProvider = isSameProvider(location.provider, currentBestLocation.provider) // 같은 프로바이더인지

        if (isMoreAccurate) { // 더 정확하면?
            return true
        } else if (isNewer && !isLessAccurate) { // 새로운 데이터이고 신규가 정확하거나 같을때
            return true
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) { // 새로운 데이터이고 너무 차이나지 않고 같은 프로바이더인 경우
            return true
        }
        return false
    }

    private fun isSameProvider(provider1: String?, provider2: String?): Boolean = if (provider1 == null) provider2 == null else provider1 == provider2

    private inner class LocationChangeListener : android.location.LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location == null) {
                return
            }

            if (isBetterLocation(location, currentBestLocation)) {
                currentBestLocation = location
                currentBestLocation?.bearing = bearing
                locationCallback?.handleNewLocation(currentBestLocation as Location)
            }
        }

        override fun onProviderDisabled(provider: String?) {}
        override fun onProviderEnabled(provider: String?) {}
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    }

    private inner class SensorListener : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            if (sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
                Logger.i("Rotation sensor accuracy changed to: " + accuracy)
            }
        }

        override fun onSensorChanged(event: SensorEvent?) {
            val rotationMatrix = FloatArray(16)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event?.values)
            val orientationValues = FloatArray(3)

            readDisplayRotation()

            SensorManager.remapCoordinateSystem(rotationMatrix, axisX, axisY, rotationMatrix)
            SensorManager.getOrientation(rotationMatrix, orientationValues)
            val azimuth = Math.toDegrees(orientationValues[0].toDouble())
            val abs = Math.abs(bearing.minus(azimuth).toFloat()) > MIN_BEARING_DIFF

            if (abs) {
                bearing = azimuth.toFloat()
                currentBestLocation?.bearing = bearing
            }
        }
    }

    private fun readDisplayRotation() {
        axisX = SensorManager.AXIS_X
        axisY = SensorManager.AXIS_Y
        when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_90 -> {
                axisX = SensorManager.AXIS_Y
                axisY = SensorManager.AXIS_MINUS_X
            }
            Surface.ROTATION_180 -> axisY = SensorManager.AXIS_MINUS_Y
            Surface.ROTATION_270 -> {
                axisX = SensorManager.AXIS_MINUS_Y
                axisY = SensorManager.AXIS_X
            }
        }
    }

    inner class LocalBinder : Binder() {
        val service: Locations
            get() = this@Locations
    }

    interface LocationCallback {
        fun handleNewLocation(location: Location)
    }
}