package com.extensions.interfaces

@Suppress("unused")
interface F1<in A> {
    operator fun invoke(object1: A)
}