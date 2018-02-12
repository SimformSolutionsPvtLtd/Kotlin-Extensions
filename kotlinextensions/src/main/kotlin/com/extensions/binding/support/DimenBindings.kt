package com.extensions.binding.support

import android.support.annotation.DimenRes
import com.extensions.binding.DimensionType
import com.extensions.binding.optionalDimen
import com.extensions.binding.optionalDimens
import com.extensions.binding.requiredDimen
import com.extensions.binding.requiredDimens
import kotlin.properties.ReadOnlyProperty
import android.support.v4.app.Fragment as SupportFragment

/**
 * SupportFragment
 */
fun SupportFragment.dimen(
    @DimenRes dimenRes :Int,
    dimension :DimensionType = DimensionType.PX
) :ReadOnlyProperty<SupportFragment, Float> = requiredDimen(contextProvider, dimenRes, dimension)

fun SupportFragment.dimenOptional(
    @DimenRes dimenRes :Int,
    dimension :DimensionType = DimensionType.PX
) :ReadOnlyProperty<SupportFragment, Float?> = optionalDimen(contextProvider, dimenRes, dimension)

fun SupportFragment.dimens(
    @DimenRes vararg dimenRes :Int,
    dimension :DimensionType = DimensionType.PX
) :ReadOnlyProperty<SupportFragment, List<Float>> = requiredDimens(contextProvider, dimenRes, dimension)

fun SupportFragment.dimensOptional(
    @DimenRes vararg dimenRes :Int,
    dimension :DimensionType = DimensionType.PX
) :ReadOnlyProperty<SupportFragment, List<Float?>> = optionalDimens(contextProvider, dimenRes, dimension)
