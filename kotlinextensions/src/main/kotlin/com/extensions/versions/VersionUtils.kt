package com.extensions.versions

import android.os.Build

class VersionUtils {

    companion object {

        inline fun doWithVersion(version: Int, body: () -> Unit) {
            if (Build.VERSION.SDK_INT == version) {
                body()
            } else {
                throw InvalidVersionException("Version is different")
            }
        }

        inline fun doWithAtLeastVersion(version: Int, body: () -> Unit) {
            if (Build.VERSION.SDK_INT >= version) {
                body()
            } else {
                throw InvalidVersionException("Version is lower than expected")
            }
        }

        inline fun doWithHigherVersion(version: Int, body: () -> Unit) {
            if (Build.VERSION.SDK_INT > version) {
                body()
            } else {
                throw InvalidVersionException("Version is lower than expected")
            }
        }

        inline fun doWithLowerVersion(version: Int, body: () -> Unit) {
            if (Build.VERSION.SDK_INT < version) {
                body()
            } else {
                throw InvalidVersionException("Version is higher than expected")
            }
        }
    }
}