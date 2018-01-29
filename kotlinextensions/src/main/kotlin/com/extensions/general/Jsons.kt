package com.extensions.general

import org.json.JSONArray
import org.json.JSONObject


fun String.createJSONObject(): JSONObject? = this.convertAcceptNull({ JSONObject(this) }, null)


fun Map<*, *>.createJSONObject(): JSONObject? = this.convertAcceptNull({ JSONObject(this) }, null)


fun String.createJSONArray(): JSONArray? = this.convertAcceptNull({ JSONArray(this) }, null)


fun List<*>.createJSONArray(): JSONArray? = this.convertAcceptNull({ JSONArray(this) }, null)


fun getJSONObject(jsonObject: JSONObject?, name: String): JSONObject? = jsonObject.convertAcceptNull({ it?.getJSONObject(name) }, null)


fun getJSONObject(jsonArray: JSONArray?, index: Int): JSONObject? = jsonArray.convertAcceptNull({ it?.getJSONObject(index) }, null)


fun getJSONArray(jsonObject: JSONObject?, name: String): JSONArray? = jsonObject.convertAcceptNull({ it?.getJSONArray(name) }, null)


fun getJSONArray(jsonArray: JSONArray?, index: Int): JSONArray? = jsonArray.convertAcceptNull({ it?.getJSONArray(index) }, null)


@JvmOverloads
fun JSONObject.getJSONString(name: String, def: String = ""): String = this.convert({ it.getString(name) }, def)


@JvmOverloads
fun JSONArray.getJSONString(index: Int, def: String = ""): String = this.convert({ it.getString(index) }, def)


@JvmOverloads
fun JSONObject.getJSONInt(name: String, def: Int = 0): Int = this.convert({ it.getInt(name) }, def)


@JvmOverloads
fun JSONArray.getJSONInt(index: Int, def: Int = 0): Int = this.convert({ it.getInt(index) }, def)


@JvmOverloads
fun JSONObject.getJSONBoolean(name: String, def: Boolean = false): Boolean = this.convert({ it.getBoolean(name) }, def)


@JvmOverloads
fun JSONArray.getJSONBoolean(index: Int, def: Boolean = false): Boolean = this.convert({ it.getBoolean(index) }, def)


@JvmOverloads
fun JSONObject.getJSONDouble(name: String, def: Double = 0.toDouble()): Double = this.convert({ it.getDouble(name) }, def)


@JvmOverloads
fun JSONArray.getJSONDouble(index: Int, def: Double = 0.toDouble()): Double = this.convert({ it.getDouble(index) }, def)


@JvmOverloads
fun JSONObject.getJSONLong(name: String, def: Long = 0.toLong()): Long = this.convert({ it.getLong(name) }, def)


@JvmOverloads
fun JSONArray.getJSONLong(index: Int, def: Long = 0.toLong()): Long = this.convert({ it.getLong(index) }, def)

fun put(jsonObject: JSONObject?, name: String, value: Any) = jsonObject?.put(name, value)

fun put(jsonArray: JSONArray?, index: Int, value: Any) = jsonArray?.put(index, value)

fun put(jsonArray: JSONArray?, value: Any) = jsonArray?.put(value)

@JvmName("forObjectEach")
inline fun JSONArray.forEach(action: (JSONObject) -> Unit) {
    for (i in 0 until this.length()) action(getJSONObject(i))
}


@JvmName("toObjectList")
fun JSONArray.toList(): List<JSONObject> = List<JSONObject>(length()) { index -> getJSONObject(index) }