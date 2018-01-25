package com.widget.sizeview.button

import android.content.Context
import android.support.annotation.CallSuper
import android.util.AttributeSet
import android.widget.FrameLayout

abstract class CompoundToggleButton :FrameLayout, ToggleButton {
    private var mChecked :Boolean = false
    private var mBroadcasting :Boolean = false
    private var mOnCheckedWidgetListener :OnCheckedChangeListener? = null

    constructor(context :Context) :super(context) {
        isClickable = true
    }

    @JvmOverloads constructor(context :Context, attrs :AttributeSet, defStyleAttr :Int = 0) :super(context, attrs, defStyleAttr) {
        isClickable = true
    }

    override fun performClick() :Boolean {
        toggle()
        return super.performClick()
    }

    override fun setOnCheckedChangeListener(listener :OnCheckedChangeListener) {
        mOnCheckedWidgetListener = listener
    }

    @CallSuper
    override fun setChecked(checked :Boolean) {
        if(mChecked != checked) {
            mChecked = checked

            if(mBroadcasting) {
                return
            }
            mBroadcasting = true
            if(mOnCheckedWidgetListener != null) {
                mOnCheckedWidgetListener!!.onCheckedChanged(this, mChecked)
            }
            mBroadcasting = false
        }
    }

    override fun isChecked() :Boolean {
        return mChecked
    }

    @CallSuper
    override fun toggle() {
        isChecked = !mChecked
    }
}
