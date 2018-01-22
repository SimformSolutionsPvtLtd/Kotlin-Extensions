package com.extensions.content.preferences

import java.util.*
import kotlin.properties.ReadWriteProperty

object PreferencesDelegates {

    fun int(
        defaultValue: Int = 0,
        key: String? = null
    ): ReadWriteProperty<SharedPreferencesProvider, Int> = IntPreferencesProperty(defaultValue, key)

    fun float(
        defaultValue: Float = 0f,
        key: String? = null
    ): ReadWriteProperty<SharedPreferencesProvider, Float> = FloatPreferencesProperty(defaultValue, key)

    fun string(
        defaultValue: String = "",
        key: String? = null
    ): ReadWriteProperty<SharedPreferencesProvider, String> = StringPreferencesProperty(defaultValue, key)

    fun boolean(
        defaultValue: Boolean = false,
        key: String? = null
    ): ReadWriteProperty<SharedPreferencesProvider, Boolean> = BooleanPreferencesProperty(defaultValue, key)

    fun date(
        defaultValue: Long = 0,
        key: String? = null
    ): ReadWriteProperty<SharedPreferencesProvider, Date> = DatePreferencesProperty(defaultValue, key)

    fun calendar(
        defaultValue: Long = 0,
        key: String? = null
    ): ReadWriteProperty<SharedPreferencesProvider, Calendar> = CalendarPreferencesProperty(defaultValue, key)

    fun <T> custom(
        defaultValue: T,
        key: String? = null,
        mapperFrom: (String) -> T
    ): ReadWriteProperty<SharedPreferencesProvider, T> = CustomPreferencesProperty(defaultValue, key, mapperFrom)

    fun <T> custom(
        defaultValue: T,
        key: String? = null,
        mapperFrom: (String) -> T,
        mapperTo: (T) -> String
    ): ReadWriteProperty<SharedPreferencesProvider, T> = CustomPreferencesProperty(defaultValue, key, mapperFrom, mapperTo)
}