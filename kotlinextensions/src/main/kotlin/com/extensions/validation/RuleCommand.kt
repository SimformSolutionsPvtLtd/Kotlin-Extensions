package com.extensions.validation

import com.extensions.validation.rules.ValidatorRule

@Suppress("unused")
class RuleCommand<in T> private constructor(rules: List<Rule<T>>) : Rule<T> {
    private val rules: MutableList<Rule<T>> = arrayListOf()
    override lateinit var errorMessage: String

    init {
        this.rules.addAll(rules)
    }

    override fun isValid(t: T?): Boolean {
        for (rule in rules) {
            if (!rule.isValid(t)) {
                errorMessage = rule.errorMessage
                return false
            }
        }
        return true
    }

    class Builder<T> {
        private val rules: MutableList<Rule<T>> = arrayListOf()

        fun build(): RuleCommand<T> = RuleCommand(rules)

        fun withRule(rule: Rule<T>): Builder<T> {
            rules.add(rule)
            return this
        }

        fun withRule(validator: Valid<T>, error: String): Builder<T> {
            rules.add(ValidatorRule(validator, error))
            return this
        }
    }
}
