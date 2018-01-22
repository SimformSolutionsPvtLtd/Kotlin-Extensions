package com.extensions.general

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.InputStream

fun InputStream.getString(): String = this.bufferedReader().use {
    it.readText()
}

fun InputStream.outAsFile(file: File): File {
    file.createNewFile()

    use { input ->
        file.outputStream().use { fileOut ->
            input.copyTo(fileOut)
        }
    }
    return file
}

fun InputStream.outAsBitmap(): Bitmap? = use {
    BitmapFactory.decodeStream(it)
}
