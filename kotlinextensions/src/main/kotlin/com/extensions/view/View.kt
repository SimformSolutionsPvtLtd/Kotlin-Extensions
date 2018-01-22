@file:JvmName("ViewUtils")

package com.extensions.view

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView

/**
 * Properties
 */
var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

var View.isInvisible: Boolean
    get() = visibility == View.INVISIBLE
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

var View.backgroundTintStateList: ColorStateList
    get() = ViewCompat.getBackgroundTintList(this)
    set(value) = ViewCompat.setBackgroundTintList(this, value)

var View.axisZ: Float
    get() = ViewCompat.getZ(this)
    set(value) = ViewCompat.setZ(this, value)

var View.translationAxisZ: Float
    get() = ViewCompat.getTranslationZ(this)
    set(value) = ViewCompat.setTranslationZ(this, value)

/**
 * Methods
 */
inline fun <V : View> View.findView(
    @IdRes id: Int,
    init: V.() -> Unit
): V = findViewById<V>(id).apply(init)


fun View.setAllPadding(padding: Int) {
    setPadding(padding, padding, padding, padding)
}

fun View.setOptionalPadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

//region view visibility
fun View.toggleVisibility() {
    visibility = if (isVisible) View.GONE else View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

//endregion view visibility

fun View.onClick(onClick: () -> Unit) = setOnClickListener { onClick() }

fun View.onLongClick(onLongClick: () -> Unit) = setOnLongClickListener {
    onLongClick()
    false
}

fun EditText.onTextChange(onTextChange: (text: CharSequence) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                onTextChange(s ?: "")
    })
}

typealias ActionString = Pair<CharSequence, View.OnClickListener>
typealias ActionResId = Pair<Int, View.OnClickListener>

fun View.snackBar(message: CharSequence, length: Int = Snackbar.LENGTH_LONG, action: ActionString? = null) {
    val snackBar = Snackbar.make(this, message, length)
    action?.let {
        snackBar.setAction(it.first, it.second)
    }
    snackBar.show()
}
fun View.snackBar(@StringRes message: Int, length: Int = Snackbar.LENGTH_LONG, action: ActionResId? = null) {
    val snackBar = Snackbar.make(this, message, length)
    action?.let {
        snackBar.setAction(it.first, it.second)
    }
    snackBar.show()
}

fun View.height(height: Int) {
    layoutParams.height = height
}
fun View.width(width: Int) {
    layoutParams.height = height
}

operator fun TextView.plusAssign(valueToAdd: String) {
    text = "$text$valueToAdd"
}
operator fun TextView.minusAssign(valueToRemove: String) {
    text = text.removePrefix(valueToRemove)
}
operator fun TextView.contains(value: String) = value in text

@Throws(IndexOutOfBoundsException::class)
operator fun TextView.get(index: Int): Char {
    return if (index in 0..text.length) {
        text[index]
    } else {
        throw IndexOutOfBoundsException("""
            Index: $index
            Start: 0
            End: ${text.length}
        """.trimIndent())
    }
}

operator fun TextView.get(char: Char, ignoreCase: Boolean = false) =
        text.toString().indexOf(char, 0, ignoreCase)

fun View.toBitmap(): Bitmap {
    this.isDrawingCacheEnabled = true
    this.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    this.layout(0, 0, this.measuredWidth, this.measuredHeight)
    this.buildDrawingCache(true)

    return this.drawingCache
}

/**
 * Post functions
 */
inline fun <T : View> T.postLet(crossinline block: (T) -> Unit) {
    post { block(this) }
}

inline fun <T : View> T.postDelayedLet(delay: Long, crossinline block: (T) -> Unit) {
    postDelayed({ block(this) }, delay)
}

inline fun <T : View> T.postApply(crossinline block: T.() -> Unit) {
    post { block(this) }
}

inline fun <T : View> T.postDelayedApply(delay: Long, crossinline block: T.() -> Unit) {
    postDelayed({ block(this) }, delay)
}