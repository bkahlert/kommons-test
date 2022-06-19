package com.bkahlert.kommons

import com.bkahlert.kommons.test.tests
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class StringsKtTest {

    @Test fun ansi_contained() = tests {
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

    @Test fun ansi_removed() = tests {
        string.ansiRemoved shouldBe string
        emptyString.ansiRemoved shouldBe emptyString
        blankString.ansiRemoved shouldBe blankString
        ansiCsiString.ansiRemoved shouldBe "bold and blue"
        ansiOscString.ansiRemoved shouldBe "↗ link"
    }

    @Test fun with_prefix() = tests {
        string.withPrefix(string) shouldBe string
        string.withPrefix("str") shouldBe string
        string.withPrefix("str-") shouldBe "str-$string"
    }

    @Test fun with_suffix() = tests {
        string.withSuffix(string) shouldBe string
        string.withSuffix("ing") shouldBe string
        string.withSuffix("-ing") shouldBe "$string-ing"
    }

    @Test fun index_of_or_null() = tests {
        charSequence.indexOfOrNull('h') shouldBe 1
        string.indexOfOrNull('t') shouldBe 1

        charSequence.indexOfOrNull('x') shouldBe null
        string.indexOfOrNull('x') shouldBe null

        charSequence.indexOfOrNull("seq") shouldBe 5
        string.indexOfOrNull("ring") shouldBe 2

        charSequence.indexOfOrNull("xyz") shouldBe null
        string.indexOfOrNull("xyz") shouldBe null
    }

    @Test fun is_multiline() = tests {
        "".isMultiline shouldBe false
        "foo".isMultiline shouldBe false
        arrayOf("\u000D\u000A", "\u000A", "\u000D").forAll {
            it.isMultiline shouldBe true
            "${it}foo".isMultiline shouldBe true
            "foo${it}".isMultiline shouldBe true
            "foo${it}bar".isMultiline shouldBe true
            "foo${it}bar${it}baz".isMultiline shouldBe true
        }
        arrayOf("\u0085", "\u2029", "\u2028").forAll {
            it.isMultiline shouldBe false
            "${it}foo".isMultiline shouldBe false
            "foo${it}".isMultiline shouldBe false
            "foo${it}bar".isMultiline shouldBe false
            "foo${it}bar${it}baz".isMultiline shouldBe false
        }
    }
}


internal val emptyException = RuntimeException()
internal val runtimeException = RuntimeException(
    "Something happened\n" +
        " ➜ A dump has been written to:\n" +
        "   - file:///var/folders/…/file.log (unchanged)\n" +
        "   - file:///var/folders/…/file.ansi-removed.log (ANSI escape/control sequences removed)\n" +
        " ➜ The last lines are:\n" +
        "    raspberry\n" +
        "    Login incorrect\n" +
        "    raspberrypi login:"
)

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
