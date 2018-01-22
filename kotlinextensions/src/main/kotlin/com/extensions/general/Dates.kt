package com.extensions.general

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Date.asDateString(format: String? = "yyyy-MM-dd HH:mm:ss"): String = SimpleDateFormat(format, Locale.getDefault()).format(this)

fun Long.asDateString(format: String? = "yyyy-MM-dd HH:mm:ss"): String = Date(this).asDateString(format)

@Suppress("unused")
fun Long.asDate(format: String? = "EEE, MMM dd hh a"): String = SimpleDateFormat(format, Locale.getDefault()).format(this)

@Suppress("unused")
fun String.parseDate(format: String? = "yyyy-MM-dd HH:mm:ss"): Date? = try {
    SimpleDateFormat(format, Locale.getDefault()).parse(this)
} catch (e: Exception) {
    null
}

@Suppress("unused")
fun String.toDateString(fromFormat: String, toFormat: String): String {
    val result: String
    val df = SimpleDateFormat(fromFormat, Locale.getDefault())
    val df2 = SimpleDateFormat(toFormat, Locale.getDefault())
    try {
        result = df2.format(df.parse(this))
    } catch (e: ParseException) {
        return this
    }
    return result
}

@Suppress("unused")
fun Long.toDateString(fromFormat: String, toFormat: String): String {
    val result: String
    val df = SimpleDateFormat(toFormat, Locale.getDefault())
    try {
        result = df.format(this.asDateString(fromFormat))
    } catch (e: ParseException) {
        return ""
    }
    return result
}
