@file:[JvmName("SupportFragmentUtils") Suppress("unused")]

package com.extensions.support.app

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.extensions.addToBackStrack
import com.extensions.app.*
import com.extensions.content.*
import com.extensions.view.findView
import com.extensions.TransactionType

inline fun <V : View> Fragment.findView(
    @IdRes id: Int,
    init: V.() -> Unit
): V = view!!.findView(id, init)

/**
 * Intents
 */
inline fun <reified T : Any> Fragment.intentFor(
    action: String? = null,
    flags: Int = -1
): Intent = context!!.intentFor<T>(action, flags)

inline fun <reified T : Any> Fragment.intentFor(
    action: String? = null,
    flags: Int = -1,
    init: Intent.() -> Unit
): Intent = intentFor<T>(action, flags).apply(init)

inline fun <reified T : Activity> Fragment.startActivity(
    action: String? = null,
    flags: Int = -1
) = startActivity(intentFor<T>(action, flags))

inline fun <reified T : Activity> Fragment.startActivity(
    requestcode :Int,
    action: String? = null,
    flags: Int = -1
) = startActivityForResult(intentFor<T>(action, flags), requestcode)

inline fun <reified T : Activity> Fragment.startActivity(
    action: String? = null,
    flags: Int = -1,
    init: Intent.() -> Unit
) = startActivity(intentFor<T>(action, flags).apply(init))

inline fun <reified T : Activity> Fragment.startActivity(
    requestcode :Int,
    action: String? = null,
    flags: Int = -1,
    init: Intent.() -> Unit
) = startActivityForResult(intentFor<T>(action, flags).apply(init), requestcode)

inline fun <reified T : Service> Fragment.startService(
    action: String? = null
): ComponentName = context!!.startService(intentFor<T>(action))

inline fun <reified T : Service> Fragment.startService(
    action: String? = null,
    init: Intent.() -> Unit
): ComponentName = context!!.startService(intentFor<T>(action = action, init = init))

/**
 * Fragment
 */
fun Fragment.addFragment(@IdRes frameId : Int, fragment: Fragment) {
    addFragment(frameId, fragment, null, 0)
}

fun Fragment.addFragment(@IdRes frameId : Int, fragment: Fragment, addToBackStack: Boolean) {
    addFragment(frameId, fragment, null, addToBackStack, 0)
}

fun Fragment.addFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, requestcode: Int) {
    addFragment(frameId, fragment, fragmentToTarget, addToBackStrack, requestcode)
}

fun Fragment.addFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, addToBackStack: Boolean, requestcode: Int) {
    loadFragment(frameId, fragment, fragmentToTarget, TransactionType.ADD, addToBackStack, requestcode)
}

fun Fragment.replaceFragment(@IdRes frameId : Int, fragment: Fragment) {
    replaceFragment(frameId, fragment, null, 0)
}

fun Fragment.replaceFragment(@IdRes frameId : Int, fragment: Fragment, addToBackStack: Boolean) {
    replaceFragment(frameId, fragment, null, addToBackStack, 0)
}

fun Fragment.replaceFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, requestcode: Int) {
    replaceFragment(frameId, fragment, fragmentToTarget, addToBackStrack, requestcode)
}

fun Fragment.replaceFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, addToBackStack: Boolean, requestcode: Int) {
    loadFragment(frameId, fragment, fragmentToTarget, TransactionType.REPLACE, addToBackStack, requestcode)
}

fun Fragment.replaceChildFragment(@IdRes frameId : Int, currentFragment: Fragment, fragment: Fragment, isAddToBackStack: Boolean) {
    (context as AppCompatActivity).replaceChildFragment(frameId, currentFragment, fragment, isAddToBackStack)
}

fun Fragment.loadFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, transactionType:TransactionType, addToBackStack: Boolean, requestcode: Int) {
    (context as AppCompatActivity).loadFragment(frameId, fragment, fragmentToTarget, transactionType, addToBackStack, requestcode)
}

fun Fragment.showDialogFragment(dialogFragment: DialogFragment) {
    (context as AppCompatActivity).showDialogFragment(dialogFragment)
}

fun Fragment.showDialogFragment(dialogFragment: DialogFragment, targetFragment: Fragment, requestCode: Int) {
    (context as AppCompatActivity).showDialogFragment(dialogFragment, targetFragment, requestCode)
}

