@file:JvmName("ActivityUtils")

package com.extensions.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DialogFragment
import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.extensions.R
import com.extensions.addToBackStrack
import com.extensions.content.Permission
import com.extensions.content.intentFor
import com.extensions.content.isPermissionsGranted
import com.extensions.TransactionType

/**
 * Shortcut for retrieving root view of Activity
 *
 * @return root view of Activity
 */
public val Activity.rootView: ViewGroup
    get() = findViewById(android.R.id.content)

/**
 * Finds view and apply init block to it.
 *
 * @param id - identifier of [View]
 * @param init - block that applies to found view
 * @return [View] with appropriate type and with applied init block
 */
public inline fun <V : View> Activity.findView(
    @IdRes id: Int,
    init: V.() -> Unit
): V = findViewById<V>(id).apply(init)

fun Activity.setFullScreen() {
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Activity.showToolbar() {
    actionBar?.show()
}

fun Activity.hideToolbar() {
    actionBar?.hide()
}


@SuppressLint("ObsoleteSdkInt")
fun Activity.toggleHideByBar(isHide: Boolean) {
    val uiOptions = window.decorView.systemUiVisibility
    var newUiOptions = uiOptions

    if (isHide) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    } else {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_VISIBLE
    }
    window.decorView.systemUiVisibility = newUiOptions
}

/**
 * Starts another Activity for result from receiver Activity.
 * Benefits over standard way that you don't need to build intent manually.
 *
 * @param requestCode code that will be returned in [Activity.onActivityResult] after Activity exits.
 * @param action - value passed as [Intent.setAction]
 * @param flags - value passed as [Intent.setFlags]
 */
public inline fun <reified T : Activity> Activity.launchActivity(
        finishCurrent: Boolean,
        action: String? = null,
        flags: Int = -1
) {
    if (finishCurrent)
        finishCurrentActivity()
    startActivity(intentFor<T>(action, flags))
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
}

/**
 * Starts another Activity for result from receiver Activity.
 * Benefits over standard way that you don't need to build intent manually.
 *
 * @param requestCode code that will be returned in [Activity.onActivityResult] after Activity exits.
 * @param action - value passed as [Intent.setAction]
 * @param flags - value passed as [Intent.setFlags]
 */
public inline fun <reified T : Activity> Activity.launchActivity(
        requestCode: Int,
        finishCurrent: Boolean,
        action: String? = null,
        flags: Int = -1
) {
    if (finishCurrent)
        finishCurrentActivity()
    startActivityForResult(intentFor<T>(action, flags), requestCode)
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
}

public inline fun <reified T : Activity> Activity.launchActivity(
        finishCurrent: Boolean,
        action: String? = null,
        flags: Int = -1,
        init: Intent.() -> Unit
) {
    if (finishCurrent)
        finishCurrentActivity()
    startActivity(intentFor<T>(action, flags, init))
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
}

public inline fun <reified T : Activity> Activity.launchActivity(
        requestCode: Int,
        finishCurrent: Boolean,
        action: String? = null,
        flags: Int = -1,
        init: Intent.() -> Unit
) {
    if (finishCurrent)
        finishCurrentActivity()
    startActivityForResult(intentFor<T>(action, flags, init), requestCode)
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
}

fun Activity.finishCurrentActivity() {
    finish()
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
}

fun Activity.finishAllCurrentActivity() {
    finishAffinity()
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
}


fun Activity.addFragment(@IdRes frameId : Int, fragment: Fragment) {
    addFragment(frameId, fragment, null, 0)
}


fun Activity.addFragment(@IdRes frameId : Int, fragment: Fragment, addToBackStack: Boolean) {
    addFragment(frameId, fragment, null, addToBackStack, 0)
}

fun Activity.addFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, requestcode: Int) {
    addFragment(frameId, fragment, fragmentToTarget, addToBackStrack, requestcode)
}

fun Activity.addFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, addToBackStack: Boolean, requestcode: Int) {
    loadFragment(frameId, fragment, fragmentToTarget, TransactionType.ADD, addToBackStack, requestcode)
}

fun Activity.replaceFragment(@IdRes frameId : Int, fragment: Fragment) {
    replaceFragment(frameId, fragment, null, 0)
}

fun Activity.replaceFragment(@IdRes frameId : Int, fragment: Fragment, addToBackStack: Boolean) {
    replaceFragment(frameId, fragment, null, addToBackStack, 0)
}

fun Activity.replaceFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, requestcode: Int) {
    replaceFragment(frameId, fragment, fragmentToTarget, addToBackStrack, requestcode)
}

fun Activity.replaceFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, addToBackStack: Boolean, requestcode: Int) {
    loadFragment(frameId, fragment, fragmentToTarget, TransactionType.REPLACE, addToBackStack, requestcode)
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun Activity.replaceChildFragment(@IdRes frameId : Int, currentFragment: Fragment, fragment: Fragment, isAddToBackStack: Boolean) {
    val fragmentTransaction = currentFragment.childFragmentManager.beginTransaction()
    fragmentTransaction.replace(frameId, fragment)

    if (isAddToBackStack)
        fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
}

@SuppressLint("ResourceType")
fun Activity.loadFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, transactionType:TransactionType, addToBackStack: Boolean, requestcode: Int) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    if (addToBackStack)
        fragmentTransaction.addToBackStack(null)
    if (fragmentToTarget != null)
        fragment.setTargetFragment(fragmentToTarget, requestcode)

    if (transactionType == TransactionType.ADD)
        fragmentTransaction.add(frameId, fragment)
    else
        fragmentTransaction.replace(frameId, fragment)
    fragmentTransaction.commit()
}


fun Activity.showDialogFragment(dialogFragment: DialogFragment) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.add(dialogFragment, null)
    fragmentTransaction.commit()
}


fun Activity.showDialogFragment(dialogFragment: DialogFragment, targetFragment: Fragment, requestCode: Int) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    dialogFragment.setTargetFragment(targetFragment, requestCode)
    fragmentTransaction.add(dialogFragment, null)
    fragmentTransaction.commit()
}


fun Activity.showDialogFragment(dialogFragment: DialogFragment, targetFragment: DialogFragment, requestCode: Int) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    dialogFragment.setTargetFragment(targetFragment, requestCode)
    fragmentTransaction.add(dialogFragment, null)
    fragmentTransaction.commit()
}


fun Activity.getCurrentFragmentManager(): FragmentManager? {
    return fragmentManager
}


fun Activity.getCurrentActiveFragment(@IdRes frameId : Int): Fragment {
    return fragmentManager.findFragmentById(frameId)
}


fun Activity.clearAllFragment() {
    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}


fun Activity.onBackTrackFragment(): Boolean {
    return if (fragmentManager.backStackEntryCount != 0) {
        fragmentManager.popBackStack()
        true
    } else
        false
}

/**
 * Permissions
 */
fun Activity.isPermissionsGranted(vararg permissions:Permission): Boolean =
    isPermissionsGranted(*permissions)

fun Activity.isPermissionsGranted(vararg permissions: String): Boolean =
    isPermissionsGranted(*permissions)

@RequiresApi(Build.VERSION_CODES.M)
fun Activity.requestPermissions(requestCode: Int, vararg permissions:Permission) {
    requestPermissions(permissions.map(Permission::value).toTypedArray(), requestCode)
}