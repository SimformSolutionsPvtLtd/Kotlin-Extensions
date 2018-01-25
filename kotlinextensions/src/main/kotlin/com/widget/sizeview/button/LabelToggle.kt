package com.widget.sizeview.button

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.extensions.R

@Suppress("DEPRECATION")
class LabelToggle @JvmOverloads constructor(context :Context, attrs :AttributeSet? = null) :MarkerButton(context, attrs), ToggleButton {
    private var mCheckAnimation :Animation? = null
    private var mUncheckAnimation :Animation? = null
    private var mTextColorAnimator :ValueAnimator? = null
    override var markerColor :Int
        get() = super.markerColor
        set(markerColor) {
            super.markerColor = markerColor
            initBackground()
        }

    init {
        init()
    }

    override fun setTextColor(color :Int) {
        super.setTextColor(color)
        initAnimation()
    }

    override fun setTextColor(colors :ColorStateList) {
        super.setTextColor(colors)
        initAnimation()
    }

    private fun init() {
        initBackground()
        initText()
        initAnimation()
    }

    private fun initBackground() {
        val checked = GradientDrawable()
        checked.setColor(markerColor)
        checked.cornerRadius = dpToPx(5f)
        checked.setStroke(1, markerColor)
        mIvBg.setImageDrawable(checked)
        val unchecked = GradientDrawable()
        unchecked.setColor(ContextCompat.getColor(context, android.R.color.transparent))
        unchecked.cornerRadius = dpToPx(5f)
        unchecked.setStroke(dpToPx(1f).toInt(), ContextCompat.getColor(context, R.color.default_badge_color))
        mTvText.setBackgroundDrawable(unchecked)
    }

    private fun initText() {
        val padding = dpToPx(16f).toInt()
        mTvText.setPadding(padding, 0, padding, 0)
    }

    private fun initAnimation() {
        val defaultTextColor = defaultTextColor
        val checkedTextColor = checkedTextColor

        mTextColorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), defaultTextColor, checkedTextColor)
        val mAnimationDuration = DEFAULT_ANIMATION_DURATION.toLong()
        mTextColorAnimator!!.duration = mAnimationDuration
        mTextColorAnimator!!.addUpdateListener {valueAnimator -> mTvText.setTextColor(valueAnimator.animatedValue as Int)}

        mCheckAnimation = AlphaAnimation(0f, 1f)
        mCheckAnimation!!.duration = mAnimationDuration
        mCheckAnimation!!.setAnimationListener(object :Animation.AnimationListener {
            override fun onAnimationStart(animation :Animation) {}
            override fun onAnimationEnd(animation :Animation) {
                mTvText.setTextColor(checkedTextColor)
            }

            override fun onAnimationRepeat(animation :Animation) {}
        })

        mUncheckAnimation = AlphaAnimation(1f, 0f)
        mUncheckAnimation!!.duration = mAnimationDuration
        mUncheckAnimation!!.setAnimationListener(object :Animation.AnimationListener {
            override fun onAnimationStart(animation :Animation) {}
            override fun onAnimationEnd(animation :Animation) {
                mIvBg.visibility = View.INVISIBLE
                mTvText.setTextColor(defaultTextColor)
            }

            override fun onAnimationRepeat(animation :Animation) {}
        })
    }

    override fun setChecked(checked :Boolean) {
        super.setChecked(checked)
        if(checked) {
            mIvBg.visibility = View.VISIBLE
            mIvBg.startAnimation(mCheckAnimation)
            mTextColorAnimator!!.start()
        } else {
            mIvBg.visibility = View.VISIBLE
            mIvBg.startAnimation(mUncheckAnimation)
            mTextColorAnimator!!.reverse()
        }
    }

    companion object {
        private val DEFAULT_ANIMATION_DURATION = 150
    }
}
