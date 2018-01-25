package com.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.design.R
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.AccessibilityDelegateCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v7.app.AppCompatDialog
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout

@Suppress("unused")
class BottomSheetDialog :AppCompatDialog {
    private var mBehavior :BottomSheetBehavior<FrameLayout>? = null
    private var mCancelable = true
    private var mCanceledOnTouchOutside = true
    private var mCanceledOnTouchOutsideSet :Boolean = false
    private val mBottomSheetCallback = object :BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet :View, @BottomSheetBehavior.State newState :Int) {
            if(newState == BottomSheetBehavior.STATE_HIDDEN) {
                cancel()
            }
        }

        override fun onSlide(bottomSheet :View, slideOffset :Float) {}
    }

    @JvmOverloads
    constructor(context :Context, @StyleRes theme :Int = R.style.Theme_Design_Light_BottomSheetDialog) : super(context, theme) { //super(context, getThemeResId(context, theme)) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    constructor(context :Context, cancelable :Boolean, cancelListener :DialogInterface.OnCancelListener) :super(context, cancelable, cancelListener) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        mCancelable = cancelable
    }

    override fun setContentView(@LayoutRes layoutResId :Int) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null))
    }

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        if(window != null) {
            if(Build.VERSION.SDK_INT >= 21) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun setContentView(view :View) {
        super.setContentView(wrapInBottomSheet(0, view, null))
    }

    override fun setContentView(view :View, params :ViewGroup.LayoutParams?) {
        super.setContentView(wrapInBottomSheet(0, view, params))
    }

    override fun setCancelable(cancelable :Boolean) {
        super.setCancelable(cancelable)
        if(mCancelable != cancelable) {
            mCancelable = cancelable
            if(mBehavior != null) {
                mBehavior!!.isHideable = cancelable
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(mBehavior != null) {
            mBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun setCanceledOnTouchOutside(cancel :Boolean) {
        super.setCanceledOnTouchOutside(cancel)
        if(cancel && !mCancelable) {
            mCancelable = true
        }
        mCanceledOnTouchOutside = cancel
        mCanceledOnTouchOutsideSet = true
    }

    @Suppress("NAME_SHADOWING")
    @SuppressLint("PrivateResource", "ClickableViewAccessibility")
    private fun wrapInBottomSheet(layoutResId :Int, view :View?, params :ViewGroup.LayoutParams?) :View {
        var view = view
        val container = View.inflate(context, R.layout.design_bottom_sheet_dialog, null) as FrameLayout
        val coordinator = container.findViewById<View>(R.id.coordinator) as CoordinatorLayout
        if(layoutResId != 0 && view == null) {
            view = layoutInflater.inflate(layoutResId, coordinator, false)
        }
        val bottomSheet = coordinator.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        mBehavior = BottomSheetBehavior.from(bottomSheet)
        mBehavior!!.setBottomSheetCallback(mBottomSheetCallback)
        mBehavior!!.isHideable = mCancelable
        if(params == null) {
            bottomSheet.addView(view)
        } else {
            bottomSheet.addView(view, params)
        }
        coordinator.findViewById<View>(R.id.touch_outside).setOnClickListener {_ ->
            if(mCancelable && isShowing && shouldWindowCloseOnTouchOutside()) {
                if(mBehavior != null) {
                    mBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
                }
                cancel()
            }
        }
        ViewCompat.setAccessibilityDelegate(bottomSheet, object :AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host :View,
                info :AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                if(mCancelable) {
                    info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS)
                    info.isDismissable = true
                } else {
                    info.isDismissable = false
                }
            }

            override fun performAccessibilityAction(host :View, action :Int, args :Bundle) :Boolean {
                if(action == AccessibilityNodeInfoCompat.ACTION_DISMISS && mCancelable) {
                    cancel()
                    return true
                }
                return super.performAccessibilityAction(host, action, args)
            }
        })

        bottomSheet.setOnTouchListener {_, _ -> true}
        return container
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun shouldWindowCloseOnTouchOutside() :Boolean {
        if(!mCanceledOnTouchOutsideSet) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                mCanceledOnTouchOutside = true
            } else {
                val a = context.obtainStyledAttributes(intArrayOf(android.R.attr.windowCloseOnTouchOutside))
                mCanceledOnTouchOutside = a.getBoolean(0, true)
                a.recycle()
            }
            mCanceledOnTouchOutsideSet = true
        }
        return mCanceledOnTouchOutside
    }

    @Suppress("NAME_SHADOWING")
    private fun getThemeResId(context :Context, themeId :Int) :Int {
        var themeId = themeId
        if(themeId == 0) {
            val outValue = TypedValue()
            themeId = if(context.theme.resolveAttribute(R.attr.bottomSheetDialogTheme, outValue, true)) {
                outValue.resourceId
            } else {
                R.style.Theme_Design_Light_BottomSheetDialog
            }
        }
        return themeId
    }
}