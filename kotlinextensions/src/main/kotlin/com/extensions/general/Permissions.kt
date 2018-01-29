package com.extensions.general

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.content.ContextCompat
import com.extensions.interfaces.F2


class PermissionExtensions private constructor() {
    @JvmOverloads
    fun checkAndRequestPermission(context: Context, array: Array<String>, callback: (Int, List<String>) -> Unit = { _, _ -> }): Boolean
            = checkAndRequestPermission(context, List(array.size) { array[it] }, callback)

    fun checkAndRequestPermission(context: Context, array: Array<String>, callback: F2<Int, List<String>>?): Boolean
            = checkAndRequestPermission(context, List(array.size) { array[it] }, callback)

    private fun checkAndRequestPermission(context: Context, list: List<String>, callback: (Int, List<String>) -> Unit): Boolean
            = context.processPermission(list, callback)

    private fun checkAndRequestPermission(context: Context, list: List<String>, callback: F2<Int, List<String>>?): Boolean
            = context.processPermission(list, { code, arrays -> callback?.invoke(code, arrays) })

    @JvmOverloads
    fun requestPermission(context: Context, array: Array<String>, callback: (Int, List<String>) -> Unit = { _, _ -> }): Boolean
            = requestPermission(context, List(array.size) { array[it] }, callback)

    fun requestPermission(context: Context, array: Array<String>, callback: F2<Int, List<String>>?): Boolean
            = requestPermission(context, List(array.size) { array[it] }, callback)

    fun requestPermission(context: Context, list: List<String>, callback: (Int, List<String>) -> Unit): Boolean
            = context.processPermissions(list, callback)

    private fun requestPermission(context: Context, list: List<String>, callback: F2<Int, List<String>>?): Boolean
            = context.processPermissions(list, { code, arrays -> callback?.invoke(code, arrays) })

    @SuppressLint("NewApi")
    private fun Context.processPermission(list: List<String>, callback: (Int, List<String>) -> Unit): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            callback(PERMISSION_GRANTED, list)
            return true
        }

        var notGranted: ArrayList<String> = this.isGranted(list)
        return if (notGranted.isEmpty()) {
            callback(PERMISSION_GRANTED, list)
            true
        } else {
            notGranted = this.isRationale(list)
            return if (notGranted.isNotEmpty()) {
                callback(PERMISSION_RATIONALE, list)
                true
            } else {
                requestPermissions(list, callback)
                false
            }
        }
    }

    @SuppressLint("NewApi")
    private fun Context.processPermissions(list: List<String>, callback: (Int, List<String>) -> Unit): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            callback(PERMISSION_GRANTED, list)
            return true
        }

        requestPermissions(list, callback)
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun Context.requestPermissions(list: List<String>, callback: (Int, ArrayList<String>) -> Unit) {
        val fm: FragmentManager = getActivity(this)?.fragmentManager!!
        val fragment = RequestFragment.getFragment(fm, callback)

        fm.beginTransaction().add(fragment, RequestFragment::javaClass.name).commitAllowingStateLoss()
        fm.executePendingTransactions()

        fragment.requestPermissions(list.toTypedArray(), 72)
    }

    @SuppressLint("ValidFragment")
    class RequestFragment() : Fragment() {
        private lateinit var fm: FragmentManager
        private var secondTime: Boolean = false
        var callback: ((Int, ArrayList<String>) -> Unit)? = null

        companion object {
            fun getFragment(fm: FragmentManager, callback: (Int, ArrayList<String>) -> Unit): RequestFragment {
                return RequestFragment(fm, callback)
            }
        }

        constructor(fm: FragmentManager, callback: (Int, ArrayList<String>) -> Unit) : this() {
            this.fm = fm
            this.callback = callback
        }

        @Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            val notGranted: ArrayList<String> = context.isGranted(permissions)
            val notDenied: ArrayList<String> = context.isRationale(permissions)
            when {
                notGranted.isEmpty() -> callback?.invoke(PERMISSION_GRANTED, getPermissions(permissions))
                notDenied.isEmpty() -> {
                    if (secondTime) {
                        secondTime = false
                        callback?.invoke(PERMISSION_FAILED, getPermissions(permissions))
                    } else {
                        secondTime = true
                        callback?.invoke(PERMISSION_SETTING, getPermissions(permissions))
                    }
                }
                else -> callback?.invoke(PERMISSION_RATIONALE, getPermissions(permissions))
            }
            fm.beginTransaction().remove(this).commitAllowingStateLoss()
        }
    }

    companion object {
        @JvmField
        var instance: PermissionExtensions = PermissionExtensions()

        @JvmField
        val PERMISSION_GRANTED = 1
        @JvmField
        val PERMISSION_RATIONALE = 2
        @JvmField
        val PERMISSION_SETTING = 3
        @JvmField
        val PERMISSION_FAILED = 4

        fun settingIntent(context: Context): Intent {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.packageName))
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            return intent
        }
    }
}

private fun getActivity(context: Context): Activity? {
    var c = context

    while (c is ContextWrapper) {
        if (c is Activity) {
            return c
        }
        c = c.baseContext
    }
    return null
}

private fun Context.isGranted(list: List<String>): ArrayList<String> {
    val notGranted: ArrayList<String> = ArrayList()
    list.forEach {
        val result = ContextCompat.checkSelfPermission(this, it)
        if (result != PackageManager.PERMISSION_GRANTED)
            notGranted.add(it)
    }
    return notGranted
}

private fun Context.isRationale(list: List<String>): ArrayList<String> {
    val notGranted: ArrayList<String> = ArrayList()
    list.forEach {
        val result = getActivity(this)?.let { activity -> shouldShowRequestPermissionRationale(activity, it) }
        if (result!!)
            notGranted.add(it)
    }
    return notGranted
}

private fun Context.isGranted(list: Array<String>): ArrayList<String> {
    val notGranted: ArrayList<String> = ArrayList()
    list.forEach {
        val result = ContextCompat.checkSelfPermission(this, it)
        if (result != PackageManager.PERMISSION_GRANTED)
            notGranted.add(it)
    }
    return notGranted
}

private fun Context.isRationale(list: Array<String>): ArrayList<String> {
    val notGranted: ArrayList<String> = ArrayList()
    list.forEach {
        val result = getActivity(this)?.let { activity -> shouldShowRequestPermissionRationale(activity, it) }
        if (result!!)
            notGranted.add(it)
    }
    return notGranted
}

private fun getPermissions(permissions: Array<String>): ArrayList<String> {
    val permissionList: ArrayList<String> = ArrayList()
    permissions.forEach {
        permissionList.add(it)
    }

    return permissionList
}