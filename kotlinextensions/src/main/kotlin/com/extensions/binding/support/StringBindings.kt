package com.extensions.binding.support

import android.support.annotation.StringRes
import com.extensions.binding.optionalString
import com.extensions.binding.optionalStrings
import com.extensions.binding.requiredString
import com.extensions.binding.requiredStrings
import kotlin.properties.ReadOnlyProperty
import android.support.v4.app.Fragment as SupportFragment

/**
 * SupportFragment
 */
fun SupportFragment.string(@StringRes stringRes: Int): ReadOnlyProperty<SupportFragment, String>
        = requiredString(contextProvider, stringRes)

fun SupportFragment.stringOptional(@StringRes stringRes: Int): ReadOnlyProperty<SupportFragment, String?>
        = optionalString(contextProvider, stringRes)

fun SupportFragment.strings(@StringRes vararg stringRes: Int): ReadOnlyProperty<SupportFragment, List<String>>
        = requiredStrings(contextProvider, stringRes)

fun SupportFragment.stringsOptional(@StringRes vararg stringRes: Int): ReadOnlyProperty<SupportFragment, List<String?>>
        = optionalStrings(contextProvider, stringRes)