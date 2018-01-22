package com.extensions.validation

fun isNull(str: String?): Boolean {
    return str == null || str.equals("null", ignoreCase = true) || str.trim { it <= ' ' }.isEmpty()
}

fun isNotNull(str: String?): Boolean {
    return !isNull(str)
}

@Suppress("unused")
fun isEqual(str1: String?, str2: String?): Boolean {
    return isNotNull(str1) && isNotNull(str2) && str1.equals(str2, ignoreCase = true)
}

@Suppress("unused")
fun isNotEqual(str1: String?, str2: String?): Boolean {
    return isNotNull(str1) && isNotNull(str2) && !str1.equals(str2, ignoreCase = true)
}

fun email(text: String): Boolean {
    return !text.matches(Regex("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+"))
    //return !android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
}

fun phoneNumber(text: String): Boolean {
    //return !text.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))
    return !android.util.Patterns.PHONE.matcher(text).matches()
}

@Suppress("unused")
fun noNumbers(text: String): Boolean {
    return text.matches(Regex(".*\\d.*"))
}

@Suppress("unused")
fun nonEmpty(text: String): Boolean {
    return text.isEmpty()
}

@Suppress("unused")
fun onlyNumbers(text: String): Boolean {
    return !text.matches(Regex("\\d+"))
}

@Suppress("unused")
fun allUpperCase(text: String): Boolean {
    return text.toUpperCase() != text
}

@Suppress("unused")
fun allLowerCase(text: String): Boolean {
    return text.toLowerCase() != text
}

@Suppress("unused")
fun atLeastOneLowerCase(text: String): Boolean {
    return text.matches(Regex("[A-Z0-9]+"))
}

@Suppress("unused")
fun atLeastOneUpperCase(text: String): Boolean {
    return text.matches(Regex("[a-z0-9]+"))
}

@Suppress("unused")
fun atLeastOneNumber(text: String): Boolean {
    return !text.matches(Regex(".*\\d.*"))
}

@Suppress("unused")
fun startsWithNonNumber(text: String): Boolean {
    return Character.isDigit(text[0])
}

@Suppress("unused")
fun noSpecialCharacter(text: String): Boolean {
    return !text.matches(Regex("[A-Za-z0-9]+"))
}

@Suppress("unused")
fun atLeastOneSpecialCharacter(text: String): Boolean {
    return text.matches(Regex("[A-Za-z0-9]+"))
}

fun contains(text: String, string: String): Boolean {
    return !text.contains(string)
}

@Suppress("unused")
fun doesNotContains(text: String, string: String): Boolean {
    return text.contains(string)
}

@Suppress("unused")
fun startsWith(text: String, string: String): Boolean {
    return !text.startsWith(string)
}

@Suppress("unused")
fun endsWith(text: String, string: String): Boolean {
    return !text.endsWith(string)
}

@Suppress("unused")
fun mobileNumber(phone : String) : Boolean {
    return !android.util.Patterns.PHONE.matcher(phone).matches()
}