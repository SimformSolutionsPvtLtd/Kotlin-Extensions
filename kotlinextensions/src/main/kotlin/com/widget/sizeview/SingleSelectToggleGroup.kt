package com.widget.sizeview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import com.widget.sizeview.button.MarkerButton

@Suppress("unused")
open class SingleSelectToggleGroup :ToggleButtonGroup {
    private var mOnCheckedChangeListener :OnCheckedChangeListener? = null
    private var mCheckedId = View.NO_ID
    var checkedId :Int
        get() = mCheckedId
        private set(id) = setCheckedId(id, true)

    constructor(context :Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if(mInitialCheckedId != View.NO_ID) {
            setCheckedStateForView(mInitialCheckedId, true)
            checkedId = mInitialCheckedId
        }
    }

    override fun addView(child :View, index :Int, params :ViewGroup.LayoutParams) {
        if(child is Checkable) {
            val checkable = child as Checkable
            if(checkable.isChecked) {
                if(mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false)
                }
                if(child.id == View.NO_ID) {
                    child.id = generateIdForView(child)
                }
                checkedId = child.id
            }
            (child as? MarkerButton)?.setRadioStyle()
        }

        super.addView(child, index, params)
    }

    override fun <T> onChildCheckedChange(child :T, isChecked :Boolean) where T :View, T :Checkable {
        if(isChecked) {
            if(mCheckedId != View.NO_ID && mCheckedId != child.id) {
                setCheckedStateForView(mCheckedId, false)
            }
            val id = child.id
            checkedId = id
        }
    }

    private fun check(id :Int) {
        if(id == mCheckedId) {
            return
        }
        if(mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false)
        }
        setCheckedStateForView(id, true)
        setCheckedId(id, false)
    }

    fun clearCheck() {
        check(-1)
    }

    fun setOnCheckedChangeListener(listener :OnCheckedChangeListener) {
        mOnCheckedChangeListener = listener
    }

    private fun setCheckedId(id :Int, notify :Boolean) {
        mCheckedId = id
        if(notify) {
            notifyCheckedChange(mCheckedId)
        }
    }

    private fun notifyCheckedChange(id :Int) {
        if(mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener!!.onCheckedChanged(this, id)
        }
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(group :SingleSelectToggleGroup, checkedId :Int)
    }

    companion object {
        private val LOG_TAG = SingleSelectToggleGroup::class.java.simpleName
    }
}
