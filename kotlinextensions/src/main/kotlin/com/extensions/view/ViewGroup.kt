@file:JvmName("ViewGroupUtils")

package com.extensions.view

import android.view.View
import android.view.ViewGroup
import com.extensions.collections.asIterableIndexed

val ViewGroup.views: List<View>
    get() = (0 until childCount).map(this::getChildAt)

infix operator fun ViewGroup.plusAssign(view: View) {
    addView(view)
}

infix operator fun ViewGroup.minusAssign(view: View) {
    removeView(view)
}

operator fun ViewGroup.get(index: Int): View = getChildAt(index)

//region ViewGroup operators
/**
 * [view] = indexOfChild(view)
 */
operator fun ViewGroup.get(view: View) = indexOfChild(view)

/**
 * if (view in views)
 */
operator fun ViewGroup.contains(child: View) = get(child) != -1

/**
 * for (view in views.iterator)
 * @param T Any layout that extends ViewGroup for example LinearLayout
 */
operator fun <T: ViewGroup> T.iterator(): Iterable<View> {
    return asIterableIndexed({ it < childCount }, {
        getChildAt(it)
    }, {
        it.inc()
    })
}
//endregion ViewGroup operators

//region ViewGroup iterations
fun ViewGroup.first() = this[0]

fun ViewGroup.last() = this[childCount]

inline fun ViewGroup.forEach(action: (View) -> Unit) {
    for (i in 0 until childCount) {
        action(getChildAt(i))
    }
}

inline fun ViewGroup.forEachIndexed(action: (Int, View) -> Unit) {
    for (i in 0 until childCount) {
        action(i, getChildAt(i))
    }
}

inline fun ViewGroup.forEachRevered(action: (View) -> Unit) {
    for (i in childCount downTo 0) {
        action(getChildAt(i))
    }
}

inline fun ViewGroup.forEachReveredIndexed(action: (Int, View) -> Unit) {
    for (i in childCount downTo 0) {
        action(i, getChildAt(i))
    }
}
//endregion ViewGroup iterations
