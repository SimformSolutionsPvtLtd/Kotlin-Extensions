package com.extensions.validation


interface Rule<in T> {

    val errorMessage: String

    fun isValid(t: T?): Boolean
}
