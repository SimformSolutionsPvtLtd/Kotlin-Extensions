package com.extensions.validation

import android.databinding.BaseObservable

@Suppress("unused")
class ValidatedObservableField<T> : BaseObservable {
    private var rule: Rule<T>? = null
    private var value: T? = null
    var isValid: Boolean = false
    private var errorMessage: String? = null

    @JvmOverloads
    constructor(value: T, rule: Rule<T>? = null, validate: Boolean = false) {
        this.value = value
        this.rule = rule
        if (validate)
            validate()
    }

    constructor()

    fun setValue(value: T?) {
        if (value != null && value != this.value) {
            this.value = value
            if (!validate())
                notifyChange()
        }
    }

    fun setRule(rule: Rule<T>?) {
        this.rule = rule
    }

    private fun getValue(): T? = value

    fun getErrorMessage(): String? = errorMessage

    fun setErrorMessage(errorMessage: String) {
        if (errorMessage != this.errorMessage) {
            this.errorMessage = errorMessage
            notifyChange()
        }
    }

    private fun validate(): Boolean {
        if (rule != null) {
            isValid = rule!!.isValid(getValue())
            errorMessage = if (isValid) null else rule!!.errorMessage
            notifyChange()
            return true
        }
        return false
    }

    fun hideErrorMessage() {
        errorMessage = null
        notifyChange()
    }
}
