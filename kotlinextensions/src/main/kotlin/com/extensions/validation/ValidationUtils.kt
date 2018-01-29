package com.extensions.validation

fun isNull(str: String?): Boolean {
    return str == null || str.equals("null", ignoreCase = true) || str.trim { it <= ' ' }.isEmpty()
}

fun isNotNull(str: String?): Boolean {
    return !isNull(str)
}


fun isEqual(str1: String?, str2: String?): Boolean {
    return isNotNull(str1) && isNotNull(str2) && str1.equals(str2, ignoreCase = true)
}


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


fun noNumbers(text: String): Boolean {
    return text.matches(Regex(".*\\d.*"))
}


fun nonEmpty(text: String): Boolean {
    return text.isEmpty()
}


fun onlyNumbers(text: String): Boolean {
    return !text.matches(Regex("\\d+"))
}


fun allUpperCase(text: String): Boolean {
    return text.toUpperCase() != text
}


fun allLowerCase(text: String): Boolean {
    return text.toLowerCase() != text
}


fun atLeastOneLowerCase(text: String): Boolean {
    return text.matches(Regex("[A-Z0-9]+"))
}


fun atLeastOneUpperCase(text: String): Boolean {
    return text.matches(Regex("[a-z0-9]+"))
}


fun atLeastOneNumber(text: String): Boolean {
    return !text.matches(Regex(".*\\d.*"))
}


fun startsWithNonNumber(text: String): Boolean {
    return Character.isDigit(text[0])
}


fun noSpecialCharacter(text: String): Boolean {
    return !text.matches(Regex("[A-Za-z0-9]+"))
}


fun atLeastOneSpecialCharacter(text: String): Boolean {
    return text.matches(Regex("[A-Za-z0-9]+"))
}

fun contains(text: String, string: String): Boolean {
    return !text.contains(string)
}


fun doesNotContains(text: String, string: String): Boolean {
    return text.contains(string)
}


fun startsWith(text: String, string: String): Boolean {
    return !text.startsWith(string)
}


fun endsWith(text: String, string: String): Boolean {
    return !text.endsWith(string)
}


fun mobileNumber(phone : String) : Boolean {
    return !android.util.Patterns.PHONE.matcher(phone).matches()
}