fun Fragment.showDialogFragment(dialogFragment: DialogFragment, targetFragment: DialogFragment, requestCode: Int) {
    (context as AppCompatActivity).showDialogFragment(dialogFragment, targetFragment, requestCode)
}

fun Fragment.getCurrentFragmentManager(): FragmentManager? {
    return (context as AppCompatActivity).getCurrentFragmentManager()
}

fun Fragment.getCurrentActiveFragment(@IdRes frameId : Int): Fragment {
    return (context as AppCompatActivity).getCurrentActiveFragment(frameId)
}

fun Fragment.clearAllFragment() {
    (context as AppCompatActivity).clearAllFragment()
}

fun Fragment.onBackTrackFragment(): Boolean = (context as AppCompatActivity).onBackTrackFragment()

/**
 * Toasts
 */
fun Fragment.showShortToast(@StringRes resId: Int) {
    context!!.showShortToast(resId)
}

fun Fragment.showShortToast(text: String) {
    context!!.showShortToast(text)
}

fun Fragment.showLongToast(@StringRes resId: Int) {
    context!!.showLongToast(resId)
}

fun Fragment.showLongToast(text: String) {
    context!!.showLongToast(text)
}

/**
 * Permissions
 */
fun Fragment.isPermissionsGranted(vararg permissions: Permission): Boolean =
    context!!.isPermissionsGranted(*permissions)

fun Fragment.isPermissionsGranted(vararg permissions: String): Boolean =
    context!!.isPermissionsGranted(*permissions)

fun Fragment.requestPermissions(requestCode: Int, vararg permissions: Permission) {
    requestPermissions(permissions.map(Permission::value).toTypedArray(), requestCode)
}

/**
 * Input soft keyboard
 */
public fun Fragment.isShowKeyboard(): Boolean {
    return context!!.inputMethodManager.isAcceptingText
}

/**
 * Shortcut for [InputMethodManager.showSoftInput]
 */
public fun Fragment.showSoftInput(editText:EditText) {
    context!!.inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
}

/**
 * Shortcut for [InputMethodManager.hideSoftInputFromWindow]
 */
public fun Fragment.hideSoftInput(currView : View) {
    context!!.inputMethodManager.hideSoftInputFromWindow((currView?: view)?.windowToken, 0)
}

/**
 * Shortcut for [InputMethodManager.toggleSoftInput]
 */
public fun Fragment.toggleSoftInput(
    showFlags: Int = InputMethodManager.SHOW_FORCED,
    hideFlags: Int = 0
) {
    context!!.inputMethodManager.toggleSoftInput(showFlags, hideFlags)
}

/**
 * Shortcut for [android.view.Window.setSoftInputMode] which uses
 * enums instead of raw int flags
 */
public fun Fragment.setSoftInputMode(
    adjustment: SoftInputAdjustment = SoftInputAdjustment.SOFT_INPUT_ADJUST_UNSPECIFIED,
    visibility: SoftInputVisibility = SoftInputVisibility.SOFT_INPUT_STATE_UNSPECIFIED
) {
    setSoftInputMode(adjustment.flag, visibility.flag)
}

/**
 * Shortcut for [android.view.Window.setSoftInputMode] which uses
 * [WindowManager.LayoutParams] flags to specify adjustment and visibility
 */
public fun Fragment.setSoftInputMode(
    adjustmentFlag: Int = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING,
    visibilityFlag: Int = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED
) {
    activity!!.window.setSoftInputMode(adjustmentFlag or visibilityFlag)
}

enum class SoftInputAdjustment(val flag: Int) {
    SOFT_INPUT_ADJUST_UNSPECIFIED(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED),
    SOFT_INPUT_ADJUST_RESIZE(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE),
    SOFT_INPUT_ADJUST_PAN(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}

enum class SoftInputVisibility(val flag: Int) {
    SOFT_INPUT_STATE_UNSPECIFIED(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED),
    SOFT_INPUT_STATE_UNCHANGED(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED),
    SOFT_INPUT_STATE_HIDDEN(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN),
    SOFT_INPUT_STATE_VISIBLE(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE),
    SOFT_INPUT_STATE_ALWAYS_VISIBLE(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}