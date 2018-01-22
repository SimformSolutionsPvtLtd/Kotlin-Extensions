@file:[Suppress("unused")]

package com.extensions.content

import android.animation.Animator
import android.animation.AnimatorInflater
import android.app.Activity
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Creates intent for specified [T] component of Android.
 *
 * @param action - value passed to [Intent.setAction]
 * @param flags - value passed to [Intent.setFlags]
 */
inline fun <reified T : Any> Context.intentFor(
    action: String? = null,
    flags: Int = -1
): Intent = Intent(this, T::class.java).apply {
    this.action = action
    this.flags = flags
}

/**
 * Creates intent for specified [T] component of Android and
 * initialize it with [init] block.
 */
inline fun <reified T : Any> Context.intentFor(
    action: String? = null,
    flags: Int = -1,
    init: Intent.() -> Unit
): Intent = intentFor<T>(action, flags).apply(init)

/**
 * Activities
 */
inline fun <reified T : Activity> Context.startActivity(
    action: String? = null,
    flags: Int = -1
) = startActivity(intentFor<T>(action, flags))

inline fun <reified T : Activity> Context.startActivity(
    action: String? = null,
    flags: Int = -1,
    init: Intent.() -> Unit
) = startActivity(intentFor<T>(action, flags, init))

/**
 * Services
 */
inline fun <reified T : Service> Context.startService(
    action: String? = null
): ComponentName = startService(intentFor<T>(action = action))

inline fun <reified T : Service> Context.startService(
    action: String? = null,
    init: Intent.() -> Unit
): ComponentName = startService(intentFor<T>(action = action, init = init))

fun Context.chooseActivity(title: String = "", f: Intent.() -> Unit) =
        startActivity(Intent.createChooser(Intent().apply(f), title))

fun Activity.startForResult(requestCode: Int, f: Intent.() -> Unit): Unit =
        Intent().apply(f).run { startActivityForResult(this, requestCode) }

fun Context.browse(url: String) {
    browse(Uri.parse(url))
}

fun Context.browse(uri: Uri) {
    startActivity(Intent(Intent.ACTION_VIEW, uri))
}

fun Context.send(f: Intent.() -> Unit) =
        Intent().apply(f).run(this::sendBroadcast)

fun Context.send(action: String, f: Intent.() -> Unit = {}) =
        Intent(action).apply(f).run(this::sendBroadcast)

fun Context.send(action: String, permission: String, f: Intent.() -> Unit = {}) =
        Intent(action).apply(f).run { sendBroadcast(this, permission) }

/**
 * PendingIntent
 */
inline fun <reified T : Any> Context.pendingIntentFor(
    intent: Intent,
    requestCode: Int = 0,
    pendingIntentFlags: Int = 0
): PendingIntent = T::class.java.let { clazz ->
    when {
        Activity::class.java.isAssignableFrom(clazz) ->
            PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlags)

        Service::class.java.isAssignableFrom(clazz) ->
            PendingIntent.getService(this, requestCode, intent, pendingIntentFlags)

        BroadcastReceiver::class.java.isAssignableFrom(clazz) ->
            PendingIntent.getBroadcast(this, requestCode, intent, pendingIntentFlags)

        else -> throw IllegalStateException("PendingIntent must be used only with Activity, Service or BroadcastReceiver")
    }
}

inline fun <reified T : Any> Context.pendingIntentFor(
    action: String? = null,
    intentFlags: Int = -1,
    requestCode: Int = 0,
    pendingIntentFlags: Int = 0
): PendingIntent = pendingIntentFor<T>(
    intent = intentFor<T>(action, intentFlags),
    requestCode = requestCode,
    pendingIntentFlags = pendingIntentFlags
)

inline fun <reified T : Any> Context.pendingIntentFor(
    action: String? = null,
    intentFlags: Int = -1,
    requestCode: Int = 0,
    pendingIntentFlags: Int = 0,
    init: Intent.() -> Unit
): PendingIntent = pendingIntentFor<T>(
    intent = intentFor<T>(action, intentFlags, init),
    requestCode = requestCode,
    pendingIntentFlags = pendingIntentFlags
)

/**
 * Shows short [Toast] with specified string resource.
 */
fun Context.showShortToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.showLongToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/**
 * Resources
 */
fun Context.getAnimation(@AnimRes id: Int): Animation =
    AnimationUtils.loadAnimation(this, id)

fun Context.getAnimator(@AnimatorRes id: Int): Animator =
    AnimatorInflater.loadAnimator(this, id)

/**
 * ContextCompat
 */
fun Context.getThemedColor(@ColorRes resId: Int): Int =
    ContextCompat.getColor(this, resId)

fun Context.getThemedColorStateList(@ColorRes resId: Int): ColorStateList? =
    ContextCompat.getColorStateList(this, resId)

fun Context.getThemedDrawable(@DrawableRes resId: Int): Drawable? =
    ContextCompat.getDrawable(this, resId)

/**
 * Other
 */
val Context.isRtl: Boolean
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    } else {
        false
    }

val Context.isLtr: Boolean
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
    } else {
        true
    }

val Context.isLandscape: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

val Context.isPortrait: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

fun Context.convertToDp(pixels: Int): Int =
    (pixels * resources.displayMetrics.density).toInt()

fun Context.convertToSp(pixels: Int): Int =
    (pixels * resources.displayMetrics.scaledDensity).toInt()

fun Context.isPackageInstalled(packageName: String): Boolean = try {
    packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
    true
} catch (e: PackageManager.NameNotFoundException) {
    false
}

fun Context.email(email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    if (subject.isNotEmpty())
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (text.isNotEmpty())
        intent.putExtra(Intent.EXTRA_TEXT, text)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false

}

@Suppress("unused")
fun Context.hideKeyboard(window: Window, view: View?) {
    if (view?.windowToken != null)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

@Suppress("unused")
fun Context.isShowKeyboard(): Boolean {
    return inputMethodManager.isAcceptingText
}

@Suppress("unused")
fun Context.toggleKeyboard() {
    if (inputMethodManager.isActive)
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
}
