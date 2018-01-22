package com.extensions.general

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.annotation.StringRes

@Suppress("unused")
fun Context.showMap(@StringRes latitude: String, longitude: String) {
    val location = "http://maps.google.com/maps?q=loc:$latitude,$longitude"
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(location)))
}