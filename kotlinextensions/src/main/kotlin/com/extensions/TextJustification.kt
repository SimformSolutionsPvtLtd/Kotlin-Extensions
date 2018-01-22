package com.extensions

import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.widget.TextView
import java.util.concurrent.atomic.AtomicBoolean

object TextJustification {
    fun justify(textView :TextView) {
        val isJustify = AtomicBoolean(false)
        val textString = textView.text.toString()
        val textPaint = textView.paint
        val textViewText = textView.text
        val builder = textViewText as? Spannable ?: SpannableString(textString)

        textView.post {
            if(!isJustify.get()) {
                val lineCount = textView.lineCount
                val textViewWidth = textView.width

                for(i in 0 until lineCount) {
                    val lineStart = textView.layout.getLineStart(i)
                    val lineEnd = textView.layout.getLineEnd(i)
                    val lineString = textString.substring(lineStart, lineEnd)

                    if(i == lineCount - 1) {
                        break
                    }
                    val trimSpaceText = lineString.trim {it <= ' '}
                    val removeSpaceText = lineString.replace(" ".toRegex(), "")
                    val removeSpaceWidth = textPaint.measureText(removeSpaceText)
                    val spaceCount = (trimSpaceText.length - removeSpaceText.length).toFloat()
                    val eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount
                    val endsSpace = spacePositionInEnds(lineString)
                    for(j in 0 until lineString.length) {
                        val c = lineString[j]
                        val drawable = ColorDrawable(0x00ffffff)

                        if(c == ' ') {
                            if(endsSpace.contains(j)) {
                                drawable.setBounds(0, 0, 0, 0)
                            } else {
                                drawable.setBounds(0, 0, eachSpaceWidth.toInt(), 0)
                            }
                            val span = ImageSpan(drawable)
                            builder.setSpan(span, lineStart + j, lineStart + j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                    }
                }

                textView.text = builder
                isJustify.set(true)
            }
        }
    }

    private fun spacePositionInEnds(string :String) :Set<Int> {
        val result = HashSet<Int>()
        for(i in 0 until string.length) {
            val c = string[i]
            if(c == ' ') {
                result.add(i)
            } else {
                break
            }
        }

        if(result.size == string.length) {
            return result
        }

        for(i in string.length - 1 downTo 1) {
            val c = string[i]
            if(c == ' ') {
                result.add(i)
            } else {
                break
            }
        }

        return result
    }
}