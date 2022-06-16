package com.bkahlert.kommons.test

import io.kotest.inspectors.forAll

/** Asserts the specified [assertions] softly for each [Enum] entry. */
public inline fun <reified E : Enum<E>> testEnum(assertions: (E) -> Unit) {
    enumValues<E>().forAll {
        tests { assertions(it) }
    }
}
