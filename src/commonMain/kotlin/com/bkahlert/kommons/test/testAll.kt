package com.bkahlert.kommons.test

import io.kotest.inspectors.forAll

/** Asserts the specified [softAssertions] for each of the specified [subjects]. */
public inline fun <T, R> testAll(vararg subjects: T, softAssertions: (T) -> R) {
    subjects.asList().testAll(softAssertions)
}

/** Asserts the specified [softAssertions] for each element of this [Collection]. */
public inline fun <T, R> Iterable<T>.testAll(softAssertions: (T) -> R) {
    val subjects = toList()
    require(subjects.isNotEmpty()) { "At least one subject must be provided for testing." }
    subjects.forAll {
        test { softAssertions(it) }
    }
}

/** Asserts the specified [softAssertions] for each element of this [Sequence]. */
public inline fun <T, R> Sequence<T>.testAll(softAssertions: (T) -> R) {
    toList().testAll(softAssertions)
}

/** Asserts the specified [softAssertions] for each entry of this [Map]. */
public inline fun <K, V, R> Map<K, V>.testAll(softAssertions: (Map.Entry<K, V>) -> R) {
    entries.testAll(softAssertions)
}
