package com.extensions.content

import android.Manifest
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
import com.extensions.os.isPreLollipop

fun Context.isPermissionsGranted(vararg permissions: Permission): Boolean =
    isPreLollipop() || permissions.all { permission ->
        ContextCompat.checkSelfPermission(this, permission.value) == PackageManager.PERMISSION_GRANTED
    }

fun Context.isPermissionsGranted(vararg permissions: String): Boolean =
    isPreLollipop() || permissions.all { rawPermission ->
        ContextCompat.checkSelfPermission(this, rawPermission) == PackageManager.PERMISSION_GRANTED
    }

enum class Permission(val value: String) {
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
    ACCESS_LOCATION_EXTRA_COM(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS),
    ACCESS_NETWORK_STATE(Manifest.permission.ACCESS_NETWORK_STATE),
    ACCESS_NOTIFICATION_POLIC(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
    ACCESS_WIFI_STATE(Manifest.permission.ACCESS_WIFI_STATE),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    ACCOUNT_MANAGER(Manifest.permission.ACCOUNT_MANAGER),
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL),
    BATTERY_STATS(Manifest.permission.BATTERY_STATS),
    BIND_ACCESSIBILITY_SERVIC(Manifest.permission.BIND_ACCESSIBILITY_SERVICE),
    BIND_APPWIDGET(Manifest.permission.BIND_APPWIDGET),
    BIND_CARRIER_MESSAGING_SE(Manifest.permission.BIND_CARRIER_MESSAGING_SERVICE),
    BIND_CARRIER_SERVICES(Manifest.permission.BIND_CARRIER_SERVICES),
    BIND_CHOOSER_TARGET_SERVI(Manifest.permission.BIND_CHOOSER_TARGET_SERVICE),
    BIND_CONDITION_PROVIDER_S(Manifest.permission.BIND_CONDITION_PROVIDER_SERVICE),
    BIND_DEVICE_ADMIN(Manifest.permission.BIND_DEVICE_ADMIN),
    BIND_DREAM_SERVICE(Manifest.permission.BIND_DREAM_SERVICE),
    BIND_INCALL_SERVICE(Manifest.permission.BIND_INCALL_SERVICE),
    BIND_INPUT_METHOD(Manifest.permission.BIND_INPUT_METHOD),
    BIND_MIDI_DEVICE_SERVICE(Manifest.permission.BIND_MIDI_DEVICE_SERVICE),
    BIND_NFC_SERVICE(Manifest.permission.BIND_NFC_SERVICE),
    BIND_NOTIFICATION_LISTENE(Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE),
    BIND_PRINT_SERVICE(Manifest.permission.BIND_PRINT_SERVICE),
    BIND_QUICK_SETTINGS_TILE(Manifest.permission.BIND_QUICK_SETTINGS_TILE),
    BIND_REMOTEVIEWS(Manifest.permission.BIND_REMOTEVIEWS),
    BIND_SCREENING_SERVICE(Manifest.permission.BIND_SCREENING_SERVICE),
    BIND_TELECOM_CONNECTION_S(Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE),
    BIND_TEXT_SERVICE(Manifest.permission.BIND_TEXT_SERVICE),
    BIND_TV_INPUT(Manifest.permission.BIND_TV_INPUT),
    BIND_VOICE_INTERACTION(Manifest.permission.BIND_VOICE_INTERACTION),
    BIND_VPN_SERVICE(Manifest.permission.BIND_VPN_SERVICE),
    BIND_VR_LISTENER_SERVICE(Manifest.permission.BIND_VR_LISTENER_SERVICE),
    BIND_WALLPAPER(Manifest.permission.BIND_WALLPAPER),
    BLUETOOTH(Manifest.permission.BLUETOOTH),
    BLUETOOTH_ADMIN(Manifest.permission.BLUETOOTH_ADMIN),
    BLUETOOTH_PRIVILEGED(Manifest.permission.BLUETOOTH_PRIVILEGED),
    BODY_SENSORS(Manifest.permission.BODY_SENSORS),
    BROADCAST_PACKAGE_REMOVED(Manifest.permission.BROADCAST_PACKAGE_REMOVED),
    BROADCAST_SMS(Manifest.permission.BROADCAST_SMS),
    BROADCAST_STICKY(Manifest.permission.BROADCAST_STICKY),
    BROADCAST_WAP_PUSH(Manifest.permission.BROADCAST_WAP_PUSH),
    CALL_PHONE(Manifest.permission.CALL_PHONE),
    CALL_PRIVILEGED(Manifest.permission.CALL_PRIVILEGED),
    CAMERA(Manifest.permission.CAMERA),
    CAPTURE_AUDIO_OUTPUT(Manifest.permission.CAPTURE_AUDIO_OUTPUT),
    CAPTURE_SECURE_VIDEO_OUTP(Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT),
    CAPTURE_VIDEO_OUTPUT(Manifest.permission.CAPTURE_VIDEO_OUTPUT),
    CHANGE_COMPONENT_ENABLED_(Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE),
    CHANGE_CONFIGURATION(Manifest.permission.CHANGE_CONFIGURATION),
    CHANGE_NETWORK_STATE(Manifest.permission.CHANGE_NETWORK_STATE),
    CHANGE_WIFI_MULTICAST_STA(Manifest.permission.CHANGE_WIFI_MULTICAST_STATE),
    CHANGE_WIFI_STATE(Manifest.permission.CHANGE_WIFI_STATE),
    CLEAR_APP_CACHE(Manifest.permission.CLEAR_APP_CACHE),
    CONTROL_LOCATION_UPDATES(Manifest.permission.CONTROL_LOCATION_UPDATES),
    DELETE_CACHE_FILES(Manifest.permission.DELETE_CACHE_FILES),
    DELETE_PACKAGES(Manifest.permission.DELETE_PACKAGES),
    DIAGNOSTIC(Manifest.permission.DIAGNOSTIC),
    DISABLE_KEYGUARD(Manifest.permission.DISABLE_KEYGUARD),
    DUMP(Manifest.permission.DUMP),
    EXPAND_STATUS_BAR(Manifest.permission.EXPAND_STATUS_BAR),
    FACTORY_TEST(Manifest.permission.FACTORY_TEST),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS),
    GET_ACCOUNTS_PRIVILEGED(Manifest.permission.GET_ACCOUNTS_PRIVILEGED),
    GET_PACKAGE_SIZE(Manifest.permission.GET_PACKAGE_SIZE),
    GET_TASKS(Manifest.permission.GET_TASKS),
    GLOBAL_SEARCH(Manifest.permission.GLOBAL_SEARCH),
    INSTALL_LOCATION_PROVIDER(Manifest.permission.INSTALL_LOCATION_PROVIDER),
    INSTALL_PACKAGES(Manifest.permission.INSTALL_PACKAGES),
    INSTALL_SHORTCUT(Manifest.permission.INSTALL_SHORTCUT),
    INTERNET(Manifest.permission.INTERNET),
    KILL_BACKGROUND_PROCESSES(Manifest.permission.KILL_BACKGROUND_PROCESSES),
    LOCATION_HARDWARE(Manifest.permission.LOCATION_HARDWARE),
    MANAGE_DOCUMENTS(Manifest.permission.MANAGE_DOCUMENTS),
    MASTER_CLEAR(Manifest.permission.MASTER_CLEAR),
    MEDIA_CONTENT_CONTROL(Manifest.permission.MEDIA_CONTENT_CONTROL),
    MODIFY_AUDIO_SETTINGS(Manifest.permission.MODIFY_AUDIO_SETTINGS),
    MODIFY_PHONE_STATE(Manifest.permission.MODIFY_PHONE_STATE),
    MOUNT_FORMAT_FILESYSTEMS(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS),
    MOUNT_UNMOUNT_FILESYSTEMS(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS),
    NFC(Manifest.permission.NFC),
    PACKAGE_USAGE_STATS(Manifest.permission.PACKAGE_USAGE_STATS),
    PERSISTENT_ACTIVITY(Manifest.permission.PERSISTENT_ACTIVITY),
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS),
    READ_CALENDAR(Manifest.permission.READ_CALENDAR),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG),
    READ_CONTACTS(Manifest.permission.READ_CONTACTS),
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),
    READ_FRAME_BUFFER(Manifest.permission.READ_FRAME_BUFFER),
    READ_INPUT_STATE(Manifest.permission.READ_INPUT_STATE),
    READ_LOGS(Manifest.permission.READ_LOGS),
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE),
    READ_SMS(Manifest.permission.READ_SMS),
    READ_SYNC_SETTINGS(Manifest.permission.READ_SYNC_SETTINGS),
    READ_SYNC_STATS(Manifest.permission.READ_SYNC_STATS),
    READ_VOICEMAIL(Manifest.permission.READ_VOICEMAIL),
    REBOOT(Manifest.permission.REBOOT),
    RECEIVE_BOOT_COMPLETED(Manifest.permission.RECEIVE_BOOT_COMPLETED),
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS),
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH),
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO),
    REORDER_TASKS(Manifest.permission.REORDER_TASKS),
    REQUEST_IGNORE_BATTERY_OP(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS),
    REQUEST_INSTALL_PACKAGES(Manifest.permission.REQUEST_INSTALL_PACKAGES),
    RESTART_PACKAGES(Manifest.permission.RESTART_PACKAGES),
    SEND_RESPOND_VIA_MESSAGE(Manifest.permission.SEND_RESPOND_VIA_MESSAGE),
    SEND_SMS(Manifest.permission.SEND_SMS),
    SET_ALARM(Manifest.permission.SET_ALARM),
    SET_ALWAYS_FINISH(Manifest.permission.SET_ALWAYS_FINISH),
    SET_ANIMATION_SCALE(Manifest.permission.SET_ANIMATION_SCALE),
    SET_DEBUG_APP(Manifest.permission.SET_DEBUG_APP),
    SET_PREFERRED_APPLICATION(Manifest.permission.SET_PREFERRED_APPLICATIONS),
    SET_PROCESS_LIMIT(Manifest.permission.SET_PROCESS_LIMIT),
    SET_TIME(Manifest.permission.SET_TIME),
    SET_TIME_ZONE(Manifest.permission.SET_TIME_ZONE),
    SET_WALLPAPER(Manifest.permission.SET_WALLPAPER),
    SET_WALLPAPER_HINTS(Manifest.permission.SET_WALLPAPER_HINTS),
    SIGNAL_PERSISTENT_PROCESS(Manifest.permission.SIGNAL_PERSISTENT_PROCESSES),
    STATUS_BAR(Manifest.permission.STATUS_BAR),
    SYSTEM_ALERT_WINDOW(Manifest.permission.SYSTEM_ALERT_WINDOW),
    TRANSMIT_IR(Manifest.permission.TRANSMIT_IR),
    UNINSTALL_SHORTCUT(Manifest.permission.UNINSTALL_SHORTCUT),
    UPDATE_DEVICE_STATS(Manifest.permission.UPDATE_DEVICE_STATS),
    USE_FINGERPRINT(Manifest.permission.USE_FINGERPRINT),
    USE_SIP(Manifest.permission.USE_SIP),
    VIBRATE(Manifest.permission.VIBRATE),
    WAKE_LOCK(Manifest.permission.WAKE_LOCK),
    WRITE_APN_SETTINGS(Manifest.permission.WRITE_APN_SETTINGS),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE),
    WRITE_GSERVICES(Manifest.permission.WRITE_GSERVICES),
    WRITE_SECURE_SETTINGS(Manifest.permission.WRITE_SECURE_SETTINGS),
    WRITE_SETTINGS(Manifest.permission.WRITE_SETTINGS),
    WRITE_SYNC_SETTINGS(Manifest.permission.WRITE_SYNC_SETTINGS),
    WRITE_VOICEMAIL(Manifest.permission.WRITE_VOICEMAIL),
    NONE("");

    companion object {
        fun filter(value :String) :Permission {
            return when(value) {
                ACCESS_COARSE_LOCATION.value -> ACCESS_COARSE_LOCATION
                ACCESS_LOCATION_EXTRA_COM.value -> ACCESS_LOCATION_EXTRA_COM
                ACCESS_NETWORK_STATE.value -> ACCESS_NETWORK_STATE
                ACCESS_NOTIFICATION_POLIC.value -> ACCESS_NOTIFICATION_POLIC
                ACCESS_WIFI_STATE.value -> ACCESS_WIFI_STATE
                ACCESS_FINE_LOCATION.value -> ACCESS_FINE_LOCATION
                ACCOUNT_MANAGER.value -> ACCOUNT_MANAGER
                ADD_VOICEMAIL.value -> ADD_VOICEMAIL
                BATTERY_STATS.value -> BATTERY_STATS
                BIND_ACCESSIBILITY_SERVIC.value -> BIND_ACCESSIBILITY_SERVIC
                BIND_APPWIDGET.value -> BIND_APPWIDGET
                BIND_CARRIER_MESSAGING_SE.value -> BIND_CARRIER_MESSAGING_SE
                BIND_CARRIER_SERVICES.value -> BIND_CARRIER_SERVICES
                BIND_CHOOSER_TARGET_SERVI.value -> BIND_CHOOSER_TARGET_SERVI
                BIND_CONDITION_PROVIDER_S.value -> BIND_CONDITION_PROVIDER_S
                BIND_DEVICE_ADMIN.value -> BIND_DEVICE_ADMIN
                BIND_DREAM_SERVICE.value -> BIND_DREAM_SERVICE
                BIND_INCALL_SERVICE.value -> BIND_INCALL_SERVICE
                BIND_INPUT_METHOD.value -> BIND_INPUT_METHOD
                BIND_MIDI_DEVICE_SERVICE.value -> BIND_MIDI_DEVICE_SERVICE
                BIND_NFC_SERVICE.value -> BIND_NFC_SERVICE
                BIND_NOTIFICATION_LISTENE.value -> BIND_NOTIFICATION_LISTENE
                BIND_PRINT_SERVICE.value -> BIND_PRINT_SERVICE
                BIND_QUICK_SETTINGS_TILE.value -> BIND_QUICK_SETTINGS_TILE
                BIND_REMOTEVIEWS.value -> BIND_REMOTEVIEWS
                BIND_SCREENING_SERVICE.value -> BIND_SCREENING_SERVICE
                BIND_TELECOM_CONNECTION_S.value -> BIND_TELECOM_CONNECTION_S
                BIND_TEXT_SERVICE.value -> BIND_TEXT_SERVICE
                BIND_TV_INPUT.value -> BIND_TV_INPUT
                BIND_VOICE_INTERACTION.value -> BIND_VOICE_INTERACTION
                BIND_VPN_SERVICE.value -> BIND_VPN_SERVICE
                BIND_VR_LISTENER_SERVICE.value -> BIND_VR_LISTENER_SERVICE
                BIND_WALLPAPER.value -> BIND_WALLPAPER
                BLUETOOTH.value -> BLUETOOTH
                BLUETOOTH_ADMIN.value -> BLUETOOTH_ADMIN
                BLUETOOTH_PRIVILEGED.value -> BLUETOOTH_PRIVILEGED
                BODY_SENSORS.value -> BODY_SENSORS
                BROADCAST_PACKAGE_REMOVED.value -> BROADCAST_PACKAGE_REMOVED
                BROADCAST_SMS.value -> BROADCAST_SMS
                BROADCAST_STICKY.value -> BROADCAST_STICKY
                BROADCAST_WAP_PUSH.value -> BROADCAST_WAP_PUSH
                CALL_PHONE.value -> CALL_PHONE
                CALL_PRIVILEGED.value -> CALL_PRIVILEGED
                CAMERA.value -> CAMERA
                CAPTURE_AUDIO_OUTPUT.value -> CAPTURE_AUDIO_OUTPUT
                CAPTURE_SECURE_VIDEO_OUTP.value -> CAPTURE_SECURE_VIDEO_OUTP
                CAPTURE_VIDEO_OUTPUT.value -> CAPTURE_VIDEO_OUTPUT
                CHANGE_COMPONENT_ENABLED_.value -> CHANGE_COMPONENT_ENABLED_
                CHANGE_CONFIGURATION.value -> CHANGE_CONFIGURATION
                CHANGE_NETWORK_STATE.value -> CHANGE_NETWORK_STATE
                CHANGE_WIFI_MULTICAST_STA.value -> CHANGE_WIFI_MULTICAST_STA
                CHANGE_WIFI_STATE.value -> CHANGE_WIFI_STATE
                CLEAR_APP_CACHE.value -> CLEAR_APP_CACHE
                CONTROL_LOCATION_UPDATES.value -> CONTROL_LOCATION_UPDATES
                DELETE_CACHE_FILES.value -> DELETE_CACHE_FILES
                DELETE_PACKAGES.value -> DELETE_PACKAGES
                DIAGNOSTIC.value -> DIAGNOSTIC
                DISABLE_KEYGUARD.value -> DISABLE_KEYGUARD
                DUMP.value -> DUMP
                EXPAND_STATUS_BAR.value -> EXPAND_STATUS_BAR
                FACTORY_TEST.value -> FACTORY_TEST
                GET_ACCOUNTS.value -> GET_ACCOUNTS
                GET_ACCOUNTS_PRIVILEGED.value -> GET_ACCOUNTS_PRIVILEGED
                GET_PACKAGE_SIZE.value -> GET_PACKAGE_SIZE
                GET_TASKS.value -> GET_TASKS
                GLOBAL_SEARCH.value -> GLOBAL_SEARCH
                INSTALL_LOCATION_PROVIDER.value -> INSTALL_LOCATION_PROVIDER
                INSTALL_PACKAGES.value -> INSTALL_PACKAGES
                INSTALL_SHORTCUT.value -> INSTALL_SHORTCUT
                INTERNET.value -> INTERNET
                KILL_BACKGROUND_PROCESSES.value -> KILL_BACKGROUND_PROCESSES
                LOCATION_HARDWARE.value -> LOCATION_HARDWARE
                MANAGE_DOCUMENTS.value -> MANAGE_DOCUMENTS
                MASTER_CLEAR.value -> MASTER_CLEAR
                MEDIA_CONTENT_CONTROL.value -> MEDIA_CONTENT_CONTROL
                MODIFY_AUDIO_SETTINGS.value -> MODIFY_AUDIO_SETTINGS
                MODIFY_PHONE_STATE.value -> MODIFY_PHONE_STATE
                MOUNT_FORMAT_FILESYSTEMS.value -> MOUNT_FORMAT_FILESYSTEMS
                MOUNT_UNMOUNT_FILESYSTEMS.value -> MOUNT_UNMOUNT_FILESYSTEMS
                NFC.value -> NFC
                PACKAGE_USAGE_STATS.value -> PACKAGE_USAGE_STATS
                PERSISTENT_ACTIVITY.value -> PERSISTENT_ACTIVITY
                PROCESS_OUTGOING_CALLS.value -> PROCESS_OUTGOING_CALLS
                READ_CALENDAR.value -> READ_CALENDAR
                READ_CALL_LOG.value -> READ_CALL_LOG
                READ_CONTACTS.value -> READ_CONTACTS
                READ_EXTERNAL_STORAGE.value -> READ_EXTERNAL_STORAGE
                READ_FRAME_BUFFER.value -> READ_FRAME_BUFFER
                READ_INPUT_STATE.value -> READ_INPUT_STATE
                READ_LOGS.value -> READ_LOGS
                READ_PHONE_STATE.value -> READ_PHONE_STATE
                READ_SMS.value -> READ_SMS
                READ_SYNC_SETTINGS.value -> READ_SYNC_SETTINGS
                READ_SYNC_STATS.value -> READ_SYNC_STATS
                READ_VOICEMAIL.value -> READ_VOICEMAIL
                REBOOT.value -> REBOOT
                RECEIVE_BOOT_COMPLETED.value -> RECEIVE_BOOT_COMPLETED
                RECEIVE_MMS.value -> RECEIVE_MMS
                RECEIVE_SMS.value -> RECEIVE_SMS
                RECEIVE_WAP_PUSH.value -> RECEIVE_WAP_PUSH
                RECORD_AUDIO.value -> RECORD_AUDIO
                REORDER_TASKS.value -> REORDER_TASKS
                REQUEST_IGNORE_BATTERY_OP.value -> REQUEST_IGNORE_BATTERY_OP
                REQUEST_INSTALL_PACKAGES.value -> REQUEST_INSTALL_PACKAGES
                RESTART_PACKAGES.value -> RESTART_PACKAGES
                SEND_RESPOND_VIA_MESSAGE.value -> SEND_RESPOND_VIA_MESSAGE
                SEND_SMS.value -> SEND_SMS
                SET_ALARM.value -> SET_ALARM
                SET_ALWAYS_FINISH.value -> SET_ALWAYS_FINISH
                SET_ANIMATION_SCALE.value -> SET_ANIMATION_SCALE
                SET_DEBUG_APP.value -> SET_DEBUG_APP
                SET_PREFERRED_APPLICATION.value -> SET_PREFERRED_APPLICATION
                SET_PROCESS_LIMIT.value -> SET_PROCESS_LIMIT
                SET_TIME.value -> SET_TIME
                SET_TIME_ZONE.value -> SET_TIME_ZONE
                SET_WALLPAPER.value -> SET_WALLPAPER
                SET_WALLPAPER_HINTS.value -> SET_WALLPAPER_HINTS
                SIGNAL_PERSISTENT_PROCESS.value -> SIGNAL_PERSISTENT_PROCESS
                STATUS_BAR.value -> STATUS_BAR
                SYSTEM_ALERT_WINDOW.value -> SYSTEM_ALERT_WINDOW
                TRANSMIT_IR.value -> TRANSMIT_IR
                UNINSTALL_SHORTCUT.value -> UNINSTALL_SHORTCUT
                UPDATE_DEVICE_STATS.value -> UPDATE_DEVICE_STATS
                USE_FINGERPRINT.value -> USE_FINGERPRINT
                USE_SIP.value -> USE_SIP
                VIBRATE.value -> VIBRATE
                WAKE_LOCK.value -> WAKE_LOCK
                WRITE_APN_SETTINGS.value -> WRITE_APN_SETTINGS
                WRITE_CALENDAR.value -> WRITE_CALENDAR
                WRITE_CALL_LOG.value -> WRITE_CALL_LOG
                WRITE_CONTACTS.value -> WRITE_CONTACTS
                WRITE_EXTERNAL_STORAGE.value -> WRITE_EXTERNAL_STORAGE
                WRITE_GSERVICES.value -> WRITE_GSERVICES
                WRITE_SECURE_SETTINGS.value -> WRITE_SECURE_SETTINGS
                WRITE_SETTINGS.value -> WRITE_SETTINGS
                WRITE_SYNC_SETTINGS.value -> WRITE_SYNC_SETTINGS
                WRITE_VOICEMAIL.value -> WRITE_VOICEMAIL
                else -> NONE
            }
        }
    }
}

