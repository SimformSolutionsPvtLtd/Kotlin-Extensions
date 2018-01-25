package com.widget

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.MotionEvent

class InterceptTouchCardView :CardView {
    constructor(context :Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defStyleAttr :Int) :super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev :MotionEvent) :Boolean {
        return true
    }
}