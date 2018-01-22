@file:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)

package com.extensions.binding.util

import android.app.Fragment
import android.support.annotation.IdRes
import android.support.annotation.RestrictTo
import android.view.View
import kotlin.reflect.KProperty

fun defaultViewAbsence(component: Any, @IdRes id: Int, property: KProperty<*>): Nothing {
    throw IllegalStateException("${component::class.java.simpleName} doesn't contain view with id $id for property '${property.name}'")
}

fun defaultViewsAbsence(component: Any, @IdRes ids: List<Int>, property: KProperty<*>): Nothing {
    throw IllegalStateException("${component::class.java.simpleName} doesn't contain views with ids $ids for property '${property.name}'")
}

internal fun ensureFragmentView(fragment: Fragment, property: KProperty<*>): View {
    return fragment.view ?: throw IllegalStateException("Fragment hasn't view. Do you access to property '${property.name}' before 'onViewCreated'?")
}