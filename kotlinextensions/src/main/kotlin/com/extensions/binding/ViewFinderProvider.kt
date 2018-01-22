package com.extensions.binding

import android.view.View

interface ViewFinderProvider {

    fun provideViewFinder(): ViewFinder<View>
}