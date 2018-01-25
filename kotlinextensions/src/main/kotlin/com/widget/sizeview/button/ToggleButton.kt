package com.widget.sizeview.button

import android.widget.Checkable
import com.widget.sizeview.button.OnCheckedChangeListener

interface ToggleButton :Checkable {
    fun setOnCheckedChangeListener(listener :OnCheckedChangeListener)
}
