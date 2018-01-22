package com.extensions.validation

@Suppress("unused")
interface Rule<in T> {

    val errorMessage: String

    fun isValid(t: T?): Boolean
}
