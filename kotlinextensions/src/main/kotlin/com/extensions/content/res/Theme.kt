@file:JvmName("ThemeUtils")

package com.extensions.content.res

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet

inline fun <R> Resources.Theme.useStyledAttributes(
    set: AttributeSet? = null,
    attrs: IntArray,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    block: (TypedArray) -> R
): R {
    val array = obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)
    var recycled = false
    try {
        return block(array)
    } catch (e: Exception) {
        recycled = true
        try {
            array.recycle()
        } catch (recycleException: Exception) {
        }
        throw e
    } finally {
        if (!recycled) {
            array.recycle()
        }
    }
}

inline fun Resources.Theme.applyStyledAttributes(
    set: AttributeSet? = null,
    attrs: IntArray,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    block: TypedArray.() -> Unit
) {
    val array = obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)
    var recycled = false
    try {
        block(array)
    } catch (e: Exception) {
        recycled = true
        try {
            array.recycle()
        } catch (recycleException: Exception) {
        }
        throw e
    } finally {
        if (!recycled) {
            array.recycle()
        }
    }
}