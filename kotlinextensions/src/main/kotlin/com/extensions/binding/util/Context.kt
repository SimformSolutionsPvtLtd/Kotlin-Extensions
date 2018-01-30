package com.extensions.binding.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.AnimRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.support.annotation.StringRes
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.extensions.binding.DimensionType

/**
 * String
 */
fun Context.safeString(@StringRes stringRes :Int) :String? {
    return safe {getString(stringRes)}
}

/**
 * Drawable
 */
fun Context.getThemedDrawable(@DrawableRes drawableRes :Int) :Drawable =
    if(Build.VERSION.SDK_INT >= 23) getDrawable(drawableRes)
    else resources.getDrawable(drawableRes)

fun Context.getSafeThemedDrawable(@DrawableRes drawableRes :Int) :Drawable? {
    return safe {getThemedDrawable(drawableRes)}
}

/**
 * Animation
 */
fun Context.getAnimation(@AnimRes animationRes :Int) :Animation {
    return AnimationUtils.loadAnimation(this, animationRes)
}

fun Context.getSafeAnimation(@AnimRes animationRes :Int) :Animation? {
    return safe {getAnimation(animationRes)}
}

/**
 * Color
 */
@ColorInt
fun Context.getThemedColor(@ColorRes colorRes :Int) :Int =
    if(Build.VERSION.SDK_INT >= 23) getColor(colorRes)
    else resources.getColor(colorRes)

@ColorInt
fun Context.getSafeThemedColor(@ColorRes colorRes :Int) :Int? {
    return safe {getThemedColor(colorRes)}
}

/**
 * Bitmap
 */
fun Context.getBitmap(@DrawableRes drawableRes :Int, options :BitmapFactory.Options? = null) :Bitmap {
    return BitmapFactory.decodeResource(resources, drawableRes, options)
}

fun Context.getSafeBitmap(@DrawableRes drawableRes :Int, options :BitmapFactory.Options? = null) :Bitmap? {
    return try {
        BitmapFactory.decodeResource(resources, drawableRes, options)
    } catch(e :Exception) {
        null
    }
}

/**
 * Integer
 */
fun Context.getInteger(@IntegerRes integerRes :Int) :Int {
    return resources.getInteger(integerRes)
}

fun Context.getSafeInteger(@IntegerRes integerRes :Int) :Int? {
    return safe {getInteger(integerRes)}
}

/**
 * Dimension
 */
fun Context.getDimension(@DimenRes dimenRes :Int, dimensionType :DimensionType = DimensionType.PX) :Float {
    return dimensionType.convert(this, resources.getDimension(dimenRes))
}

fun Context.getSafeDimension(@DimenRes dimenRes :Int, dimensionType :DimensionType = DimensionType.PX) :Float? {
    return safe {getDimension(dimenRes, dimensionType)}
}

private inline fun <reified R> safe(provider :() -> R) :R? {
    return try {
        provider()
    } catch(e :Resources.NotFoundException) {
        null
    }
}