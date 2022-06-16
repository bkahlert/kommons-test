package com.bkahlert.kommons.test

import io.kotest.assertions.assertSoftly

/** Asserts the specified [assertions] softly. */
public inline fun <R> tests(assertions: () -> R) {
    assertSoftly(assertions)
}
