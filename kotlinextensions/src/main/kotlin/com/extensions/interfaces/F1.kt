package com.extensions.interfaces


interface F1<in A> {
    operator fun invoke(object1: A)
}