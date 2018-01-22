package com.extensions.content.preferences

import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class CalendarPreferencesProperty(
    private val defaultValue: Long,
    private val key: String?
) : ReadWriteProperty<SharedPreferencesProvider, Calendar> {

    override fun getValue(thisRef: SharedPreferencesProvider, property: KProperty<*>): Calendar {
        val key = key ?: property.name
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = thisRef.sharedPreferences.getLong(key, defaultValue)
        return calendar
    }

    override fun setValue(thisRef: SharedPreferencesProvider, property: KProperty<*>, value: Calendar) {
        val key = key ?: property.name
        thisRef.sharedPreferences.save(key, value.timeInMillis)
    }
}