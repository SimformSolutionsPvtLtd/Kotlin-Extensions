package com.widget.sizeview.button

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.extensions.R

@Suppress("unused", "DEPRECATION")
abstract class MarkerButton @SuppressLint("ResourceType")
constructor(context :Context, attrs :AttributeSet?) :CompoundToggleButton(context, attrs!!) {
    protected var mTvText :TextView
    protected var mIvBg :ImageView
    open var markerColor :Int = 0
    private var isRadioStyle :Boolean = false
    var text :CharSequence
        get() = mTvText.text
        set(text) {
            mTvText.text = text
        }
    private val textColors :ColorStateList
        get() = mTvText.textColors
    var textSize :Float
        get() = mTvText.textSize
        set(size) {
            mTvText.textSize = size
        }
    var textBackground :Drawable
        get() = mTvText.background
        set(drawable) = mTvText.setBackgroundDrawable(drawable)
    var checkedImageDrawable :Drawable
        get() = mIvBg.drawable
        set(drawable) = mIvBg.setImageDrawable(drawable)
    protected val defaultTextColor :Int
        get() = textColors.defaultColor
    protected val checkedTextColor :Int
        get() = textColors.getColorForState(CHECKED_STATE_SET, defaultTextColor)

    constructor(context :Context) :this(context, null)

    init {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_marker_button, this, true)
        mIvBg = findViewById(R.id.img_bg)
        mTvText = findViewById(R.id.txt_text)
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.MarkerButton, 0, 0)
        try {
            val text = a.getText(R.styleable.MarkerButton_android_text)
            mTvText.text = text
            var colors = a.getColorStateList(R.styleable.MarkerButton_android_textColor)
            if(colors == null) {
                colors = ContextCompat.getColorStateList(context, R.drawable.selector_marker_text)
            }
            mTvText.setTextColor(colors)
            val textSize = a.getDimension(R.styleable.MarkerButton_android_textSize, dpToPx(DEFAULT_TEXT_SIZE_SP.toFloat()))
            mTvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

            markerColor = a.getColor(R.styleable.MarkerButton_tbgMarkerColor, ContextCompat.getColor(getContext(), R.color.default_badge_color))
            isRadioStyle = a.getBoolean(R.styleable.MarkerButton_tbgRadioStyle, false)
        } finally {
            a.recycle()
        }
    }

    override fun toggle() {
        if(isRadioStyle && isChecked) {
            return
        }
        super.toggle()
    }

    fun setRadioStyle() {
        isRadioStyle = true
    }

    open fun setTextColor(color :Int) {
        mTvText.setTextColor(color)
    }

    open fun setTextColor(colors :ColorStateList) {
        mTvText.setTextColor(colors)
    }

    fun setTextSize(unit :Int, size :Float) {
        mTvText.setTextSize(unit, size)
    }

    protected fun dpToPx(dp :Float) :Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    companion object {
        private val DEFAULT_TEXT_SIZE_SP = 14
        protected val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}
