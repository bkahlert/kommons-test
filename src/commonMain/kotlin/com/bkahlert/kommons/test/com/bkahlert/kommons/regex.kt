package com.bkahlert.kommons.test.com.bkahlert.kommons

/** Returns a [Regex] that matches one of the specified [literals]. */
internal fun Regex.Companion.fromLiteralAlternates(vararg literals: String): Regex =
    fromLiteralAlternates(literals.asList())

/** Returns a [Regex] that matches one of the specified [literals]. */
internal fun Regex.Companion.fromLiteralAlternates(literals: Collection<String>): Regex =
    Regex(literals.joinToString("|") { escape(it) })

private const val anyCharacterPattern = "[\\s\\S]"
private const val anyNonLineSeparatorPattern = "."

/**
 * Returns a [Regex] that matches the same way the specified glob-like [pattern],
 * using the specified [wildcard] (default: `*`) to match within lines,
 * and the specified [multilineWildcard] (default: `**`) to match across lines,
 * would.
 *
 * The returned regex render all specified [lineSeparators] (default: [LineSeparators.Common])
 * matchable by any of the specified [lineSeparators].
 *
 * @see [CharSequence.matchesGlob]
 */
internal fun Regex.Companion.fromGlob(
    pattern: CharSequence,
    wildcard: String = "*",
    multilineWildcard: String = "**",
    vararg lineSeparators: String = LineSeparators.Common,
): Regex {
    val anyLineSepPattern = "(?:${Regex.fromLiteralAlternates(*lineSeparators).pattern})"
    val anyNumberLineSepPattern = "$anyLineSepPattern*"
    val multilineWildcardRegex = Regex("$anyNumberLineSepPattern${escape(multilineWildcard)}$anyNumberLineSepPattern")
    return pattern
        .split(multilineWildcardRegex)
        .joinToString("$anyCharacterPattern*") { multilineWildcardFenced ->
            multilineWildcardFenced.split(wildcard).joinToString("$anyNonLineSeparatorPattern*") { wildcardFenced ->
                wildcardFenced.splitToSequence(delimiters = lineSeparators).joinToString(anyLineSepPattern) {
                    escape(it)
                }
            }
        }.toRegex()
}

/**
 * Returns `true` if this character sequence matches the given
 * glob-like [pattern],
 * using the specified [wildcard] (default: `*`) to match within lines,
 * and the specified [multilineWildcard] (default: `**`) to match across lines.
 *
 * The specified [lineSeparators] (default: [LineSeparators.Common]) can
 * be matches by any line separator, i.e. [LineSeparators.LF] / `\n`.
 *
 * **Example 1: matching within lines with wildcard**
 * ```kotlin
 * "foo.bar()".matchesGlob("foo.*")  // ✅
 * ```
 *
 * **Example 2: matching across lines with multiline wildcard**
 * ```kotlin
 * """
 * foo
 *   .bar()
 *   .baz()
 * """.trimIndent().matchesGlob(
 *     """
 *     foo
 *       .**()
 *     """.trimIndent())             // ✅
 * ```
 *
 * **Example 3: wildcard not matching across lines**
 * ```kotlin
 * """
 * foo
 *   .bar()
 *   .baz()
 * """.trimIndent().matchesGlob(
 *     """
 *     foo
 *       .*()
 *     """.trimIndent())             // ❌ (* does not match across lines)
 * ```
 *
 * @see [Regex.Companion.fromGlob]
 */
internal fun CharSequence.matchesGlob(
    pattern: CharSequence,
    wildcard: String = "*",
    multilineWildcard: String = "**",
    vararg lineSeparators: String = LineSeparators.Common,
): Boolean = Regex.fromGlob(pattern, wildcard, multilineWildcard, *lineSeparators).matches(this)

/**
 * Returns `true` if this character sequence matches the given
 * SLF4J / Logback style [pattern], using
 * `{}` to match within lines, and
 * `{{}}` to match across lines.
 *
 * The specified [lineSeparators] (default: [LineSeparators.Common]) can
 * be matches by any line separator, i.e. [LineSeparators.LF] / `\n`.
 *
 * **Example 1: matching within lines with `{}`**
 * ```kotlin
 * "foo.bar()".matchesGlob("foo.{}")  // ✅
 * ```
 *
 * **Example 2: matching across lines with `{{}}`**
 * ```kotlin
 * """
 * foo
 *   .bar()
 *   .baz()
 * """.trimIndent().matchesGlob(
 *     """
 *     foo
 *       .{{}}()
 *     """.trimIndent())             // ✅
 * ```
 *
 * **Example 3: `{}` not matching across lines**
 * ```kotlin
 * """
 * foo
 *   .bar()
 *   .baz()
 * """.trimIndent().matchesGlob(
 *     """
 *     foo
 *       .{}()
 *     """.trimIndent())             // ❌ ({} does not match across lines)
 * ```
 *
 * @see [Regex.Companion.fromGlob]
 */
internal fun CharSequence.matchesCurly(
    pattern: CharSequence,
    vararg lineSeparators: String = LineSeparators.Common,
): Boolean = Regex.fromGlob(pattern, "{}", "{{}}", *lineSeparators).matches(this)
