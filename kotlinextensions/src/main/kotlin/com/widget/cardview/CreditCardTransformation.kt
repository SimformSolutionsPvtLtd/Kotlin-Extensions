package com.widget.cardview

import android.text.method.PasswordTransformationMethod
import android.view.View

class CreditCardTransformation : PasswordTransformationMethod() {
    override fun getTransformation(source : CharSequence, view : View) : CharSequence {
        return source
    }

    companion object {
        private var sInstance : CreditCardTransformation? = null
        val instance :CreditCardTransformation
            get() {
                if (sInstance != null)
                    return sInstance as CreditCardTransformation

                sInstance = CreditCardTransformation()
                return sInstance as CreditCardTransformation
            }
    }
}