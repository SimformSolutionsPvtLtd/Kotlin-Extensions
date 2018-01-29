package com.extensions.validation.rules

import com.extensions.validation.Valid


class ValidatorRule<in T>(private val validator: Valid<T>, error: String) : AbstractRule<T>(error) {
    override fun isValid(t: T?): Boolean = validator.isValid(t!!)
}
