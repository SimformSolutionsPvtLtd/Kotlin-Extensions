package com.extensions.interfaces

@Suppress("unused")
interface F3<in A, in B, in C> {
    operator fun invoke(object1: A, object2: B, object3: C)
}