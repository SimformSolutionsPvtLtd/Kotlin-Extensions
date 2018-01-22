package com.extensions.validation

@Suppress("unused")
enum class ValidationError {
    MINIMUM_LENGTH,
    MAXIMUM_LENGTH,
    AT_LEAST_ONE_UPPER_CASE,
    AT_LEAST_ONE_LOWER_CASE,
    ALL_LOWER_CASE,
    ALL_UPPER_CASE,
    ONLY_NUMBERS,
    NON_EMPTY,
    NO_NUMBERS,
    EMAIL,
    AT_LEAST_ONE_NUMBER,
    STARTS_WITH_NON_NUMBER,
    NO_SPECIAL_CHARACTER,
    AT_LEAST_ONE_SPECIAL_CHARACTER,
    CONTAINS,
    DOES_NOT_CONTAINS,
    STARTS_WITH,
    ENDS_WITH
}