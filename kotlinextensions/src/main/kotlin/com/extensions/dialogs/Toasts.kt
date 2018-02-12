package com.extensions.dialogs

import android.app.Fragment
import android.content.Context
import android.widget.Toast
import android.support.v4.app.Fragment as SupportFragment

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.toast(message: Int) = activity.toast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun SupportFragment.toast(message: Int) = context!!.toast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun Context.toast(message: Int): Toast = Toast
        .makeText(this, message, Toast.LENGTH_SHORT)
        .apply {
            show()
        }

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.toast(message: CharSequence) = activity.toast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun SupportFragment.toast(message: CharSequence) = context!!.toast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Context.toast(message: CharSequence): Toast = Toast
        .makeText(this, message, Toast.LENGTH_SHORT)
        .apply {
            show()
        }

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.longToast(message: Int) = activity.longToast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun SupportFragment.longToast(message: Int) = context!!.longToast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun Context.longToast(message: Int): Toast = Toast
        .makeText(this, message, Toast.LENGTH_LONG)
        .apply {
            show()
        }

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.longToast(message: CharSequence) = activity.longToast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun SupportFragment.longToast(message: CharSequence) = context!!.longToast(message)

/**
 * Display the simple Toast message with the [Toast.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Context.longToast(message: CharSequence): Toast = Toast
        .makeText(this, message, Toast.LENGTH_LONG)
        .apply {
            show()
        }