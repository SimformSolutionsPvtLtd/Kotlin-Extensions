package com.extensions.binding

import android.app.Activity
import android.app.Dialog
import android.app.Fragment
import android.content.Context
import android.support.annotation.DimenRes
import android.support.annotation.RestrictTo
import android.view.View
import com.extensions.binding.util.*
import kotlin.properties.ReadOnlyProperty

/**
 * View
 */
fun View.dimen(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<View, Float>
        = requiredDimen(contextProvider, dimenRes, dimension)

fun View.dimenOptional(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<View, Float?>
        = optionalDimen(contextProvider, dimenRes, dimension)

fun View.dimens(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<View, List<Float>>
        = requiredDimens(contextProvider, dimenRes, dimension)

fun View.dimensOptional(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<View, List<Float?>>
        = optionalDimens(contextProvider, dimenRes, dimension)

/**
 * Activity
 */
fun Activity.dimen(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Activity, Float>
        = requiredDimen(contextProvider, dimenRes, dimension)

fun Activity.dimenOptional(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Activity, Float?>
        = optionalDimen(contextProvider, dimenRes, dimension)

fun Activity.dimens(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Activity, List<Float>>
        = requiredDimens(contextProvider, dimenRes, dimension)

fun Activity.dimensOptional(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Activity, List<Float?>>
        = optionalDimens(contextProvider, dimenRes, dimension)

/**
 * Fragment
 */
fun Fragment.dimen(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Fragment, Float>
        = requiredDimen(contextProvider, dimenRes, dimension)

fun Fragment.dimenOptional(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Fragment, Float?>
        = optionalDimen(contextProvider, dimenRes, dimension)

fun Fragment.dimens(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Fragment, List<Float>>
        = requiredDimens(contextProvider, dimenRes, dimension)

fun Fragment.dimensOptional(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Fragment, List<Float?>>
        = optionalDimens(contextProvider, dimenRes, dimension)

/**
 * Dialog
 */
fun Dialog.dimen(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Dialog, Float>
        = requiredDimen(contextProvider, dimenRes, dimension)

fun Dialog.dimenOptional(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Dialog, Float?>
        = optionalDimen(contextProvider, dimenRes, dimension)

fun Dialog.dimens(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Dialog, List<Float>>
        = requiredDimens(contextProvider, dimenRes, dimension)

fun Dialog.dimensOptional(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<Dialog, List<Float?>>
        = optionalDimens(contextProvider, dimenRes, dimension)

/**
 * ContextProvider
 */
fun ContextProvider.dimen(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<ContextProvider, Float>
        = requiredDimen(this::provideContext, dimenRes, dimension)

fun ContextProvider.dimenOptional(@DimenRes dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<ContextProvider, Float?>
        = optionalDimen(this::provideContext, dimenRes, dimension)

fun ContextProvider.dimens(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<ContextProvider, List<Float>>
        = requiredDimens(this::provideContext, dimenRes, dimension)

fun ContextProvider.dimensOptional(@DimenRes vararg dimenRes: Int, dimension: DimensionType = DimensionType.PX): ReadOnlyProperty<ContextProvider, List<Float?>>
        = optionalDimens(this::provideContext, dimenRes, dimension)

/**
 * Getters
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> requiredDimen(crossinline contextProvider: () -> Context?,
                             @DimenRes dimenRes: Int,
                             dimension: DimensionType): ReadOnlyProperty<R, Float> {

    return Required { contextProvider()!!.getDimension(dimenRes, dimension) }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> optionalDimen(crossinline contextProvider: () -> Context?,
                             @DimenRes dimenRes: Int,
                             dimension: DimensionType): ReadOnlyProperty<R, Float?> {

    return Optional { contextProvider()!!.getSafeDimension(dimenRes, dimension) }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> requiredDimens(crossinline contextProvider: () -> Context?,
                              @DimenRes dimenRes: IntArray,
                              dimension: DimensionType): ReadOnlyProperty<R, List<Float>> {

    return Required {
        val context = contextProvider()
        dimenRes.map { id -> context!!.getDimension(id, dimension) }
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> optionalDimens(crossinline contextProvider: () -> Context?,
                              @DimenRes dimenRes: IntArray,
                              dimension: DimensionType): ReadOnlyProperty<R, List<Float?>> {

    return Required {
        val context = contextProvider()
        dimenRes.map { id -> context!!.getSafeDimension(id, dimension) }
    }
}
