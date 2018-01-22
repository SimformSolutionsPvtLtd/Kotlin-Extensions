@file:Suppress("UNCHECKED_CAST")

package com.extensions.binding.util

import android.app.Activity
import android.app.Dialog
import android.app.Fragment
import android.view.View
import com.extensions.binding.ViewFinder
import com.extensions.binding.ViewFinderProvider
import kotlin.reflect.KProperty

/**
 * Native
 */
internal fun <V : View> View.viewProvider(property: KProperty<*>): ViewFinder<V>
        = this::findViewById

internal fun <V : View> Activity.viewProvider(property: KProperty<*>): ViewFinder<V>
        = this::findViewById

internal fun <V : View> Fragment.viewProvider(property: KProperty<*>): ViewFinder<V>
        = { ensureFragmentView(this, property).findViewById(it) as V? }

internal fun <V : View> Dialog.viewProvider(property: KProperty<*>): ViewFinder<V>
        = this::findViewById

/**
 * ViewFinderProvider
 */
internal fun <V : View> ViewFinderProvider.genericViewFinder(property: KProperty<*>): ViewFinder<V>
        = { provideViewFinder().invoke(it) as V? }