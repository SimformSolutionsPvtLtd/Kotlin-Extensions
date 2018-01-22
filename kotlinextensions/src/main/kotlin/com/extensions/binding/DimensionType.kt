package com.extensions.binding

import android.content.Context

enum class DimensionType {
    PX {
        override fun convert(context: Context, pixels: Float): Float {
            return pixels
        }
    },
    DP {
        override fun convert(context: Context, pixels: Float): Float {
            return pixels / context.resources.displayMetrics.density
        }
    },
    SP {
        override fun convert(context: Context, pixels: Float): Float {
            return pixels / context.resources.displayMetrics.scaledDensity
        }
    };

    abstract fun convert(context: Context, pixels: Float): Float
}