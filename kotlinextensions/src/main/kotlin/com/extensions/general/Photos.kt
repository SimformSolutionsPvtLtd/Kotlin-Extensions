package com.extensions.general

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.support.annotation.DrawableRes
import java.io.IOException

private val options: BitmapFactory.Options by lazy {
    val opt = BitmapFactory.Options()
    opt.inJustDecodeBounds = true
    opt
}

@Suppress("unused")
fun String.getImageWidth(): Int {
    BitmapFactory.decodeFile(this, options)
    return options.outWidth
}

@Suppress("unused")
fun String.getImageHeight(): Int {
    BitmapFactory.decodeFile(this, options)
    return options.outHeight
}

@Suppress("unused")
fun String.getImageMimeType(): String {
    BitmapFactory.decodeFile(this, options)
    return options.outMimeType ?: ""
}

@Suppress("unused")
fun Context.getImageWidth(@DrawableRes resId: Int): Int {
    BitmapFactory.decodeResource(this.resources, resId, options)
    return options.outWidth
}

@Suppress("unused")
fun Context.getImageHeight(@DrawableRes resId: Int): Int {
    BitmapFactory.decodeResource(this.resources, resId, options)
    return options.outHeight
}

@Suppress("unused")
fun Context.getImageMimeType(@DrawableRes resId: Int): String {
    BitmapFactory.decodeResource(this.resources, resId, options)
    return options.outMimeType ?: ""
}

@Suppress("unused")
fun getPhotoOrientationDegree(filePath: String?): Int {
    var degree = 0
    var exif: ExifInterface? = null

    if (filePath == null)
        return degree

    try {
        exif = ExifInterface(filePath)
    } catch (e: IOException) {
        println("Error: " + e.message)
    }

    if (exif != null) {
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)
        if (orientation != -1) {
            degree = when (orientation) {
                ExifInterface.ORIENTATION_NORMAL, ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> 0
                ExifInterface.ORIENTATION_ROTATE_180, ExifInterface.ORIENTATION_FLIP_VERTICAL -> 180
                ExifInterface.ORIENTATION_ROTATE_90, ExifInterface.ORIENTATION_TRANSPOSE -> 90
                ExifInterface.ORIENTATION_ROTATE_270, ExifInterface.ORIENTATION_TRANSVERSE -> 270
                else -> 0
            }
        }
    }
    return degree
}

@Suppress("unused")
fun rotate(bitmap: Bitmap, degree: Int): Bitmap {
    val mat = Matrix()
    mat.postRotate(degree.toFloat())
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, mat, true)
}