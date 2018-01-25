package com.extensions.os

import android.os.Build

inline fun doWithApi(api: Api, block: () -> Unit) {
    doWithApi(api.sdkCode, block)
}

inline fun doWithApi(sdkCode: Int, block: () -> Unit) {
    if (Build.VERSION.SDK_INT == sdkCode) {
        block()
    }
}

inline fun doWithAtLeastApi(api: Api, block: () -> Unit) {
    doWithAtLeastApi(api.sdkCode, block)
}

inline fun doWithAtLeastApi(sdkCode: Int, block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= sdkCode) {
        block()
    }
}

inline fun doWithAtMostApi(api: Api, block: () -> Unit) {
    doWithAtMostApi(api.sdkCode, block)
}

inline fun doWithAtMostApi(sdkCode: Int, block: () -> Unit) {
    if (Build.VERSION.SDK_INT <= sdkCode) {
        block()
    }
}

inline fun doWithHigherApi(api: Api, block: () -> Unit) {
    doWithHigherApi(api.sdkCode, block)
}

inline fun doWithHigherApi(sdkCode: Int, block: () -> Unit) {
    if (Build.VERSION.SDK_INT > sdkCode) {
        block()
    }
}

inline fun doWithLowerApi(api: Api, block: () -> Unit) {
    doWithLowerApi(api.sdkCode, block)
}

inline fun doWithLowerApi(sdkCode: Int, block: () -> Unit) {
    if (Build.VERSION.SDK_INT < sdkCode) {
        block()
    }
}

fun isLollipop(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun isPreLollipop(): Boolean =
    Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP

enum class Api(val sdkCode: Int) {
    BASE(1),
    BASE_1_1(2),
    CUPCAKE(3),
    CUR_DEVELOPMENT(10000),
    DONUT(4),
    ECLAIR(5),
    ECLAIR_0_1(6),
    ECLAIR_MR1(7),
    FROYO(8),
    GINGERBREAD(9),
    GINGERBREAD_MR1(10),
    HONEYCOMB(11),
    HONEYCOMB_MR1(12),
    HONEYCOMB_MR2(13),
    ICE_CREAM_SANDWICH(14),
    ICE_CREAM_SANDWICH_MR1(15),
    JELLY_BEAN(16),
    JELLY_BEAN_MR1(17),
    JELLY_BEAN_MR2(18),
    KITKAT(19),
    KITKAT_WATCH(20),
    LOLLIPOP(21),
    LOLLIPOP_MR1(22),
    M(23),
    N(24),
    N_MR1(25),
    O(26)
}