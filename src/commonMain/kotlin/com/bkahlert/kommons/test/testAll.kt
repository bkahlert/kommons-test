package com.bkahlert.kommons.test

import io.kotest.inspectors.forAll

/** Asserts the specified [assertions] for each of the specified [subjects] softly. */
public inline fun <T, R> testAll(vararg subjects: T, assertions: (T) -> R) {
    subjects.forAll {
        tests { assertions(it) }
    }
}
