package com.extensions.spanner

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan

@Suppress("unused")
class SpanBuilder :SpannableStringBuilder {
    private var flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

    constructor() :super("")
    constructor(text :CharSequence) :super(text)
    constructor(text :CharSequence, vararg spans :Any) :super(text) {
        for(span in spans) {
            setSpan(span, 0, length)
        }
    }

    constructor(text :CharSequence, span :Any) :super(text) {
        setSpan(span, 0, text.length)
    }

    fun append(text :CharSequence, vararg spans :Any) : SpanBuilder {
        append(text)
        for(span in spans) {
            setSpan(span, length - text.length, length)
        }
        return this
    }

    fun append(text :CharSequence, span :Any) : SpanBuilder {
        append(text)
        setSpan(span, length - text.length, length)
        return this
    }

    @Suppress("NAME_SHADOWING")
    fun append(text :CharSequence, imageSpan :ImageSpan) : SpanBuilder {
        var text = text
        text = "" + text
        append(text)
        setSpan(imageSpan, length - text.length, length - text.length + 1)
        return this
    }

    override fun append(text :CharSequence) : SpanBuilder {
        super.append(text)
        return this
    }

    @Deprecated("")
    fun appendText(text :CharSequence) : SpanBuilder {
        append(text)
        return this
    }

    fun setFlag(flag :Int) {
        this.flag = flag
    }

    private fun setSpan(span :Any, start :Int, end :Int) {
        setSpan(span, start, end, flag)
    }

    fun findAndSpan(textToSpan :CharSequence, getSpan : GetSpan) : SpanBuilder {
        var lastIndex = 0
        while(lastIndex != -1) {
            lastIndex = toString().indexOf(textToSpan.toString(), lastIndex)
            if(lastIndex != -1) {
                setSpan(getSpan.span, lastIndex, lastIndex + textToSpan.length)
                lastIndex += textToSpan.length
            }
        }
        return this
    }

    interface GetSpan {
        /**
         * @return A new span object should be returned.
         */
        val span :Any
    }

    companion object {
        fun spanText(text :CharSequence, vararg spans :Any) :SpannableString {
            val spannableString = SpannableString(text)
            for(span in spans) {
                spannableString.setSpan(span, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            return spannableString
        }

        fun spanText(text :CharSequence, span :Any) :SpannableString {
            val spannableString = SpannableString(text)
            spannableString.setSpan(span, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return spannableString
        }
    }
}