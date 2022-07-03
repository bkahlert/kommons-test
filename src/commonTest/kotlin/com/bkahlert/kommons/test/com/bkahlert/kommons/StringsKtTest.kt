package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.test
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.sequences.shouldContainExactly
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
        charSequence.indexOfOrNull('e') shouldBe 6
        charSequence.indexOfOrNull('E') shouldBe null
        charSequence.indexOfOrNull('e', ignoreCase = true) shouldBe 6
        charSequence.indexOfOrNull('e', startIndex = 7) shouldBe 9
        charSequence.indexOfOrNull('E', startIndex = 7) shouldBe null
        charSequence.indexOfOrNull('e', startIndex = 7, ignoreCase = true) shouldBe 9

        charSequence.indexOfOrNull("e") shouldBe 6
        charSequence.indexOfOrNull("E") shouldBe null
        charSequence.indexOfOrNull("e", ignoreCase = true) shouldBe 6
        charSequence.indexOfOrNull("e", startIndex = 7) shouldBe 9
        charSequence.indexOfOrNull("E", startIndex = 7) shouldBe null
        charSequence.indexOfOrNull("e", startIndex = 7, ignoreCase = true) shouldBe 9
    }

    @Test fun last_index_of_or_null() = test {
        charSequence.lastIndexOfOrNull('e') shouldBe 12
        charSequence.lastIndexOfOrNull('E') shouldBe null
        charSequence.lastIndexOfOrNull('e', ignoreCase = true) shouldBe 12
        charSequence.lastIndexOfOrNull('e', startIndex = 7) shouldBe 6
        charSequence.lastIndexOfOrNull('E', startIndex = 7) shouldBe null
        charSequence.lastIndexOfOrNull('e', startIndex = 7, ignoreCase = true) shouldBe 6

        charSequence.lastIndexOfOrNull("e") shouldBe 12
        charSequence.lastIndexOfOrNull("E") shouldBe null
        charSequence.lastIndexOfOrNull("e", ignoreCase = true) shouldBe 12
        charSequence.lastIndexOfOrNull("e", startIndex = 7) shouldBe 6
        charSequence.lastIndexOfOrNull("E", startIndex = 7) shouldBe null
        charSequence.lastIndexOfOrNull("e", startIndex = 7, ignoreCase = true) shouldBe 6
    }


    @Test fun split_to_sequence() = test {
        "foo X bar x baz".cs.splitToSequence(" X ").shouldContainExactly("foo", "bar x baz")
        "foo X bar x baz".cs.splitToSequence(" X ", " x ").shouldContainExactly("foo", "bar", "baz")
        "foo X bar x baz".cs.splitToSequence(" X ", " x ", keepDelimiters = true).shouldContainExactly("foo X ", "bar x ", "baz")
        "foo X bar x baz".cs.splitToSequence(" X ", ignoreCase = true).shouldContainExactly("foo", "bar", "baz")
        "foo X bar x baz".cs.splitToSequence(" X ", ignoreCase = true, keepDelimiters = true).shouldContainExactly("foo X ", "bar x ", "baz")
        "foo X bar x baz".cs.splitToSequence(" X ", " x ", limit = 2).shouldContainExactly("foo", "bar x baz")

        "foo X bar x baz".splitToSequence(" X ").shouldContainExactly("foo", "bar x baz")
        "foo X bar x baz".splitToSequence(" X ", " x ").shouldContainExactly("foo", "bar", "baz")
        "foo X bar x baz".splitToSequence(" X ", " x ", keepDelimiters = true).shouldContainExactly("foo X ", "bar x ", "baz")
        "foo X bar x baz".splitToSequence(" X ", ignoreCase = true).shouldContainExactly("foo", "bar", "baz")
        "foo X bar x baz".splitToSequence(" X ", ignoreCase = true, keepDelimiters = true).shouldContainExactly("foo X ", "bar x ", "baz")
        "foo X bar x baz".splitToSequence(" X ", " x ", limit = 2).shouldContainExactly("foo", "bar x baz")
    }
}

internal val String.cs: CharSequence get() = StringBuilder(this)

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
