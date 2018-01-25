package com.widget.sizeview

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.CompoundButton
import com.extensions.R
import com.widget.sizeview.button.OnCheckedChangeListener
import com.widget.sizeview.button.ToggleButton

@Suppress("unused")
abstract class ToggleButtonGroup @JvmOverloads constructor(context :Context, attrs :AttributeSet? = null) :FlowLayout(context, attrs) {
    protected var mInitialCheckedId = View.NO_ID
    private var mCheckedStateTracker :OnCheckedChangeListener? = null
    private var mCompoundButtonStateTracker :CompoundButton.OnCheckedChangeListener? = null
    private var mPassThroughListener :PassThroughHierarchyChangeListener? = null
    protected abstract fun <T> onChildCheckedChange(child :T, isChecked :Boolean) where T :View, T :Checkable

    init {
        val a = context.theme.obtainStyledAttributes(attrs,
            R.styleable.ToggleButtonGroup, 0, 0)
        try {
            mInitialCheckedId = a.getResourceId(R.styleable.ToggleButtonGroup_tbgCheckedButton,
                View.NO_ID)
        } finally {
            a.recycle()
        }

        init()
    }

    private fun init() {
        mPassThroughListener = PassThroughHierarchyChangeListener()
        super.setOnHierarchyChangeListener(mPassThroughListener)
    }

    override fun setOnHierarchyChangeListener(listener :ViewGroup.OnHierarchyChangeListener) {
        mPassThroughListener!!.mOnHierarchyChangeListener = listener
    }

    protected fun setCheckedStateForView(viewId :Int, checked :Boolean) {
        val target = findViewById<View>(viewId)
        if(target != null && target is Checkable) {
            (target as Checkable).isChecked = checked
        }
    }

    protected fun toggleCheckedStateForView(viewId :Int) {
        val target = findViewById<View>(viewId)
        if(target != null && target is Checkable) {
            (target as Checkable).toggle()
        }
    }

    private inner class CheckedStateTracker :OnCheckedChangeListener {
        override fun <T> onCheckedChanged(view :T, isChecked :Boolean) where T :View, T :Checkable {
            onChildCheckedChange(view, isChecked)
        }
    }

    private inner class CompoundButtonCheckedStateTracker :CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView :CompoundButton, isChecked :Boolean) {
            onChildCheckedChange(buttonView, isChecked)
        }
    }

    private inner class PassThroughHierarchyChangeListener :ViewGroup.OnHierarchyChangeListener {
        var mOnHierarchyChangeListener :ViewGroup.OnHierarchyChangeListener? = null
        override fun onChildViewAdded(parent :View, child :View) {
            if(parent === this@ToggleButtonGroup && child is Checkable) {
                if(child.id == View.NO_ID) {
                    child.id = generateIdForView(child)
                }
                if(child is ToggleButton) {
                    setStateTracker(child as ToggleButton)
                } else if(child is CompoundButton) {
                    setStateTracker(child)
                }
            }

            mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(parent :View, child :View) {
            if(parent === this@ToggleButtonGroup && child is Checkable) {
                if(child is ToggleButton) {
                    clearStateTracker(child as ToggleButton)
                } else if(child is CompoundButton) {
                    clearStateTracker(child)
                }
            }

            mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }

    private fun setStateTracker(view :ToggleButton) {
        if(mCheckedStateTracker == null) {
            mCheckedStateTracker = CheckedStateTracker()
        }
        view.setOnCheckedChangeListener(mCheckedStateTracker!!)
    }

    @Suppress("UNREACHABLE_CODE")
    private fun clearStateTracker(view :ToggleButton) {
        view.setOnCheckedChangeListener(null!!)
    }

    private fun setStateTracker(view :CompoundButton) {
        if(mCompoundButtonStateTracker == null) {
            mCompoundButtonStateTracker = CompoundButtonCheckedStateTracker()
        }
        view.setOnCheckedChangeListener(mCompoundButtonStateTracker)
    }

    private fun clearStateTracker(view :CompoundButton) {
        view.setOnCheckedChangeListener(null)
    }

    protected fun generateIdForView(view :View) :Int {
        return if(android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1)
            view.hashCode()
        else
            View.generateViewId()
    }
}
