package com.bkahlert.kommons.test

import io.kotest.assertions.assertSoftly

@Deprecated("use testAll", replaceWith = ReplaceWith("testAll(softAssertions)", "com.bkahlert.kommons.test.testAll"))
/** Asserts the specified [softAssertions]. */
public inline fun <R> test(softAssertions: () -> R) {
    assertSoftly(softAssertions)
}

@Deprecated("use testAll", replaceWith = ReplaceWith("testAll(softAssertions)", "com.bkahlert.kommons.test.testAll"))
/** Asserts the specified [softAssertions]. */
public inline fun <R> tests(softAssertions: () -> R) {
    assertSoftly(softAssertions)
}
