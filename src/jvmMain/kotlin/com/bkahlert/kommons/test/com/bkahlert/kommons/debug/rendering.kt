package com.bkahlert.kommons.test.com.bkahlert.kommons.debug

import com.bkahlert.kommons.test.com.bkahlert.kommons.quoted
import com.bkahlert.kommons.test.rootCause
import java.nio.file.Path


internal val LineSeparators: Array<String> = arrayOf(
    "\u000D\u000A",
    "\u000A",
    "\u000D",
    "\u0085",
    "\u2029",
    "\u2028",
)

internal fun Any?.toCompactString(): String {
    return when (this) {
        is Path -> toUri().toString()
        is ByteArray -> "0x" + joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
        is Array<*> -> toList().toCompactString()
        is Iterable<*> -> joinToString(prefix = "[", postfix = "]") { it.toCompactString() }
        is Process -> also { waitFor() }.exitValue().toString()
        is CharSequence -> split(*LineSeparators).joinToString(separator = "⏎").removeSuffix("⏎").quoted
        else -> when (this) {
            null -> "null"
            Unit -> ""
            else -> {
                val string = toCustomStringOrNull() ?: renderType(simplified = true)
                string.split(*LineSeparators).joinToString(separator = "⏎").removeSuffix("⏎")
            }
        }
    }
}

internal fun Throwable?.toCompactString(): String {
    if (this == null) return ""
    val messagePart = message?.let { ": " + it.lines()[0] } ?: ""
    return rootCause.run {
        this::class.simpleName + messagePart + stackTrace?.firstOrNull()
            ?.let { element -> " at.(${element.fileName}:${element.lineNumber})" }
    }
}

internal fun Result<*>?.toCompactString(): String {
    if (this == null) return ""
    return if (isSuccess) getOrNull().toCompactString()
    else exceptionOrNull().toCompactString()
}
