package com.extensions.general

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.showMap(latitude: String, longitude: String) {
    val location = "http://maps.google.com/maps?q=loc:$latitude,$longitude"
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(location)))
}

fun Context.showMap(latitude: Double, longitude: Double) {
    val location = "http://maps.google.com/maps?q=loc:$latitude,$longitude"
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(location)))
}

fun Context.showNavigationMap(slatitude: String, slongitude: String, dlatitude: String, dlongitude: String) {
    val location = "http://maps.google.com/maps?saddr=$slatitude,$slongitude&daddr=$dlatitude,$dlongitude"
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(location)))
}

fun Context.showNavigationMap(slatitude: Double, slongitude: Double, dlatitude: Double, dlongitude: Double) {
    val location = "http://maps.google.com/maps?saddr=$slatitude,$slongitude&daddr=$dlatitude,$dlongitude"
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(location)))
}