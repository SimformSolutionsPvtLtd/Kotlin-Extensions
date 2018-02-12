package com.extensions.dialogs

import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment as SupportFragment

typealias AlertBuilderFactory<D> = (Context) -> AlertBuilder<D>

inline fun <D : DialogInterface> SupportFragment.alert(
        noinline factory:AlertBuilderFactory<D>,
        message: String,
        title: String? = null,
        noinline init: (AlertBuilder<D>.() -> Unit)? = null
) = context!!.alert(factory, message, title, init)

inline fun <D : DialogInterface> Fragment.alert(
        noinline factory:AlertBuilderFactory<D>,
        message: String,
        title: String? = null,
        noinline init: (AlertBuilder<D>.() -> Unit)? = null
) = activity.alert(factory, message, title, init)

fun <D : DialogInterface> Context.alert(
        factory:AlertBuilderFactory<D>,
        message: String,
        title: String? = null,
        init: (AlertBuilder<D>.() -> Unit)? = null
):AlertBuilder<D> {
    return factory(this).apply {
        if (title != null) {
            this.title = title
        }
        this.message = message
        if (init != null) init()
    }
}

inline fun <D : DialogInterface> SupportFragment.alert(
        noinline factory:AlertBuilderFactory<D>,
        message: Int,
        title: Int? = null,
        noinline init: (AlertBuilder<D>.() -> Unit)? = null
) = context!!.alert(factory, message, title, init)

inline fun <D : DialogInterface> Fragment.alert(
        noinline factory:AlertBuilderFactory<D>,
        message: Int,
        title: Int? = null,
        noinline init: (AlertBuilder<D>.() -> Unit)? = null
) = activity.alert(factory, message, title, init)

fun <D : DialogInterface> Context.alert(
        factory:AlertBuilderFactory<D>,
        messageResource: Int,
        titleResource: Int? = null,
        init: (AlertBuilder<D>.() -> Unit)? = null
):AlertBuilder<D> {
    return factory(this).apply {
        if (titleResource != null) {
            this.titleResource = titleResource
        }
        this.messageResource = messageResource
        if (init != null) init()
    }
}

inline fun <D : DialogInterface> SupportFragment.alert(
        noinline factory:AlertBuilderFactory<D>,
        noinline init: AlertBuilder<D>.() -> Unit
) = context!!.alert(factory, init)

inline fun <D : DialogInterface> Fragment.alert(
        noinline factory:AlertBuilderFactory<D>,
        noinline init: AlertBuilder<D>.() -> Unit
) = activity.alert(factory, init)

fun <D : DialogInterface> Context.alert(
        factory:AlertBuilderFactory<D>,
        init: AlertBuilder<D>.() -> Unit
):AlertBuilder<D> = factory(this).apply { init() }