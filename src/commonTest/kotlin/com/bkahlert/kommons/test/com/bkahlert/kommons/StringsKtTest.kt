package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.test
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldStartWith
import kotlin.test.Test

class StringsKtTest {

    @Test fun ansi_contained() = test {
        charSequence.ansiContained shouldBe false
        string.ansiContained shouldBe false
        emptyCharSequence.ansiContained shouldBe false
        emptyString.ansiContained shouldBe false
        blankCharSequence.ansiContained shouldBe false
        blankString.ansiContained shouldBe false
        ansiCsiCharSequence.ansiContained shouldBe true
        ansiCsiString.ansiContained shouldBe true
        ansiOscCharSequence.ansiContained shouldBe true
        ansiOscString.ansiContained shouldBe true
    }

    @Test fun ansi_removed() = test {
        string.ansiRemoved shouldBe string
        emptyString.ansiRemoved shouldBe emptyString
        blankString.ansiRemoved shouldBe blankString
        ansiCsiString.ansiRemoved shouldBe "bold and blue"
        ansiOscString.ansiRemoved shouldBe "↗ link"
    }

    @Test fun with_prefix() = test {
        string.withPrefix(string) shouldBe string
        string.withPrefix("str") shouldBe string
        string.withPrefix("str-") shouldBe "str-$string"
    }

    @Test fun with_suffix() = test {
        string.withSuffix(string) shouldBe string
        string.withSuffix("ing") shouldBe string
        string.withSuffix("-ing") shouldBe "$string-ing"
    }

    @Test fun with_random_suffix() = test {
        charSequence.withRandomSuffix() should {
            it shouldMatch Regex("$charSequence--[\\da-zA-Z]{4}")
            it shouldStartWith charSequence
            it.withRandomSuffix() shouldBe it
        }
        string.withRandomSuffix() should {
            it shouldMatch Regex("$string--[\\da-zA-Z]{4}")
            it shouldStartWith string
            it.withRandomSuffix() shouldBe it
        }
    }

    @Test fun random_string() = test {
        randomString() shouldHaveLength 16
        randomString(7) shouldHaveLength 7

        val allowedByDefault = (('0'..'9') + ('a'..'z') + ('A'..'Z')).toList()
        randomString(100).forAll { allowedByDefault shouldContain it }

        randomString(100, 'A', 'B').forAll { listOf('A', 'B') shouldContain it }
    }

    @Test fun index_of_or_null() = test {
        charSequence.indexOfOrNull('h') shouldBe 1
        string.indexOfOrNull('t') shouldBe 1

        charSequence.indexOfOrNull('x') shouldBe null
        string.indexOfOrNull('x') shouldBe null

        charSequence.indexOfOrNull("seq") shouldBe 5
        string.indexOfOrNull("ring") shouldBe 2

        charSequence.indexOfOrNull("xyz") shouldBe null
        string.indexOfOrNull("xyz") shouldBe null
    }

    @Test fun is_multiline() = test {
        "".isMultiline shouldBe false
        "foo".isMultiline shouldBe false
        LineSeparators.Common.forAll {
            it.isMultiline shouldBe true
            "${it}foo".isMultiline shouldBe true
            "foo${it}".isMultiline shouldBe true
            "foo${it}bar".isMultiline shouldBe true
            "foo${it}bar${it}baz".isMultiline shouldBe true
        }
        LineSeparators.Uncommon.forAll {
            it.isMultiline shouldBe false
            "${it}foo".isMultiline shouldBe false
            "foo${it}".isMultiline shouldBe false
            "foo${it}bar".isMultiline shouldBe false
            "foo${it}bar${it}baz".trimIndent().isMultiline shouldBe false
        }
    }
}

internal val charSequence: CharSequence = StringBuilder("char sequence")
internal val emptyCharSequence: CharSequence = StringBuilder()
internal val blankCharSequence: CharSequence = StringBuilder("   ")

internal const val string: String = "string"
internal const val emptyString: String = ""
internal const val blankString: String = "   "


/** [String] containing CSI (`control sequence intro`) escape sequences */
internal const val ansiCsiString: String = "[1mbold [34mand blue[0m"

/** [CharSequence] containing CSI (`control sequence intro`) escape sequences */
internal val ansiCsiCharSequence: CharSequence = StringBuilder().append(ansiCsiString)

/** [String] containing CSI (`control sequence intro`) escape sequences */
internal const val ansiOscString: String = "[34m↗(B[m ]8;;https://example.com\\link]8;;\\"

/** [CharSequence] containing CSI (`control sequence intro`) escape sequences */
internal val ansiOscCharSequence: CharSequence = StringBuilder().append(ansiOscString)
