@file:Suppress("SpellCheckingInspection")

package com.bkahlert.kommons.test

import com.bkahlert.kommons.test.com.bkahlert.kommons.LineSeparators
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class MatchesGlobKtTest {

    @Test fun should_match_glob() {
        shouldNotThrowAny {
            matchGlobInput shouldMatchGlob """
                foo.*
                bar[0]++
                baz did **
            """.trimIndent()
        }
    }

    @Test fun should_match_glob__failure() {
        shouldThrow<AssertionError> {
            matchGlobInput shouldMatchGlob """
                foo.*
                bar[0]++
                baz did *
            """.trimIndent()
        }.message shouldBe """
            ""${'"'}
            foo.bar()
            bar[0]++
            baz did throw a RuntimeException
                at SomeFile.kt:42
            ""${'"'}
            should match the following glob pattern with (wildcard: *, multiline wildcard: **, line separators: CRLF (\r\n), LF (\n), CR (\r))
            ""${'"'}
            foo.*
            bar[0]++
            baz did *
            ""${'"'}
        """.trimIndent()
    }


    @Test fun should_not_match_glob() {
        shouldNotThrowAny {
            matchGlobInput shouldNotMatchGlob """
                foo.*
                bar[0]++
                baz did *
            """.trimIndent()
        }
    }

    @Test fun should_not_match_glob__failure() {
        shouldThrow<AssertionError> {
            matchGlobInput shouldNotMatchGlob """
                foo.*
                bar[0]++
                baz did **
            """.trimIndent()
        }.message shouldBe """
            ""${'"'}
            foo.bar()
            bar[0]++
            baz did throw a RuntimeException
                at SomeFile.kt:42
            ""${'"'}
            should not match the following glob pattern with (wildcard: *, multiline wildcard: **, line separators: CRLF (\r\n), LF (\n), CR (\r))
            ""${'"'}
            foo.*
            bar[0]++
            baz did **
            ""${'"'}
        """.trimIndent()
    }


    @Test fun match_glob() {
        shouldNotThrowAny {
            matchGlobInput should matchGlob(
                """
                foo.{}
                bar[0]++
                baz did {{}}
            """.trimIndent(),
                wildcard = "{}",
                multilineWildcard = "{{}}",
                *LineSeparators.Unicode, "🫠",
            )
        }
    }

    @Test fun match_glob__failure() {
        @Suppress("LongLine")
        shouldThrow<AssertionError> {
            matchGlobInput should matchGlob(
                """
                foo.{}
                bar[0]++
                baz did {}
            """.trimIndent(),
                wildcard = "{}",
                multilineWildcard = "{{}}",
                *LineSeparators.Unicode, "🫠",
            )
        }.message shouldBe """
            ""${'"'}
            foo.bar()
            bar[0]++
            baz did throw a RuntimeException
                at SomeFile.kt:42
            ""${'"'}
            should match the following glob pattern with (wildcard: {}, multiline wildcard: {{}}, line separators: CRLF (\r\n), LF (\n), CR (\r), NEL (\u0085), PS (\u2029), LS (\u2028), Unknown (0xf09faba0))
            ""${'"'}
            foo.{}
            bar[0]++
            baz did {}
            ""${'"'}
        """.trimIndent()
    }

    @Test fun match_glob__singleLine_failure() {
        @Suppress("LongLine")
        shouldThrow<AssertionError> {
            "foo.bar()" should matchGlob("bar.{}")
        }.message shouldBe """
            "foo.bar()"
            should match the following glob pattern with (wildcard: *, multiline wildcard: **, line separators: CRLF (\r\n), LF (\n), CR (\r))
            "bar.{}"
        """.trimIndent()
    }
}

internal val matchGlobInput = """
    foo.bar()
    bar[0]++
    baz did throw a RuntimeException
        at SomeFile.kt:42
""".trimIndent()
