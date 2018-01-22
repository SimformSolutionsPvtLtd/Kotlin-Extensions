package com.extensions.general

import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import com.extensions.interfaces.F1
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

@Suppress("unused")
fun downloadFile(urlPath :String, localPath :String, callback :(Uri?) -> Unit = {}) :Uri? {
    var uri :Uri? = null
    val connection = URL(urlPath).openConnection() as HttpURLConnection

    if(connection.responseCode == HttpURLConnection.HTTP_OK) {
        uri = Uri.fromFile(connection.inputStream.outAsFile(localPath.toFile()))
    }
    connection.disconnect()
    if(uri is Uri) {
        callback(uri)
    } else {
        callback(null)
    }
    return uri
}

@Suppress("unused")
fun downloadFile(urlPath :String, localPath :String, callback : F1<Uri>?) :Uri? {
    var uri :Uri? = null
    val connection = URL(urlPath).openConnection() as HttpURLConnection

    if(connection.responseCode == HttpURLConnection.HTTP_OK) {
        uri = Uri.fromFile(connection.inputStream.outAsFile(localPath.toFile()))
    }
    connection.disconnect()
    callback?.invoke(uri!!)
    return uri
}

fun String.toFile() = File(this)
@Suppress("unused")
fun saveFile(fullPath :String, content :String) :File =
    fullPath.toFile().apply {
        writeText(content, Charset.defaultCharset())
    }

@Suppress("unused")
fun File.readFile() :String = this.readText(Charset.defaultCharset())

private fun getDataColumn(context :Context, uri :Uri?, selection :String?, selectionArgs :Array<String>?) :String {
    context.contentResolver.query(uri, arrayOf("_data"), selection, selectionArgs, null).use {
        if(it != null && it.moveToFirst()) {
            val columnIndex = it.getColumnIndexOrThrow("_data")
            return it.getString(columnIndex)
        }
    }
    return ""
}

@Suppress("unused")
@TargetApi(Build.VERSION_CODES.KITKAT)
infix fun Uri.getRealPath(context :Context) :String {
    val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    if(isKitKat && DocumentsContract.isDocumentUri(context, this)) {
        return checkAuthority(context)
    }

    if("content".equals(this.scheme, ignoreCase = true)) {
        if("com.google.android.apps.photos.content" == this.authority)
            return this.lastPathSegment

        return getDataColumn(context, this, null, null)
    }

    if("file".equals(this.scheme, ignoreCase = true)) {
        return this.path
    }

    return this.path
}

@RequiresApi(Build.VERSION_CODES.KITKAT)
private fun Uri.checkAuthority(context :Context) :String {
    val docId = DocumentsContract.getDocumentId(this)
    val split = docId.split(":".toRegex()).dropLastWhile {it.isEmpty()}.toTypedArray()

    if("com.android.externalstorage.documents" == this.authority) {
        val type = split[0]

        if("primary".equals(type, ignoreCase = true))
            return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
    } else if("com.android.providers.downloads.documents" == this.authority) {
        return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), docId.toLong()), null, null)
    } else if("com.android.providers.media.documents" == this.authority) {
        val contentUri = when(split[0]) {
            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        return getDataColumn(context, contentUri, "_id=?", arrayOf(split[1]))
    }

    return this.path
}

//get Path
@Suppress("unused")
@TargetApi(Build.VERSION_CODES.KITKAT)
fun Context.getRealPathFromURI(uri :Uri) :String? {
    val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    if(isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
        if(isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile {it.isEmpty()}.toTypedArray()
            val type = split[0]

            if("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
        } else if(isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

            return getDataColumns(contentUri, null, null)
        } else if(isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile {it.isEmpty()}.toTypedArray()
            val type = split[0]
            var contentUri :Uri? = null
            when(type) {
                "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])

            return getDataColumns(contentUri, selection, selectionArgs)
        }
    } else return if("content".equals(uri.scheme, ignoreCase = true)) {
        if(isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumns(uri, null, null)
    } else if("file".equals(uri.scheme, ignoreCase = true)) {
        uri.path
    } else
        getRealPathFromURIDB(uri)
    return null
}

private fun Context.getRealPathFromURIDB(contentUri :Uri) :String {
    val cursor = contentResolver.query(contentUri, null, null, null, null)
    return if(cursor == null) {
        contentUri.path
    } else {
        cursor.moveToFirst()
        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        val realPath = cursor.getString(index)
        cursor.close()
        realPath
    }
}

private fun Context.getDataColumns(uri :Uri?, selection :String?, selectionArgs :Array<String>?) :String? {
    var cursor :Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)
        if(cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        if(cursor != null)
            cursor.close()
    }
    return null
}

private fun isExternalStorageDocument(uri :Uri) :Boolean =
    "com.android.externalstorage.documents" == uri.authority

private fun isDownloadsDocument(uri :Uri) :Boolean =
    "com.android.providers.downloads.documents" == uri.authority

private fun isMediaDocument(uri :Uri) :Boolean = "com.android.providers.media.documents" == uri.authority
private fun isGooglePhotosUri(uri :Uri) :Boolean = "com.google.android.apps.photos.content" == uri.authority