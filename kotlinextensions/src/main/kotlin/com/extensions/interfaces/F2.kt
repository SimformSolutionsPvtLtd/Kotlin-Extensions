package com.extensions.interfaces

@Suppress("unused")
interface F2<in A, in B> {
    operator fun invoke(object1: A, object2: B)
}