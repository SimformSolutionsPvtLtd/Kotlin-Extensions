package com.extensions.content.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringPreferencesProperty(
    private val defaultValue: String,
    private val key: String?
) : ReadWriteProperty<SharedPreferencesProvider, String> {

    override fun getValue(thisRef: SharedPreferencesProvider, property: KProperty<*>): String {
        val key = key ?: property.name
        return thisRef.sharedPreferences.getString(key, defaultValue)
    }

    override fun setValue(thisRef: SharedPreferencesProvider, property: KProperty<*>, value: String) {
        val key = key ?: property.name
        thisRef.sharedPreferences.save(key, value)
    }
}