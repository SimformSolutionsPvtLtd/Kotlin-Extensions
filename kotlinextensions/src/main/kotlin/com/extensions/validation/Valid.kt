package com.extensions.validation

interface Valid<in T> {
    fun isValid(t: T): Boolean
}

