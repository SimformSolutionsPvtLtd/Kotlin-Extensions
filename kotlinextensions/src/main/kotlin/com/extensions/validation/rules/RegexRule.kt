package com.extensions.validation.rules

@Suppress("unused")
class RegexRule(private val regex: String, error: String) : AbstractRule<String>(error) {
    override fun isValid(t: String?): Boolean = t!!.matches(regex.toRegex())
}
