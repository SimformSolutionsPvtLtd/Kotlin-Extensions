package com.extensions.binding.support

import android.support.annotation.ColorRes
import com.extensions.binding.optionalColor
import com.extensions.binding.optionalColors
import com.extensions.binding.requiredColor
import com.extensions.binding.requiredColors
import kotlin.properties.ReadOnlyProperty
import android.support.v4.app.Fragment as SupportFragment

/**
 * SupportFragment
 */
fun SupportFragment.color(@ColorRes colorRes: Int): ReadOnlyProperty<SupportFragment, Int>
        = requiredColor(contextProvider, colorRes)

fun SupportFragment.colorOptional(@ColorRes colorRes: Int): ReadOnlyProperty<SupportFragment, Int?>
        = optionalColor(contextProvider, colorRes)

fun SupportFragment.colors(@ColorRes vararg colorRes: Int): ReadOnlyProperty<SupportFragment, List<Int>>
        = requiredColors(contextProvider, colorRes)

fun SupportFragment.colorsOptional(@ColorRes vararg colorRes: Int): ReadOnlyProperty<SupportFragment, List<Int?>>
        = optionalColors(contextProvider, colorRes)