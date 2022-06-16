package com.bkahlert.kommons.test

import com.bkahlert.kommons.t
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test as JupiterTest

class JvmJupiterTestsKtTest {

    @JupiterTest fun test_success() {
        shouldNotThrowAny {
            tests {
                "foo bar" shouldContain "foo"
                "foo bar" shouldContain "bar"
            }
        }
    }

    @JupiterTest fun test_single_fail() {
        shouldThrow<AssertionError> {
            tests {
                "foo bar" shouldContain "baz"
                "foo bar" shouldContain "bar"
            }
        }.message shouldBe """
            "foo bar" should include substring "baz"
        """.trimIndent()
    }

    @JupiterTest fun test_multiple_fails() {
        shouldThrow<AssertionError> {
            tests {
                "foo bar" shouldContain "baz"
                "foo bar" shouldContain "FOO"
            }
        }.message shouldBe """
            
            The following 2 assertions failed:
            1) "foo bar" should include substring "baz"
            ${t}at com.bkahlert.kommons.test.JvmJupiterTestsKtTest.test_multiple_fails(JvmJupiterTestsKtTest.kt:35)
            2) "foo bar" should include substring "FOO"
            ${t}at com.bkahlert.kommons.test.JvmJupiterTestsKtTest.test_multiple_fails(JvmJupiterTestsKtTest.kt:36)
            
        """.trimIndent()
    }
}
