package com.extensions.binding

import android.app.Activity
import android.app.Dialog
import android.app.Fragment
import android.content.Context
import android.support.annotation.RestrictTo
import android.support.annotation.StringRes
import android.view.View
import com.extensions.binding.util.Optional
import com.extensions.binding.util.Required
import com.extensions.binding.util.contextProvider
import com.extensions.binding.util.safeString
import kotlin.properties.ReadOnlyProperty

/**
 * View
 */
fun View.string(@StringRes stringRes: Int): ReadOnlyProperty<View, String>
        = requiredString(contextProvider, stringRes)

fun View.stringOptional(@StringRes stringRes: Int): ReadOnlyProperty<View, String?>
        = optionalString(contextProvider, stringRes)

fun View.strings(@StringRes vararg stringRes: Int): ReadOnlyProperty<View, List<String>>
        = requiredStrings(contextProvider, stringRes)

fun View.stringsOptional(@StringRes vararg stringRes: Int): ReadOnlyProperty<View, List<String?>>
        = optionalStrings(contextProvider, stringRes)

/**
 * Activity
 */
fun Activity.string(@StringRes stringRes: Int): ReadOnlyProperty<Activity, String>
        = requiredString(contextProvider, stringRes)

fun Activity.stringOptional(@StringRes stringRes: Int): ReadOnlyProperty<Activity, String?>
        = optionalString(contextProvider, stringRes)

fun Activity.strings(@StringRes vararg stringRes: Int): ReadOnlyProperty<Activity, List<String>>
        = requiredStrings(contextProvider, stringRes)

fun Activity.stringsOptional(@StringRes vararg stringRes: Int): ReadOnlyProperty<Activity, List<String?>>
        = optionalStrings(contextProvider, stringRes)

/**
 * Fragment
 */
fun Fragment.string(@StringRes stringRes: Int): ReadOnlyProperty<Fragment, String>
        = requiredString(contextProvider, stringRes)

fun Fragment.stringOptional(@StringRes stringRes: Int): ReadOnlyProperty<Fragment, String?>
        = optionalString(contextProvider, stringRes)

fun Fragment.strings(@StringRes vararg stringRes: Int): ReadOnlyProperty<Fragment, List<String>>
        = requiredStrings(contextProvider, stringRes)

fun Fragment.stringsOptional(@StringRes vararg stringRes: Int): ReadOnlyProperty<Fragment, List<String?>>
        = optionalStrings(contextProvider, stringRes)

/**
 * Dialog
 */
fun Dialog.string(@StringRes stringRes: Int): ReadOnlyProperty<Dialog, String>
        = requiredString(contextProvider, stringRes)

fun Dialog.stringOptional(@StringRes stringRes: Int): ReadOnlyProperty<Dialog, String?>
        = optionalString(contextProvider, stringRes)

fun Dialog.strings(@StringRes vararg stringRes: Int): ReadOnlyProperty<Dialog, List<String>>
        = requiredStrings(contextProvider, stringRes)

fun Dialog.stringsOptional(@StringRes vararg stringRes: Int): ReadOnlyProperty<Dialog, List<String?>>
        = optionalStrings(contextProvider, stringRes)

/**
 * ContextProvider
 */
fun ContextProvider.string(@StringRes stringRes: Int): ReadOnlyProperty<ContextProvider, String>
        = requiredString(this::provideContext, stringRes)

fun ContextProvider.stringOptional(@StringRes stringRes: Int): ReadOnlyProperty<ContextProvider, String?>
        = optionalString(this::provideContext, stringRes)

fun ContextProvider.strings(@StringRes vararg stringRes: Int): ReadOnlyProperty<ContextProvider, List<String>>
        = requiredStrings(this::provideContext, stringRes)

fun ContextProvider.stringsOptional(@StringRes vararg stringRes: Int): ReadOnlyProperty<ContextProvider, List<String?>>
        = optionalStrings(this::provideContext, stringRes)

/**
 * Getters
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> requiredString(crossinline contextProvider: () -> Context?,
                              @StringRes stringRes: Int): ReadOnlyProperty<R, String> {

    return Required { contextProvider()!!.getString(stringRes) }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> optionalString(crossinline contextProvider: () -> Context?,
                              @StringRes stringRes: Int): ReadOnlyProperty<R, String?> {

    return Optional { contextProvider()!!.safeString(stringRes) }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> requiredStrings(crossinline contextProvider: () -> Context?,
                               @StringRes stringRes: IntArray): ReadOnlyProperty<R, List<String>> {

    return Required {
        val context = contextProvider()!!
        stringRes.map(context::getString)
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
inline fun <R> optionalStrings(crossinline contextProvider: () -> Context?,
                               @StringRes stringRes: IntArray): ReadOnlyProperty<R, List<String?>> {

    return Required {
        val context = contextProvider()!!
        stringRes.map(context::safeString)
    }
}
