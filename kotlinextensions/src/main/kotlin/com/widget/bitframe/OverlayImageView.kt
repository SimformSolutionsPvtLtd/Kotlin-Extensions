package com.widget.bitframe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView

class OverlayImageView : ImageView {
    private var overlay = Overlay(null)

    constructor(context : Context) : super(context)
    constructor(context : Context, attrs : AttributeSet) : super(context, attrs)

    override fun onDraw(canvas : Canvas) {
        super.onDraw(canvas)
        overlay.draw(canvas)
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        overlay.setDrawableState(drawableState)
    }

    override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        overlay.setDrawableBounds(measuredWidth, measuredHeight)
    }

    override fun onSizeChanged(width : Int, height : Int, oldWidth : Int, oldHeight : Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        overlay.setDrawableBounds(width, height)
    }

    override fun invalidateDrawable(drawable : Drawable) {
        if (drawable === overlay.drawable) {
            invalidate()
        } else {
            super.invalidateDrawable(drawable)
        }
    }

    fun setOverlayDrawable(drawable : Drawable?) {
        if (drawable !== overlay.drawable) {
            overlay.cleanupDrawable(this)
            if (drawable != null) {
                drawable.callback = this
            }

            overlay = Overlay(drawable)
            overlay.setDrawableState(drawableState)
            requestLayout()
        }
    }

    class Overlay internal constructor(internal val drawable : Drawable?) {
        fun cleanupDrawable(imageView : ImageView) {
            if (drawable != null) {
                drawable.callback = null
                imageView.unscheduleDrawable(drawable)
            }
        }

        fun setDrawableBounds(width : Int, height : Int) {
            drawable?.setBounds(0, 0, width, height)
        }

        fun setDrawableState(state : IntArray) {
            if (drawable != null && drawable.isStateful) {
                drawable.state = state
            }
        }

        fun draw(canvas : Canvas) {
            if (drawable != null) {
                canvas.drawColor(Color.BLUE)
                drawable.draw(canvas)
            }
        }
    }
}
