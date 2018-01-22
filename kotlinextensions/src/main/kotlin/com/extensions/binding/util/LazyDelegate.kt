package com.extensions.binding.util

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class LazyDelegate<R, T> : ReadOnlyProperty<R, T> {

    private object EMPTY

    private var value: Any? = EMPTY

    @Suppress("unchecked_cast")
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == EMPTY) {
            value = initializeValue(thisRef, property)
        }
        return value as T
    }

    abstract fun initializeValue(ref: R, property: KProperty<*>): T
}