@file:[JvmName("SupportFragmentUtils") Suppress("unused")]

package com.extensions.app

import android.app.Activity
import android.app.DialogFragment
import android.app.Fragment
import android.app.FragmentManager
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.extensions.TransactionType
import com.extensions.addToBackStrack
import com.extensions.content.Permission
import com.extensions.content.inputMethodManager
import com.extensions.content.intentFor
import com.extensions.view.findView

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
): Intent {
    return activity!!.intentFor<T>(action, flags)
}

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
): ComponentName = activity!!.startService(intentFor<T>(action))

inline fun <reified T : Service> Fragment.startService(
    action: String? = null,
    init: Intent.() -> Unit
): ComponentName = activity!!.startService(intentFor<T>(action = action, init = init))

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

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun Fragment.replaceChildFragment(@IdRes frameId : Int, currentFragment: Fragment, fragment: Fragment, isAddToBackStack: Boolean) {
    (activity as Activity).replaceChildFragment(frameId, currentFragment, fragment, isAddToBackStack)
}

fun Fragment.loadFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, transactionType:TransactionType, addToBackStack: Boolean, requestcode: Int) {
    (activity as Activity).loadFragment(frameId, fragment, fragmentToTarget, transactionType, addToBackStack, requestcode)
}

fun Fragment.showDialogFragment(dialogFragment: DialogFragment) {
    (activity as Activity).showDialogFragment(dialogFragment)
}

fun Fragment.showDialogFragment(dialogFragment: DialogFragment, targetFragment: Fragment, requestCode: Int) {
    (activity as Activity).showDialogFragment(dialogFragment, targetFragment, requestCode)
}

fun Fragment.showDialogFragment(dialogFragment: DialogFragment, targetFragment: DialogFragment, requestCode: Int) {
    (activity as Activity).showDialogFragment(dialogFragment, targetFragment, requestCode)
}

fun Fragment.getCurrentFragmentManager(): FragmentManager? {
    return (activity as Activity).getCurrentFragmentManager()
}

fun Fragment.getCurrentActiveFragment(@IdRes frameId : Int): Fragment {
    return (activity as Activity).getCurrentActiveFragment(frameId)
}

fun Fragment.clearAllFragment() {
    (activity as Activity).clearAllFragment()
}

fun Fragment.onBackTrackFragment(): Boolean = (activity as Activity).onBackTrackFragment()

/**
 * Permissions
 */
fun Fragment.isPermissionsGranted(vararg permissions: Permission): Boolean =
    activity!!.isPermissionsGranted(*permissions)

fun Fragment.isPermissionsGranted(vararg permissions: String): Boolean =
    activity!!.isPermissionsGranted(*permissions)

@RequiresApi(Build.VERSION_CODES.M)
fun Fragment.requestPermissions(requestCode: Int, vararg permissions: Permission) {
    activity!!.requestPermissions(permissions.map(Permission::value).toTypedArray(), requestCode)
}

/**
 * Input soft keyboard
 */
public fun Fragment.isShowKeyboard(): Boolean {
    return activity!!.inputMethodManager.isAcceptingText
}

/**
 * Shortcut for [InputMethodManager.showSoftInput]
 */
public fun Fragment.showSoftInput(editText:EditText) {
    activity!!.inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
}

/**
 * Shortcut for [InputMethodManager.hideSoftInputFromWindow]
 */
public fun Fragment.hideSoftInput(currView : View) {
    activity!!.inputMethodManager.hideSoftInputFromWindow((currView?: view)?.windowToken, 0)
}

/**
 * Shortcut for [InputMethodManager.toggleSoftInput]
 */
public fun Fragment.toggleSoftInput(
    showFlags: Int = InputMethodManager.SHOW_FORCED,
    hideFlags: Int = 0
) {
    activity!!.inputMethodManager.toggleSoftInput(showFlags, hideFlags)
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