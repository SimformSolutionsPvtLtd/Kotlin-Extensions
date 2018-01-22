package com.extensions.clipboard

import android.content.ClipData
import android.content.Context
import android.net.Uri
import com.extensions.content.clipboardManager

fun Context.copyTextToClipboard(value: String) {
    clipboardManager.primaryClip = ClipData.newPlainText("text", value)
}

fun Context.copyUriToClipboard(uri: Uri) {
    clipboardManager.primaryClip = ClipData.newUri(contentResolver, "uri", uri)
}

fun Context.getTextFromClipboard(): CharSequence {
    val clipData = clipboardManager.primaryClip
    if (clipData != null && clipData.itemCount > 0) {
        return clipData.getItemAt(0).coerceToText(this)
    }

    return ""
}

fun Context.getUriFromClipboard(): Uri? {
    val clipData = clipboardManager.primaryClip
    if (clipData != null && clipData.itemCount > 0) {
        return clipData.getItemAt(0).uri
    }

    return null
}
