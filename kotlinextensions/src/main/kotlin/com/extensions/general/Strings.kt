package com.extensions.general

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.webkit.URLUtil
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

fun String.isValidEmail() :Boolean {
    return !matches(Regex("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+"))
    //return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isUrl() :Boolean {
    return URLUtil.isValidUrl(this)
}

fun String.isNumeric() :Boolean {
    try {
        val d = java.lang.Double.parseDouble(this)
    } catch(nfe :NumberFormatException) {
        return false
    }

    return true
}

fun String.isPhoneNumber() :Boolean {
    return android.util.Patterns.PHONE.matcher(this).matches()
}

fun String.random(length :Int = 8) :String {
    val random = Random()
    val base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()-_=+"
    var randomString :String = ""

    for(i in 1..length) {
        val randomValue = (0..base.count()).random()
        randomString += "${base[randomValue]}"
    }
    return randomString
}

fun String.toBitmap() :Bitmap {
    val decoded = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decoded, 0, decoded.count())
}

fun String.ellipsize(at :Int) :String {
    if(this.length > at) {
        return this.substring(0, at) + "..."
    }
    return this
}

fun String.toDate(withFormat :String = "yyyy/MM/dd hh:mm") :Date {
    val dateFormat = SimpleDateFormat(withFormat)
    var convertedDate = Date()
    try {
        convertedDate = dateFormat.parse(this)
    } catch(e :ParseException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }

    return convertedDate
}

fun String.plainText() :String {
    return android.text.Html.fromHtml(this).toString()
}

fun String?.isNull() :Boolean {
    return this == null || this.equals("null", ignoreCase = true) || this.trim {it <= ' '}.isEmpty()
}

fun String?.isNotNull() :Boolean {
    return !isNull()
}

fun String?.isEqual(str2 :String?) :Boolean {
    return isNotNull() && str2.isNotNull() && equals(str2, ignoreCase = true)
}

fun String?.isNotEqual(str2 :String?) :Boolean {
    return isNotNull() && str2.isNotNull() && !equals(str2, ignoreCase = true)
}

fun String.noNumbers() :Boolean {
    return matches(Regex(".*\\d.*"))
}

fun String.onlyNumbers() :Boolean {
    return !matches(Regex("\\d+"))
}

fun String.allUpperCase() :Boolean {
    return toUpperCase() != this
}

fun String.allLowerCase() :Boolean {
    return toLowerCase() != this
}

fun String.atLeastOneLowerCase() :Boolean {
    return matches(Regex("[A-Z0-9]+"))
}

fun String.atLeastOneUpperCase() :Boolean {
    return matches(Regex("[a-z0-9]+"))
}

fun String.atLeastOneNumber() :Boolean {
    return !matches(Regex(".*\\d.*"))
}

fun String.startsWithNonNumber() :Boolean {
    return Character.isDigit(this[0])
}

fun String.noSpecialCharacter() :Boolean {
    return !matches(Regex("[A-Za-z0-9]+"))
}

fun String.atLeastOneSpecialCharacter() :Boolean {
    return matches(Regex("[A-Za-z0-9]+"))
}