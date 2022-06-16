package com.bkahlert.kommons.test

import io.kotest.assertions.assertSoftly

/** Asserts the specified [assertions] softly. */
public inline fun <R> tests(assertions: () -> R) {
    assertSoftly(assertions)
}
//
///** Asserts the specified [assertions] softly. */
//inline fun <T, R> testAll(vararg subjects: T, assertions: (T) -> R) {
//    tests {
//        subjects.forAll {
//            assertions(it)
//        }
//    }
//}
