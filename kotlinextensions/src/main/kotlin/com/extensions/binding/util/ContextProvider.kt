package com.extensions.binding.util

import android.app.Activity
import android.app.Dialog
import android.app.Fragment
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi

/**
 * Native
 */
internal val android.view.View.contextProvider: () -> android.content.Context
    inline get() = this::getContext

internal val Activity.contextProvider: () -> Context
    inline get() = { this }

internal val Fragment.contextProvider: () -> Context
    @RequiresApi(Build.VERSION_CODES.M) inline get() = this::getContext

internal val Dialog.contextProvider: () -> Context
    inline get() = this::getContext
