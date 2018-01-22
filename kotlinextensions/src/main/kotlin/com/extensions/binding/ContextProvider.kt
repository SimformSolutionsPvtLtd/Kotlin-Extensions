package com.extensions.binding

import android.content.Context

interface ContextProvider {

    fun provideContext(): Context
}