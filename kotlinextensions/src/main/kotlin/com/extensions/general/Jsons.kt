package com.extensions.general

import org.json.JSONArray
import org.json.JSONObject

@Suppress("unused")
fun String.createJSONObject(): JSONObject? = this.convertAcceptNull({ JSONObject(this) }, null)

@Suppress("unused")
fun Map<*, *>.createJSONObject(): JSONObject? = this.convertAcceptNull({ JSONObject(this) }, null)

@Suppress("unused")
fun String.createJSONArray(): JSONArray? = this.convertAcceptNull({ JSONArray(this) }, null)

@Suppress("unused")
fun List<*>.createJSONArray(): JSONArray? = this.convertAcceptNull({ JSONArray(this) }, null)

@Suppress("unused")
fun getJSONObject(jsonObject: JSONObject?, name: String): JSONObject? = jsonObject.convertAcceptNull({ it?.getJSONObject(name) }, null)

@Suppress("unused")
fun getJSONObject(jsonArray: JSONArray?, index: Int): JSONObject? = jsonArray.convertAcceptNull({ it?.getJSONObject(index) }, null)

@Suppress("unused")
fun getJSONArray(jsonObject: JSONObject?, name: String): JSONArray? = jsonObject.convertAcceptNull({ it?.getJSONArray(name) }, null)

@Suppress("unused")
fun getJSONArray(jsonArray: JSONArray?, index: Int): JSONArray? = jsonArray.convertAcceptNull({ it?.getJSONArray(index) }, null)

@Suppress("unused")
@JvmOverloads
fun JSONObject.getJSONString(name: String, def: String = ""): String = this.convert({ it.getString(name) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONArray.getJSONString(index: Int, def: String = ""): String = this.convert({ it.getString(index) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONObject.getJSONInt(name: String, def: Int = 0): Int = this.convert({ it.getInt(name) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONArray.getJSONInt(index: Int, def: Int = 0): Int = this.convert({ it.getInt(index) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONObject.getJSONBoolean(name: String, def: Boolean = false): Boolean = this.convert({ it.getBoolean(name) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONArray.getJSONBoolean(index: Int, def: Boolean = false): Boolean = this.convert({ it.getBoolean(index) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONObject.getJSONDouble(name: String, def: Double = 0.toDouble()): Double = this.convert({ it.getDouble(name) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONArray.getJSONDouble(index: Int, def: Double = 0.toDouble()): Double = this.convert({ it.getDouble(index) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONObject.getJSONLong(name: String, def: Long = 0.toLong()): Long = this.convert({ it.getLong(name) }, def)

@Suppress("unused")
@JvmOverloads
fun JSONArray.getJSONLong(index: Int, def: Long = 0.toLong()): Long = this.convert({ it.getLong(index) }, def)

fun put(jsonObject: JSONObject?, name: String, value: Any) = jsonObject?.put(name, value)

fun put(jsonArray: JSONArray?, index: Int, value: Any) = jsonArray?.put(index, value)

fun put(jsonArray: JSONArray?, value: Any) = jsonArray?.put(value)

@JvmName("forObjectEach")
inline fun JSONArray.forEach(action: (JSONObject) -> Unit) {
    for (i in 0 until this.length()) action(getJSONObject(i))
}

@Suppress("unused")
@JvmName("toObjectList")
fun JSONArray.toList(): List<JSONObject> = List<JSONObject>(length()) { index -> getJSONObject(index) }