package com.bkahlert.kommons.test

import com.bkahlert.kommons.t
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainIgnoringCase
import kotlin.test.Test as KotlinTest

class JvmKotlinTestAllKtTest {

    @KotlinTest fun test_empty() {
        shouldThrow<IllegalArgumentException> {
            testAll<Any?, Any?> { }
        }
    }

    @KotlinTest fun test_success() {
        shouldNotThrowAny {
            testAll("foo bar", "FOO BAR") {
                it shouldContainIgnoringCase "foo"
                it shouldContainIgnoringCase "bar"
            }
        }
    }

    @KotlinTest fun test_single_fail_single_subject() {
        shouldThrow<AssertionError> {
            testAll("foo bar", "FOO BAR") {
                it shouldContain "foo"
                it shouldContainIgnoringCase "bar"
            }
        }.message shouldBe """
            1 elements passed but expected 2

            The following elements passed:
            foo bar

            The following elements failed:
            "FOO BAR" => "FOO BAR" should include substring "foo"
        """.trimIndent()
    }

    @KotlinTest fun test_single_fail_multiple_subjects() {
        shouldThrow<AssertionError> {
            testAll("foo bar", "FOO BAR") {
                it shouldContainIgnoringCase "baz"
                it shouldContainIgnoringCase "bar"
            }
        }.message shouldBe """
            0 elements passed but expected 2

            The following elements passed:
            --none--

            The following elements failed:
            "foo bar" => "foo bar" should contain the substring "baz" (case insensitive)
            "FOO BAR" => "FOO BAR" should contain the substring "baz" (case insensitive)
        """.trimIndent()
    }

    @KotlinTest fun test_multiple_fails_multiple_subjects() {
        shouldThrow<AssertionError> {
            testAll("foo bar", "FOO BAR") {
                it shouldContain "baz"
                it shouldContain "BAZ"
            }
        }.message shouldBe """
            0 elements passed but expected 2

            The following elements passed:
            --none--
            
            The following elements failed:
            "foo bar" => 
            The following 2 assertions failed:
            1) "foo bar" should include substring "baz"
            ${t}at com.bkahlert.kommons.test.JvmKotlinTestAllKtTest.test_multiple_fails_multiple_subjects(JvmKotlinTestAllKtTest.kt:66)
            2) "foo bar" should include substring "BAZ"
            ${t}at com.bkahlert.kommons.test.JvmKotlinTestAllKtTest.test_multiple_fails_multiple_subjects(JvmKotlinTestAllKtTest.kt:67)
            
            "FOO BAR" => 
            The following 2 assertions failed:
            1) "FOO BAR" should include substring "baz"
            ${t}at com.bkahlert.kommons.test.JvmKotlinTestAllKtTest.test_multiple_fails_multiple_subjects(JvmKotlinTestAllKtTest.kt:66)
            2) "FOO BAR" should include substring "BAZ"
            ${t}at com.bkahlert.kommons.test.JvmKotlinTestAllKtTest.test_multiple_fails_multiple_subjects(JvmKotlinTestAllKtTest.kt:67)
            
        """.trimIndent()
    }
}
