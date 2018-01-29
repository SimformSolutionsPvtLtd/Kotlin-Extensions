package com.extensions.layout

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import com.extensions.content.layoutInflater

fun Context.inflate(
        @LayoutRes layoutId: Int,
        root: ViewGroup? = null,
        attachToRoot: Boolean = false
): View = layoutInflater.inflate(layoutId, root, attachToRoot)


fun ViewGroup.inflateInto(
        @LayoutRes layoutReId: Int,
        attachToRoot: Boolean = false
): View = context.inflate(layoutReId, this, attachToRoot)


fun ViewGroup.inflateView(@LayoutRes layoutRes: Int): View =
    context.inflate(layoutRes, this, false)


fun ViewGroup.inflateBindView(@LayoutRes layoutRes: Int): ViewDataBinding =
    DataBindingUtil.inflate(context.layoutInflater, layoutRes, this, false)


fun Context.inflateBindView(@LayoutRes layoutRes: Int): ViewDataBinding =
    DataBindingUtil.inflate(layoutInflater, layoutRes, null, false)