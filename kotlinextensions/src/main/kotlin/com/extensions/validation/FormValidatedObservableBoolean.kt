package com.extensions.validation

import android.databinding.Observable
import android.databinding.ObservableBoolean

@Suppress("unused","UNCHECKED_CAST", "SENSELESS_COMPARISON")
class FormValidatedObservableBoolean(vararg fields: ValidatedObservableField<*>) : ObservableBoolean() {
    private val fields: Array<ValidatedObservableField<*>>

    init {
        if (fields == null) {
            this.fields = arrayOf()
        } else {
            this.fields = fields as Array<ValidatedObservableField<*>>
        }
        init()
    }

    private fun init() {
        for (field in this.fields) {
            field.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(observable: Observable, i: Int) {
                    set(areAllFieldsValid())
                }
            })
        }

        set(areAllFieldsValid())
    }

    private fun areAllFieldsValid(): Boolean {
        var isValid = fields.isNotEmpty()
        for (field in fields) {
            isValid = isValid and field.isValid
            if (!isValid)
                break
        }
        return isValid
    }
}
