package com.extensions.binding

import android.app.Activity
import android.app.Dialog
import android.app.Fragment
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.DrawableRes
import android.support.annotation.RestrictTo
import android.view.View
import com.extensions.binding.util.*
import kotlin.properties.ReadOnlyProperty

/**
 * View
 */
fun View.bitmap(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<View, Bitmap>
        = requiredBitmap(contextProvider, drawableRes, options)

fun View.bitmapOptional(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<View, Bitmap?>
        = optionalBitmap(contextProvider, drawableRes, options)

fun View.bitmaps(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<View, List<Bitmap>>
        = requiredBitmaps(contextProvider, drawableRes, options)

fun View.bitmapsOptional(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<View, List<Bitmap?>>
        = optionalBitmaps(contextProvider, drawableRes, options)

/**
 * Activity
 */
fun Activity.bitmap(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Activity, Bitmap>
        = requiredBitmap(contextProvider, drawableRes, options)

fun Activity.bitmapOptional(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Activity, Bitmap?>
        = optionalBitmap(contextProvider, drawableRes, options)

fun Activity.bitmaps(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Activity, List<Bitmap>>
        = requiredBitmaps(contextProvider, drawableRes, options)

fun Activity.bitmapsOptional(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Activity, List<Bitmap?>>
        = optionalBitmaps(contextProvider, drawableRes, options)

/**
 * Fragment
 */
fun Fragment.bitmap(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Fragment, Bitmap>
        = requiredBitmap(contextProvider, drawableRes, options)

fun Fragment.bitmapOptional(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Fragment, Bitmap?>
        = optionalBitmap(contextProvider, drawableRes, options)

fun Fragment.bitmaps(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Fragment, List<Bitmap>>
        = requiredBitmaps(contextProvider, drawableRes, options)

fun Fragment.bitmapsOptional(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Fragment, List<Bitmap?>>
        = optionalBitmaps(contextProvider, drawableRes, options)

/**
 * Dialog
 */
fun Dialog.bitmap(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Dialog, Bitmap>
        = requiredBitmap(contextProvider, drawableRes, options)

fun Dialog.bitmapOptional(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Dialog, Bitmap?>
        = optionalBitmap(contextProvider, drawableRes, options)

fun Dialog.bitmaps(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Dialog, List<Bitmap>>
        = requiredBitmaps(contextProvider, drawableRes, options)

fun Dialog.bitmapsOptional(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<Dialog, List<Bitmap?>>
        = optionalBitmaps(contextProvider, drawableRes, options)

/**
 * ContextProvider
 */
fun ContextProvider.bitmap(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<ContextProvider, Bitmap>
        = requiredBitmap(this::provideContext, drawableRes, options)

fun ContextProvider.bitmapOptional(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<ContextProvider, Bitmap?>
        = optionalBitmap(this::provideContext, drawableRes, options)

fun ContextProvider.bitmaps(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<ContextProvider, List<Bitmap>>
        = requiredBitmaps(this::provideContext, drawableRes, options)

fun ContextProvider.bitmapsOptional(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<ContextProvider, List<Bitmap?>>
        = optionalBitmaps(this::provideContext, drawableRes, options)

/**
 * Getters
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> requiredBitmap(
        crossinline contextProvider: () -> Context?,
        @DrawableRes drawableRes: Int,
        options: BitmapFactory.Options?
): ReadOnlyProperty<R, Bitmap> = Required { contextProvider()!!.getBitmap(drawableRes, options) }

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> optionalBitmap(
        crossinline contextProvider: () -> Context?,
        @DrawableRes drawableRes: Int,
        options: BitmapFactory.Options?
): ReadOnlyProperty<R, Bitmap?> = Optional { contextProvider()!!.getSafeBitmap(drawableRes, options) }

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> requiredBitmaps(
        crossinline contextProvider: () -> Context?,
        @DrawableRes drawableRes: IntArray,
        options: BitmapFactory.Options?
): ReadOnlyProperty<R, List<Bitmap>> = Required {
    val context = contextProvider()
    drawableRes.map { id -> context!!.getBitmap(id, options) }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> optionalBitmaps(
        crossinline contextProvider: () -> Context?,
        @DrawableRes drawableRes: IntArray,
        options: BitmapFactory.Options?
): ReadOnlyProperty<R, List<Bitmap?>> = Required {
    val context = contextProvider()
    drawableRes.map { id -> context!!.getSafeBitmap(id, options) }
}
