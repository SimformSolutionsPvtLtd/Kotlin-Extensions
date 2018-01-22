package com.extensions.content.preferences

import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class DatePreferencesProperty(
    private val defaultValue: Long,
    private val key: String?
) : ReadWriteProperty<SharedPreferencesProvider, Date> {

    override fun getValue(thisRef: SharedPreferencesProvider, property: KProperty<*>): Date {
        val key = key ?: property.name
        val date = Date()
        date.time = thisRef.sharedPreferences.getLong(key, defaultValue)
        return date
    }

    override fun setValue(thisRef: SharedPreferencesProvider, property: KProperty<*>, value: Date) {
        val key = key ?: property.name
        thisRef.sharedPreferences.save(key, value.time)
    }
}