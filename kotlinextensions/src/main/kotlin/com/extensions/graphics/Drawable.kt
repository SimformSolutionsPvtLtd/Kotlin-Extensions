package com.extensions.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

fun Drawable.toBitmap(): Bitmap {
    var bitmap: Bitmap?

    if (this is BitmapDrawable) {
        val bitmapDrawable = this as BitmapDrawable
        if (bitmapDrawable.bitmap != null) {
            return bitmapDrawable.bitmap
        }
    }

    if (this.intrinsicWidth <= 0 || this.intrinsicHeight <= 0) {
        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
    } else {
        bitmap = Bitmap.createBitmap(this.intrinsicWidth, this.intrinsicHeight, Bitmap.Config.ARGB_8888)
    }

    val canvas = Canvas(bitmap)
    this.bounds.set(0, 0, canvas.width, canvas.height)
    this.draw(canvas)
    return bitmap
}


fun Context.getDrawableResId(name: String): Int {
    val resources = resources
    return resources.getIdentifier(name, "drawable", packageName)
}

fun Context.getDrawable(name: String):Drawable? {
    val resources = resources
    val resourceId = resources.getIdentifier(name, "drawable", packageName)
    return ContextCompat.getDrawable(this, resourceId)
}


fun Context.bitmapToDrawable(bitmap: Bitmap?): Drawable? = BitmapDrawable(this.resources, bitmap)