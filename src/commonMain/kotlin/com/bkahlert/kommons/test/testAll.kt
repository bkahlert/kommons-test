package com.bkahlert.kommons.test

import io.kotest.inspectors.forAll

/** Asserts the specified [assertions] softly for each of the specified [subjects]. */
public inline fun <T, R> testAll(vararg subjects: T, assertions: (T) -> R) {
    require(subjects.isNotEmpty()) { "At least one subject must be provided for testing." }
    subjects.forAll {
        tests { assertions(it) }
    }
}

/** Asserts the specified [assertions] softly for each element of this [Collection]. */
public inline fun <T, R> Iterable<T>.testAll(assertions: (T) -> R) {
    val subjects = toList()
    require(subjects.isNotEmpty()) { "At least one subject must be provided for testing." }
    subjects.forAll {
        tests { assertions(it) }
    }
}

/** Asserts the specified [assertions] softly for each element of this [Sequence]. */
public inline fun <T, R> Sequence<T>.testAll(assertions: (T) -> R) {
    toList().testAll(assertions)
}

/** Asserts the specified [assertions] softly for each entry of this [Map]. */
public inline fun <K, V, R> Map<K, V>.testAll(assertions: (Map.Entry<K, V>) -> R) {
    require(isNotEmpty()) { "At least one subject must be provided for testing." }
    forAll {
        tests { assertions(it) }
    }
}
