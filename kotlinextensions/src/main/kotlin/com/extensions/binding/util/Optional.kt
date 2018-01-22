package com.extensions.binding.util

import android.content.res.Resources
import kotlin.reflect.KProperty

class Optional<R, T>(
        private val init: (KProperty<*>) -> T?) : LazyDelegate<R, T?>() {

    init {
        Resources.getSystem().displayMetrics
    }

    override fun initializeValue(ref: R, property: KProperty<*>) = init(property)
}