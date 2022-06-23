package com.bkahlert.kommons.test

import io.kotest.assertions.assertSoftly

/** Asserts the specified [softAssertions]. */
public inline fun <R> test(softAssertions: () -> R) {
    assertSoftly(softAssertions)
}

@Deprecated("use test", replaceWith = ReplaceWith("test(softAssertions)", "com.bkahlert.kommons.test.test"))
/** Asserts the specified [softAssertions]. */
public inline fun <R> tests(softAssertions: () -> R) {
    assertSoftly(softAssertions)
}
