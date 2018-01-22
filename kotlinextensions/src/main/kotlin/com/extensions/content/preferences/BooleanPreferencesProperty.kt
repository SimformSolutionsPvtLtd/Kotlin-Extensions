package com.extensions.content.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanPreferencesProperty(
    private val defaultValue: Boolean,
    private val key: String?
) : ReadWriteProperty<SharedPreferencesProvider, Boolean> {

    override fun getValue(thisRef: SharedPreferencesProvider, property: KProperty<*>): Boolean {
        val key = key ?: property.name
        return thisRef.sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun setValue(thisRef: SharedPreferencesProvider, property: KProperty<*>, value: Boolean) {
        val key = key ?: property.name
        thisRef.sharedPreferences.save(key, value)
    }
}