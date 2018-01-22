package com.extensions.binding.support

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.DrawableRes
import com.extensions.binding.optionalBitmap
import com.extensions.binding.optionalBitmaps
import com.extensions.binding.requiredBitmap
import com.extensions.binding.requiredBitmaps
import kotlin.properties.ReadOnlyProperty
import android.support.v4.app.Fragment as SupportFragment

/**
 * SupportFragment
 */
fun SupportFragment.bitmap(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<SupportFragment, Bitmap>
        = requiredBitmap(contextProvider, drawableRes, options)

fun SupportFragment.bitmapOptional(@DrawableRes drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<SupportFragment, Bitmap?>
        = optionalBitmap(contextProvider, drawableRes, options)

fun SupportFragment.bitmaps(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<SupportFragment, List<Bitmap>>
        = requiredBitmaps(contextProvider, drawableRes, options)

fun SupportFragment.bitmapsOptional(@DrawableRes vararg drawableRes: Int, options: BitmapFactory.Options? = null): ReadOnlyProperty<SupportFragment, List<Bitmap?>>
        = optionalBitmaps(contextProvider, drawableRes, options)