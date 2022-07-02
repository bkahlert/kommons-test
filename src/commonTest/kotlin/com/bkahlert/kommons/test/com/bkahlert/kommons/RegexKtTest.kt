package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.test
import io.kotest.assertions.withClue
import io.kotest.inspectors.forAll
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlin.test.Test

@Suppress(
    "RegExpRepeatedSpace",
    "RegExpSingleCharAlternation",
    "RegExpOctalEscape",
    "RegExpDuplicateAlternationBranch",
    "RegExpEscapedMetaCharacter",
    "RegExpEmptyAlternationBranch",
    "RegExpAnonymousGroup",
    "RegExpUnexpectedAnchor",
    "RegExpDuplicateCharacterInClass",
    "RegExpRedundantNestedCharacterClass",
    "RegExpUnnecessaryNonCapturingGroup",
    "RegExpSuspiciousBackref",
    "RegExpSimplifiable",
    "RegExpRedundantClassElement",
)
class RegexKtTest {

    @Test fun from_literal_alternates() = test {
        Regex.fromLiteralAlternates() shouldBe Regex("")
        Regex.fromLiteralAlternates("foo") shouldBe Regex(Regex.escape("foo"))
        Regex.fromLiteralAlternates("foo", "bar") shouldBe Regex("${Regex.escape("foo")}|${Regex.escape("bar")}")

        Regex.fromLiteralAlternates(emptyList()) shouldBe Regex("")
        Regex.fromLiteralAlternates(listOf("foo")) shouldBe Regex(Regex.escape("foo"))
        Regex.fromLiteralAlternates(listOf("foo", "bar")) shouldBe Regex("${Regex.escape("foo")}|${Regex.escape("bar")}")
    }

    @Test fun from_glob() = test {
        val input = """
            foo.bar()
            bar[0]++
            baz did throw a RuntimeException
                at SomeFile.kt:42
        """.trimIndent()

        Regex.fromGlob(
            """
                foo.*
                bar[0]++
                baz did **
            """.trimIndent()
        ) should { regex ->
            withClue("matched by glob pattern") {
                regex.matches(input) shouldBe true
            }

            withClue("line breaks matched by any common line break") {
                LineSeparators.Common.forAll { sep ->
                    regex.matches(input.replace("\n", sep)) shouldBe true
                }
            }
            withClue("line breaks matched by no uncommon line break") {
                LineSeparators.Uncommon.forAll { sep ->
                    regex.matches(input.replace("\n", sep)) shouldBe false
                }
            }
        }

        Regex.fromGlob(
            """
                foo.*
                bar[0]++
                baz did **
            """.trimIndent(),
            lineSeparators = LineSeparators.Unicode
        ) should { regex ->
            regex.matches(input) shouldBe true

            withClue("line breaks matched by any unicode line break") {
                LineSeparators.Unicode.forAll { sep ->
                    regex.matches(input.replace("\n", sep)) shouldBe true
                }
            }
        }

        Regex.fromGlob(
            """
                foo.*
                bar[0]++
                baz did *
            """.trimIndent(),
        ) should { regex ->
            withClue("line breaks not matched by simple wildcard") {
                regex.matches(input) shouldBe false
            }
        }

        Regex.fromGlob(
            """
                foo.{}
                bar[0]++
                baz did {{}}
            """.trimIndent(),
            wildcard = "{}",
            multilineWildcard = "{{}}",
        ) should { regex ->
            withClue("matched by glob pattern with custom wildcards") {
                regex.matches(input) shouldBe true
            }
        }
    }
}
