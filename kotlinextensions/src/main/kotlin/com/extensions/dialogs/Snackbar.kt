package com.extensions.dialogs

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun snackbar(view: View, message: Int) = Snackbar
        .make(view, message, Snackbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun longSnackbar(view: View, message: Int) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun indefiniteSnackbar(view: View, message: Int) = Snackbar
        .make(view, message, Snackbar.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun snackbar(view: View, message: String) = Snackbar
        .make(view, message, Snackbar.LENGTH_SHORT)
        .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun longSnackbar(view: View, message: String) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .apply { show() }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun indefiniteSnackbar(view: View, message: String) = Snackbar
        .make(view, message, Snackbar.LENGTH_INDEFINITE)
        .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun snackbar(view: View, message: Int, actionText: Int, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_SHORT)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun longSnackbar(view: View, message: Int, actionText: Int, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun indefiniteSnackbar(view: View, message: Int, actionText: Int, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_INDEFINITE)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun snackbar(view: View, message: String, actionText: String, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_SHORT)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun longSnackbar(view: View, message: String, actionText: String, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .apply {
            setAction(actionText, action)
            show()
        }

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun indefiniteSnackbar(view: View, message: String, actionText: String, noinline action: (View) -> Unit) = Snackbar
        .make(view, message, Snackbar.LENGTH_INDEFINITE)
        .apply {
            setAction(actionText, action)
            show()
        }