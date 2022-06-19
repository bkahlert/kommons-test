package com.bkahlert.kommons

/** Returns this char sequence with its first letter in upper case. */
internal fun CharSequence.capitalize(): CharSequence = if (isNotEmpty() && first().isLowerCase()) first().uppercaseChar() + substring(1) else this

/** Returns this string with its first letter in upper case. */
@Suppress("SpellCheckingInspection")
internal fun String.decapitalize(): String = if (isNotEmpty() && first().isUpperCase()) first().lowercaseChar() + substring(1) else this
