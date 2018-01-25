package com.widget.sizeview.button

import android.view.View
import android.widget.Checkable

interface OnCheckedChangeListener {
    fun <T> onCheckedChanged(view :T, isChecked :Boolean) where T :View, T :Checkable
}
