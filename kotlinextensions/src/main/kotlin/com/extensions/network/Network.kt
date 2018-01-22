package com.extensions.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.RequiresPermission
import com.extensions.content.connectivityManager

/**
 * Connections
 */
@get:RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
val Context.hasConnection: Boolean
    get() = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false

@get:RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
val Context.isConnectedToWifi: Boolean
    get() = connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI

@get:RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
inline val Context.isConnectedToMobile: Boolean
    get() = connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_MOBILE