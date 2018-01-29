package com.extensions.validation.rules


class MinimumLengthRule(private val min: Int, error: String) : AbstractRule<String>(error) {
    override fun isValid(t: String?): Boolean = t!!.length >= min
}
