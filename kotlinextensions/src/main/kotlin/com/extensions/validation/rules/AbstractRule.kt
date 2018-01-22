package com.extensions.validation.rules

import com.extensions.validation.Rule

@Suppress("unused")
abstract class AbstractRule<in T> protected constructor(override val errorMessage: String) : Rule<T>