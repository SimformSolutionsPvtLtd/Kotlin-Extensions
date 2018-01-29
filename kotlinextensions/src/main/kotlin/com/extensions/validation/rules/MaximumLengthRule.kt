package com.extensions.validation.rules


class MaximumLengthRule(private val max: Int, error: String) : AbstractRule<String>(error) {
    override fun isValid(t: String?): Boolean = t!!.length <= max
}
