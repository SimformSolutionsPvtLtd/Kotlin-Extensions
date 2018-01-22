package com.extensions.general

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.extensions.BuildConfig
import java.text.DecimalFormat

@Suppress("unused")
inline fun <T, R> T.tryCatch(block :(T) -> R) :R {
    try {
        return block(this)
    } catch(e :Exception) {
        Log.e("tag", "I/O Exception", e)
        throw e
    }
}

@Suppress("unused", "UNCHECKED_CAST")
internal inline fun <R> getValue(block :() -> R, def :Any?) :R =
    try {
        block()
    } catch(e :Exception) {
        def as R
    }

@Suppress("unused", "UNCHECKED_CAST")
internal inline fun <T, R> T.convert(block :(T) -> R, def :Any?) :R =
    try {
        block(this)
    } catch(e :Exception) {
        def as R
    }

@Suppress("unused", "UNCHECKED_CAST")
internal inline fun <T, R> T.convertAcceptNull(block :(T) -> R, def :Any?) :R? =
    try {
        block(this)
    } catch(e :Exception) {
        def as R
    }

@Suppress("unused")
fun toNumFormat(num :String) :String {
    val df = DecimalFormat("#,###")
    return df.format(Integer.parseInt(num))
}

val uiHandler by lazy { Handler(Looper.getMainLooper()) }

fun post(block: () -> Unit) =
        uiHandler.post(Runnable(block))

fun postDelayed(delay: Long, block: () -> Unit) =
        uiHandler.postDelayed(Runnable(block), delay)

inline fun <reified T> Any.ifInstance(block: T.() -> Unit) {
    if (this is T) block(this)
}

inline fun ifDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block.invoke()
    }
}