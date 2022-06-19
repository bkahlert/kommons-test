package com.bkahlert.kommons.test

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

class TestsKtTest {

    @Test fun test_success() {
        shouldNotThrowAny {
            tests {
                "foo bar" shouldContain "foo"
                "foo bar" shouldContain "bar"
            }
        }
    }

    @Test fun test_single_fail() {
        shouldThrow<AssertionError> {
            tests {
                "foo bar" shouldContain "baz"
                "foo bar" shouldContain "bar"
            }
        }.message shouldBe """
            "foo bar" should include substring "baz"
        """.trimIndent()
    }

    @Test fun test_multiple_fails() {
        shouldThrow<AssertionError> {
            tests {
                "foo bar" shouldContain "baz"
                "foo bar" shouldContain "FOO"
            }
        }.message
            .shouldContain(
                """
                    The following 2 assertions failed:
                """.trimIndent()
            )
            .shouldContain(
                """
                    1) "foo bar" should include substring "baz"
                """.trimIndent()
            )
            .shouldContain(
                """
                    2) "foo bar" should include substring "FOO"
                """.trimIndent()
            )
    }
}