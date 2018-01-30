package com.extensions.dialogs

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import kotlin.DeprecationLevel.ERROR

val Appcompat: AlertBuilderFactory<AlertDialog> = ::AppcompatAlertBuilder

internal class AppcompatAlertBuilder(override val ctx: Context) : AlertBuilder<AlertDialog> {
    private val builder = AlertDialog.Builder(ctx)

    override var title: CharSequence
        @Deprecated("Property does not have a getter", level = ERROR) get() = throw Exception("Property does not have a getter")
        set(value) { builder.setTitle(value) }

    override var titleResource: Int
        @Deprecated("Property does not have a getter", level = ERROR) get() = throw Exception("Property does not have a getter")
        set(value) { builder.setTitle(value) }

    override var message: CharSequence
        @Deprecated("Property does not have a getter", level = ERROR) get() = throw Exception("Property does not have a getter")
        set(value) { builder.setMessage(value) }

    override var messageResource: Int
        @Deprecated("Property does not have a getter", level = ERROR) get() = throw Exception("Property does not have a getter")
        set(value) { builder.setMessage(value) }

    override var icon: Drawable
        @Deprecated("Property does not have a getter", level = ERROR) get() = throw Exception("Property does not have a getter")
        set(value) { builder.setIcon(value) }

    override var iconResource: Int
        @Deprecated("Property does not have a getter", level = ERROR) get() = throw Exception("Property does not have a getter")
        set(value) { builder.setIcon(value) }

    override var isCancelable: Boolean
        @Deprecated("Property does not have a getter", level = ERROR) get() = throw Exception("Property does not have a getter")
        set(value) { builder.setCancelable(value) }

    override fun onCancelled(handler: (DialogInterface) -> Unit) {
        builder.setOnCancelListener(handler)
    }

    override fun onKeyPressed(handler: (dialog: DialogInterface, keyCode: Int, e: KeyEvent) -> Boolean) {
        builder.setOnKeyListener(handler)
    }

    override fun positiveButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit) {
        builder.setPositiveButton(buttonText) { dialog, _ -> onClicked(dialog) }
    }

    override fun positiveButton(buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit) {
        builder.setPositiveButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
    }

    override fun negativeButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit) {
        builder.setNegativeButton(buttonText) { dialog, _ -> onClicked(dialog) }
    }

    override fun negativeButton(buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit) {
        builder.setNegativeButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
    }

    override fun neutralPressed(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit) {
        builder.setNeutralButton(buttonText) { dialog, _ -> onClicked(dialog) }
    }

    override fun neutralPressed(buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit) {
        builder.setNeutralButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
    }

    override fun items(items: List<CharSequence>, onItemSelected: (dialog: DialogInterface, index: Int) -> Unit) {
        builder.setItems(Array(items.size) { i -> items[i].toString() }) { dialog, which ->
            onItemSelected(dialog, which)
        }
    }

    override fun <T> items(items: List<T>, onItemSelected: (dialog: DialogInterface, item: T, index: Int) -> Unit) {
        builder.setItems(Array(items.size) { i -> items[i].toString() }) { dialog, which ->
            onItemSelected(dialog, items[which], which)
        }
    }

    override fun build(): AlertDialog = builder.create()

    override fun show(): AlertDialog = builder.show()
}