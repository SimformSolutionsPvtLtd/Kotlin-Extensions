@file:JvmName("TypedArrayUtils")

package com.extensions.content.res

import android.content.res.TypedArray

inline fun <R> TypedArray.use(block: (TypedArray) -> R): R {
    var recycled = false
    try {
        return block(this)
    } catch (e: Exception) {
        recycled = true
        try {
            recycle()
        } catch (recycleException: Exception) {
        }
        throw e
    } finally {
        if (!recycled) {
            recycle()
        }
    }
}