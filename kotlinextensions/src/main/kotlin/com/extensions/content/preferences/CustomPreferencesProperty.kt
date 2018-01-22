package com.extensions.content.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class CustomPreferencesProperty<T>(
    defaultValue: T,
    private val key: String?,
    private val getMapper: (String) -> T,
    private val setMapper: (T) -> String = { it.toString() }
) : ReadWriteProperty<SharedPreferencesProvider, T> {

    private val defaultValueRaw: String = setMapper(defaultValue)

    override fun getValue(thisRef: SharedPreferencesProvider, property: KProperty<*>): T {
        val key = property.name
        return getMapper(thisRef.sharedPreferences.getString(key, defaultValueRaw))
    }

    override fun setValue(thisRef: SharedPreferencesProvider, property: KProperty<*>, value: T) {
        val key = property.name
        thisRef.sharedPreferences.save(key, setMapper(value))
    }
}