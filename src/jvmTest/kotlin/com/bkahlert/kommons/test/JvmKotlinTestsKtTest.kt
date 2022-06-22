package com.bkahlert.kommons.test

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test as KotlinTest

class JvmKotlinTestsKtTest {

    @KotlinTest fun test_success() {
        shouldNotThrowAny {
            tests {
                "foo bar" shouldContain "foo"
                "foo bar" shouldContain "bar"
            }
        }
    }

    @KotlinTest fun test_single_fail() {
        shouldThrow<AssertionError> {
            tests {
                "foo bar" shouldContain "baz"
                "foo bar" shouldContain "bar"
            }
        }.message shouldBe """
            "foo bar" should include substring "baz"
        """.trimIndent()
    }

    @KotlinTest fun test_multiple_fails() {
        shouldThrow<AssertionError> {
            tests {
                "foo bar" shouldContain "baz"
                "foo bar" shouldContain "FOO"
            }
        }.message shouldBe """
            
            The following 2 assertions failed:
            1) "foo bar" should include substring "baz"
            ${t}at com.bkahlert.kommons.test.JvmKotlinTestsKtTest.test_multiple_fails(JvmKotlinTestsKtTest.kt:34)
            2) "foo bar" should include substring "FOO"
            ${t}at com.bkahlert.kommons.test.JvmKotlinTestsKtTest.test_multiple_fails(JvmKotlinTestsKtTest.kt:35)
            
        """.trimIndent()
    }
}
