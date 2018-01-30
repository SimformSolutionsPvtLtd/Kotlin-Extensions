package com.extensions.dialogs

import android.app.AlertDialog
import android.app.Fragment
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment as SupportFragment

inline fun Fragment.alert(
        message: CharSequence,
        title: CharSequence? = null,
        noinline init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
) = activity.alert(message, title, init)

inline fun SupportFragment.alert(
    message: CharSequence,
    title: CharSequence? = null,
    noinline init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
) = context!!.alert(message, title, init)

fun Context.alert(
        message: CharSequence,
        title: CharSequence? = null,
        init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
):AlertBuilder<AlertDialog> {
    return AndroidAlertBuilder(this).apply {
        if (title != null) {
            this.title = title
        }
        this.message = message
        if (init != null) init()
    }
}

inline fun SupportFragment.alert(
        message: Int,
        title: Int? = null,
        noinline init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
) = context!!.alert(message, title, init)

inline fun Fragment.alert(
        message: Int,
        title: Int? = null,
        noinline init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
) = activity.alert(message, title, init)

fun Context.alert(
        messageResource: Int,
        titleResource: Int? = null,
        init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
):AlertBuilder<DialogInterface> {
    return AndroidAlertBuilder(this).apply {
        if (titleResource != null) {
            this.titleResource = titleResource
        }
        this.messageResource = messageResource
        if (init != null) init()
    }
}


inline fun SupportFragment.alert(noinline init: AlertBuilder<DialogInterface>.() -> Unit) = context!!.alert(init)
inline fun Fragment.alert(noinline init: AlertBuilder<DialogInterface>.() -> Unit) = activity.alert(init)

fun Context.alert(init: AlertBuilder<DialogInterface>.() -> Unit):AlertBuilder<DialogInterface> =
        AndroidAlertBuilder(this).apply { init() }

inline fun SupportFragment.progressDialog(
        message: Int? = null,
        title: Int? = null,
        noinline init: (ProgressDialog.() -> Unit)? = null
) = context!!.progressDialog(message, title, init)

inline fun Fragment.progressDialog(
        message: Int? = null,
        title: Int? = null,
        noinline init: (ProgressDialog.() -> Unit)? = null
) = activity.progressDialog(message, title, init)

fun Context.progressDialog(
        message: Int? = null,
        title: Int? = null,
        init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(false, message?.let { getString(it) }, title?.let { getString(it) }, init)


inline fun SupportFragment.indeterminateProgressDialog(
        message: Int? = null,
        title: Int? = null,
        noinline init: (ProgressDialog.() -> Unit)? = null
) = context!!.indeterminateProgressDialog(message, title, init)

inline fun Fragment.indeterminateProgressDialog(
        message: Int? = null,
        title: Int? = null,
        noinline init: (ProgressDialog.() -> Unit)? = null
) = activity.indeterminateProgressDialog(message, title, init)

fun Context.indeterminateProgressDialog(
        message: Int? = null,
        title: Int? = null,
        init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(true, message?.let { getString(it) }, title?.let { getString(it) }, init)


inline fun SupportFragment.progressDialog(
        message: CharSequence? = null,
        title: CharSequence? = null,
        noinline init: (ProgressDialog.() -> Unit)? = null
) = context!!.progressDialog(message, title, init)

inline fun Fragment.progressDialog(
        message: CharSequence? = null,
        title: CharSequence? = null,
        noinline init: (ProgressDialog.() -> Unit)? = null
) = activity.progressDialog(message, title, init)

fun Context.progressDialog(
        message: CharSequence? = null,
        title: CharSequence? = null,
        init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(false, message, title, init)


inline fun SupportFragment.indeterminateProgressDialog(
        message: CharSequence? = null,
        title: CharSequence? = null,
        noinline init: (ProgressDialog.() -> Unit)? = null
) = context!!.indeterminateProgressDialog(message, title, init)

inline fun Fragment.indeterminateProgressDialog(
        message: CharSequence? = null,
        title: CharSequence? = null,
        noinline init: (ProgressDialog.() -> Unit)? = null
) = activity.indeterminateProgressDialog(message, title, init)

fun Context.indeterminateProgressDialog(
        message: CharSequence? = null,
        title: CharSequence? = null,
        init: (ProgressDialog.() -> Unit)? = null
) = progressDialog(true, message, title, init)


private fun Context.progressDialog(
        indeterminate: Boolean,
        message: CharSequence? = null,
        title: CharSequence? = null,
        init: (ProgressDialog.() -> Unit)? = null
) = ProgressDialog(this).apply {
    isIndeterminate = indeterminate
    if (!indeterminate) setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
    if (message != null) setMessage(message)
    if (title != null) setTitle(title)
    if (init != null) init()
    show()
}
