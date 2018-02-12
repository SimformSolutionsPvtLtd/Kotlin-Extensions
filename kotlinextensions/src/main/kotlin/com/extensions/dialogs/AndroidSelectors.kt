package com.extensions.dialogs

import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment as SupportFragment

inline fun SupportFragment.selector(
        title: CharSequence? = null,
        items: List<CharSequence>,
        noinline onClick: (DialogInterface, Int) -> Unit
) = context!!.selector(title, items, onClick)

inline fun Fragment.selector(
        title: CharSequence? = null,
        items: List<CharSequence>,
        noinline onClick: (DialogInterface, Int) -> Unit
) = activity.selector(title, items, onClick)

fun Context.selector(
        title: CharSequence? = null,
        items: List<CharSequence>,
        onClick: (DialogInterface, Int) -> Unit
) {
    with(AndroidAlertBuilder(this)) {
        if (title != null) {
            this.title = title
        }
        items(items, onClick)
        show()
    }
}