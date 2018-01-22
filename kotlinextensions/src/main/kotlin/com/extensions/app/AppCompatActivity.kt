package com.extensions.app

import android.app.Activity
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.extensions.addToBackStrack
import com.extensions.content.Permission
import com.extensions.content.isPermissionsGranted
import com.extensions.content.showLongToast
import com.extensions.content.showShortToast
import com.simform.enums.TransactionType

fun AppCompatActivity.showToolbar() {
    supportActionBar?.show()
}

fun AppCompatActivity.hideToolbar() {
    supportActionBar?.hide()
}

fun AppCompatActivity.addFragment(@IdRes frameId : Int, fragment: Fragment) {
    addFragment(frameId, fragment, null, 0)
}

fun AppCompatActivity.addFragment(@IdRes frameId : Int, fragment: Fragment, addToBackStack: Boolean) {
    addFragment(frameId, fragment, null, addToBackStack, 0)
}

fun AppCompatActivity.addFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, requestcode: Int) {
    addFragment(frameId, fragment, fragmentToTarget, addToBackStrack, requestcode)
}

fun AppCompatActivity.addFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, addToBackStack: Boolean, requestcode: Int) {
    loadFragment(frameId, fragment, fragmentToTarget, TransactionType.ADD, addToBackStack, requestcode)
}

fun AppCompatActivity.replaceFragment(@IdRes frameId : Int, fragment: Fragment) {
    replaceFragment(frameId, fragment, null, 0)
}

fun AppCompatActivity.replaceFragment(@IdRes frameId : Int, fragment: Fragment, addToBackStack: Boolean) {
    replaceFragment(frameId, fragment, null, addToBackStack, 0)
}

fun AppCompatActivity.replaceFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, requestcode: Int) {
    replaceFragment(frameId, fragment, fragmentToTarget, addToBackStrack, requestcode)
}

fun AppCompatActivity.replaceFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, addToBackStack: Boolean, requestcode: Int) {
    loadFragment(frameId, fragment, fragmentToTarget, TransactionType.REPLACE, addToBackStack, requestcode)
}

fun AppCompatActivity.replaceChildFragment(@IdRes frameId : Int, currentFragment: Fragment, fragment: Fragment, isAddToBackStack: Boolean) {
    val fragmentTransaction = currentFragment.childFragmentManager.beginTransaction()
    fragmentTransaction.replace(frameId, fragment)

    if (isAddToBackStack)
        fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
}

fun AppCompatActivity.loadFragment(@IdRes frameId : Int, fragment: Fragment, fragmentToTarget: Fragment?, transactionType: TransactionType, addToBackStack: Boolean, requestcode: Int) {
    val fragmentManager = supportFragmentManager

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

fun AppCompatActivity.showDialogFragment(dialogFragment: DialogFragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(dialogFragment, null)
    fragmentTransaction.commit()
}

fun AppCompatActivity.showDialogFragment(dialogFragment: DialogFragment, targetFragment: Fragment, requestCode: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    dialogFragment.setTargetFragment(targetFragment, requestCode)
    fragmentTransaction.add(dialogFragment, null)
    fragmentTransaction.commit()
}

fun AppCompatActivity.showDialogFragment(dialogFragment: DialogFragment, targetFragment: DialogFragment, requestCode: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    dialogFragment.setTargetFragment(targetFragment, requestCode)
    fragmentTransaction.add(dialogFragment, null)
    fragmentTransaction.commit()
}

fun AppCompatActivity.getCurrentFragmentManager(): FragmentManager? {
    return supportFragmentManager
}

fun AppCompatActivity.getCurrentActiveFragment(@IdRes frameId : Int): Fragment {
    return supportFragmentManager.findFragmentById(frameId)
}

fun AppCompatActivity.clearAllFragment() {
    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun AppCompatActivity.onBackTrackFragment(): Boolean {
    val fragmentManager = supportFragmentManager
    return if (fragmentManager.backStackEntryCount != 0) {
        fragmentManager.popBackStack()
        true
    } else
        false
}

/**
 * Permissions
 */
fun AppCompatActivity.isPermissionsGranted(vararg permissions:Permission): Boolean =
    isPermissionsGranted(*permissions)

fun AppCompatActivity.isPermissionsGranted(vararg permissions: String): Boolean =
    isPermissionsGranted(*permissions)

@RequiresApi(Build.VERSION_CODES.M)
fun AppCompatActivity.requestPermissions(requestCode: Int, vararg permissions:Permission) {
    requestPermissions(permissions.map(Permission::value).toTypedArray(), requestCode)
}

/**
 * Toasts
 */
fun AppCompatActivity.showShortToast(@StringRes resId: Int) {
    showShortToast(resId)
}

fun AppCompatActivity.showShortToast(text: String) {
    showShortToast(text)
}

fun AppCompatActivity.showLongToast(@StringRes resId: Int) {
    showLongToast(resId)
}

fun AppCompatActivity.showLongToast(text: String) {
    showLongToast(text)
}