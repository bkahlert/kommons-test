package com.bkahlert.kommons.test

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test as JupiterTest

class JvmJupiterTestKtTest {

    @JupiterTest fun test_success() {
        shouldNotThrowAny {
            test {
                "foo bar" shouldContain "foo"
                "foo bar" shouldContain "bar"
            }
        }
    }

    @JupiterTest fun test_single_fail() {
        shouldThrow<AssertionError> {
            test {
                "foo bar" shouldContain "baz"
                "foo bar" shouldContain "bar"
            }
        }.message shouldBe """
            "foo bar" should include substring "baz"
        """.trimIndent()
    }

    @JupiterTest fun test_multiple_fails() {
        shouldThrow<AssertionError> {
            test {
                "foo bar" shouldContain "baz"
                "foo bar" shouldContain "FOO"
            }
        }.message shouldBe """
            
            The following 2 assertions failed:
            1) "foo bar" should include substring "baz"
            ${t}at com.bkahlert.kommons.test.JvmJupiterTestKtTest.test_multiple_fails(JvmJupiterTestKtTest.kt:34)
            2) "foo bar" should include substring "FOO"
            ${t}at com.bkahlert.kommons.test.JvmJupiterTestKtTest.test_multiple_fails(JvmJupiterTestKtTest.kt:35)
            
        """.trimIndent()
    }
}
