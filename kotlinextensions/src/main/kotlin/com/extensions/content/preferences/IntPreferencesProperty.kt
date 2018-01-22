package com.extensions.content.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class IntPreferencesProperty(
    private val defaultValue: Int,
    private val key: String?
) : ReadWriteProperty<SharedPreferencesProvider, Int> {

    override fun getValue(thisRef: SharedPreferencesProvider, property: KProperty<*>): Int {
        val key = key ?: property.name
        return thisRef.sharedPreferences.getInt(key, defaultValue)
    }

    override fun setValue(thisRef: SharedPreferencesProvider, property: KProperty<*>, value: Int) {
        val key = key ?: property.name
        thisRef.sharedPreferences.save(key, value)
    }
}