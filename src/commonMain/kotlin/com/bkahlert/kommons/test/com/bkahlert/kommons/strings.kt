package com.bkahlert.kommons.test.com.bkahlert.kommons

internal val ansiPatterns = listOf(
    @Suppress("RegExpRedundantEscape") // otherwise "lone quantifier brackets in JS"
    "\\u001B\\]\\d*;[^\\u001B]*\\u001B\\\\".toRegex(), // OSC (operating system command) escape sequences
    "\\u001B[@-Z\\-_]".toRegex(),            // Fe escape sequences
    "\\u001B[ -/][@-~]".toRegex(),           // 2-byte sequences
    "\\u001B\\[[0-?]*[ -/]*[@-~]".toRegex(), // CSI (control sequence intro) escape sequences
)

internal val ansiPattern: Regex = ansiPatterns.joinToString("|") { it.pattern }.toRegex()

/** Whether this character sequence contains [ANSI escape codes](https://en.wikipedia.org/wiki/ANSI_escape_code).*/
internal val CharSequence.ansiContained: Boolean
    get() = ansiPattern.containsMatchIn(this)

/** Contains this character sequence with all [ANSI escape codes](https://en.wikipedia.org/wiki/ANSI_escape_code) removed. */
internal val String.ansiRemoved: String
    get() = if (ansiContained) ansiPattern.replace(this, "") else this


/** Returns this string with the [prefix] prepended if it is not already present. */
internal fun String.withPrefix(prefix: CharSequence): String = if (startsWith(prefix)) this else buildString { append(prefix); append(this@withPrefix) }

/** Returns this string with the [suffix] appended if it is not already present. */
internal fun String.withSuffix(suffix: CharSequence): String = if (endsWith(suffix)) this else buildString { append(this@withSuffix);append(suffix) }

/**
 * Returns the index within this string of the first occurrence of the specified character,
 * starting from the specified [startIndex].
 *
 * @param ignoreCase `true` to ignore character case when matching a character. By default `false`.
 * @return An index of the first occurrence of [char] or `null` if none is found.
 */
internal fun CharSequence.indexOfOrNull(char: Char, startIndex: Int = 0, ignoreCase: Boolean = false): Int? =
    indexOf(char, startIndex, ignoreCase).takeIf { it >= 0 }

/**
 * Returns the index within this char sequence of the first occurrence of the specified [string],
 * starting from the specified [startIndex].
 *
 * @param ignoreCase `true` to ignore character case when matching a string. By default `false`.
 * @return An index of the first occurrence of [string] or `null` if none is found.
 */
internal fun CharSequence.indexOfOrNull(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? =
    indexOf(string, startIndex, ignoreCase).takeIf { it >= 0 }


/** Whether this string consists of more than one line. */
internal val CharSequence.isMultiline: Boolean get() = lineSequence().take(2).count() > 1
