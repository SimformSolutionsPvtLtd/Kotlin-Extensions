package com.extensions.binding.support

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import com.extensions.binding.optionalDrawable
import com.extensions.binding.optionalDrawables
import com.extensions.binding.requiredDrawable
import com.extensions.binding.requiredDrawables
import kotlin.properties.ReadOnlyProperty
import android.support.v4.app.Fragment as SupportFragment

/**
 * SupportFragment
 */
fun SupportFragment.drawable(@DrawableRes drawableRes: Int): ReadOnlyProperty<SupportFragment, Drawable>
        = requiredDrawable(contextProvider, drawableRes)

fun SupportFragment.drawableOptional(@DrawableRes drawableRes: Int): ReadOnlyProperty<SupportFragment, Drawable?>
        = optionalDrawable(contextProvider, drawableRes)

fun SupportFragment.drawables(@DrawableRes vararg drawableRes: Int): ReadOnlyProperty<SupportFragment, List<Drawable>>
        = requiredDrawables(contextProvider, drawableRes)

fun SupportFragment.drawablesOptional(@DrawableRes vararg drawableRes: Int): ReadOnlyProperty<SupportFragment, List<Drawable?>>
        = optionalDrawables(contextProvider, drawableRes)