class PermissionExtensions private constructor() {
    fun checkAndRequestPermission(context: Context, permissions: Array<Permission>, callback: (Int, Array<Permission>) -> Unit): Boolean
        = context.processPermission(permissions, callback)

    fun checkAndRequestPermission(context: Context, permissions: Array<Permission>, callback: F2<Int, Array<Permission>>?): Boolean
        = context.processPermission(permissions, { code, permissions -> callback?.invoke(code, permissions) })

    fun requestPermission(context: Context, permissions: Array<Permission>, callback: (Int, Array<Permission>) -> Unit): Boolean
        = context.processPermissions(permissions, callback)

    fun requestPermission(context: Context, permissions: Array<Permission>, callback: F2<Int, Array<Permission>>?): Boolean
        = context.processPermissions(permissions, { code, permissions -> callback?.invoke(code, permissions)})

    private fun Context.processPermission(permissions: Array<Permission>, callback: (Int, Array<Permission>) -> Unit): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            callback(PERMISSION_GRANTED, permissions)
            return true
        }

        var notGranted: Array<out Permission> = this.isGranted(permissions)
        return if (notGranted.isEmpty()) {
            callback(PERMISSION_GRANTED, permissions)
            true
        } else {
            notGranted = this.isRationale(permissions)
            return if (notGranted.isNotEmpty()) {
                callback(PERMISSION_RATIONALE, permissions)
                true
            } else {
                requestPermissions(permissions, callback)
                false
            }
        }
    }

    private fun Context.processPermissions(permissions: Array<Permission>, callback: (Int, Array<Permission>) -> Unit): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            callback(PERMISSION_GRANTED, permissions)
            return true
        }

        requestPermissions(permissions, callback)
        return false
    }


    private fun Context.requestPermissions(permissions: Array<Permission>, callback: (Int, Array<Permission>) -> Unit) {
        val fm:FragmentManager = getActivity(this)?.fragmentManager!!
        val fragment = RequestFragment.getFragment(fm, callback)

        fm.beginTransaction()?.add(fragment, RequestFragment::javaClass.name)?.commitAllowingStateLoss()
        fm.executePendingTransactions()

        fragment.requestPermissions(permissions.map(Permission::value).toTypedArray(), 72)
    }

    class RequestFragment() : Fragment() {
        private lateinit var fm: FragmentManager
        private var secondTime: Boolean = false
        var callback: ((Int, Array<Permission>) -> Unit)? = null

        companion object {
            fun getFragment(fm: FragmentManager, callback: (Int, Array<Permission>) -> Unit): RequestFragment {
                return RequestFragment(fm, callback)
            }
        }

        @SuppressLint("ValidFragment")
        constructor(fm: FragmentManager, callback: (Int, Array<Permission>) -> Unit) : this() {
            this.fm = fm
            this.callback = callback
        }

        @Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            val notGranted: Array<Permission> = context.isGranted(getPermissions(permissions))
            val notDenied: Array<Permission> = context.isRationale(getPermissions(permissions))
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

        fun settingIntent(context: Context):Intent {
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

private fun Context.isGranted(permissions : Array<Permission>): Array<Permission> {
    val notGranted: Array<Permission> = arrayOf()
    permissions.forEach {
        val result = ContextCompat.checkSelfPermission(this, it.value)
        if (result != PackageManager.PERMISSION_GRANTED)
            notGranted[notGranted.size] = it
    }
    return notGranted
}

private fun Context.isRationale(permissions: Array<Permission>): Array<Permission> {
    val notRationale: Array<Permission> = arrayOf()
    permissions.forEach {
        val result = getActivity(this)?.let { activity -> shouldShowRequestPermissionRationale(activity, it.value) }
        if (result!!)
            notRationale[notRationale.size] = it
    }
    return notRationale
}

private fun getPermissions(permissions: Array<String>): Array<Permission> {
    val permission: Array<Permission> = arrayOf()
    permissions.forEach {
        permission[permission.size] = Permission.filter(it)
    }
    return  permission
}
