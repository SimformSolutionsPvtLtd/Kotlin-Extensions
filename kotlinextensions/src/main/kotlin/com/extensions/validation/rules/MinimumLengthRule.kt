package com.extensions.validation.rules

@Suppress("unused")
class MinimumLengthRule(private val min: Int, error: String) : AbstractRule<String>(error) {
    override fun isValid(t: String?): Boolean = t!!.length >= min
}
