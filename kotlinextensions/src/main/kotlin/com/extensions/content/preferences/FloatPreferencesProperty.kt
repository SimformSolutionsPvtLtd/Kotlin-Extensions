package com.extensions.content.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FloatPreferencesProperty(
    private val defaultValue: Float,
    private val key: String?
) : ReadWriteProperty<SharedPreferencesProvider, Float> {

    override fun getValue(thisRef: SharedPreferencesProvider, property: KProperty<*>): Float {
        val key = key ?: property.name
        return thisRef.sharedPreferences.getFloat(key, defaultValue)
    }

    override fun setValue(thisRef: SharedPreferencesProvider, property: KProperty<*>, value: Float) {
        val key = key ?: property.name
        thisRef.sharedPreferences.save(key, value)
    }
}