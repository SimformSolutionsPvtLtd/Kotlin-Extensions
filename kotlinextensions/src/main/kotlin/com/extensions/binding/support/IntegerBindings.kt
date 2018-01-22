package com.extensions.binding.support

import android.support.annotation.IntegerRes
import com.extensions.binding.optionalInteger
import com.extensions.binding.optionalIntegers
import com.extensions.binding.requiredInteger
import com.extensions.binding.requiredIntegers
import kotlin.properties.ReadOnlyProperty
import android.support.v4.app.Fragment as SupportFragment

/**
 * SupportFragment
 */
fun SupportFragment.integer(@IntegerRes integerRes: Int): ReadOnlyProperty<SupportFragment, Int>
        = requiredInteger(contextProvider, integerRes)

fun SupportFragment.integerOptional(@IntegerRes integerRes: Int): ReadOnlyProperty<SupportFragment, Int?>
        = optionalInteger(contextProvider, integerRes)

fun SupportFragment.integers(@IntegerRes vararg integerRes: Int): ReadOnlyProperty<SupportFragment, List<Int>>
        = requiredIntegers(contextProvider, integerRes)

fun SupportFragment.integersOptional(@IntegerRes vararg integerRes: Int): ReadOnlyProperty<SupportFragment, List<Int?>>
        = optionalIntegers(contextProvider, integerRes)
