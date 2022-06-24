package com.bkahlert.kommons.test.com.bkahlert.kommons

import kotlin.random.Random

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

private const val randomSuffixLength = 4
private const val randomSuffixSeparator = "--"

@Suppress("RegExpSimplifiable")
private val randomSuffixMatcher: Regex = Regex(".*$randomSuffixSeparator[\\da-zA-Z]{$randomSuffixLength}\$")

/** Returns this char sequence with a random suffix of two dashes dash and four alphanumeric characters. */
internal fun CharSequence.withRandomSuffix(): CharSequence =
    if (randomSuffixMatcher.matches(this)) this
    else buildString { append(this@withRandomSuffix); append(randomSuffixSeparator); append(randomString(length = randomSuffixLength)) }

/** Returns this string with a random suffix of two dashes dash and four alphanumeric characters. */
internal fun String.withRandomSuffix(): String =
    if (randomSuffixMatcher.matches(this)) this
    else buildString { append(this@withRandomSuffix); append(randomSuffixSeparator); append(randomString(length = randomSuffixLength)) }

/** Creates a random string of the specified [length] made up of the specified [allowedCharacters]. */
internal fun randomString(length: Int = 16, vararg allowedCharacters: Char = (('0'..'9') + ('a'..'z') + ('A'..'Z')).toCharArray()): String =
    buildString(length) { repeat(length) { append(allowedCharacters[Random.nextInt(0, allowedCharacters.size)]) } }

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
