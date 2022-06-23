package com.bkahlert.kommons.test.com.bkahlert.kommons.debug

/** Function that renders any object. */
internal typealias Renderer = (Any?) -> String
/** Function that outputs any string. */
internal typealias Printer = (String) -> Unit
/** Function that transform an instance of type `T` for further inspection. */
internal typealias Inspector<T> = (T) -> Any?

/**
 * Helper property that supports
 * [print debugging][https://en.wikipedia.org/wiki/Debugging#Print_debugging].
 *
 * **Example**
 * ```kotlin
 * chain().of.endless().trace.breaks.print().calls()
 * ```
 * … does the same as …
 *
 * ```kotlin
 * chain().of.endless().calls()
 * ```
 * … with the only difference that the return value of
 *
 * ```kotlin
 * chain().of.endless()
 * ```
 *
 * will be printed.
 */
@Suppress("GrazieInspection", "DEPRECATION", "unused")
@Deprecated("Don't forget to remove after you finished debugging.", replaceWith = ReplaceWith("this"))
internal val <T> T.trace: T get(): T = trace()

/**
 * Helper property that supports
 * [print debugging][https://en.wikipedia.org/wiki/Debugging#Print_debugging].
 *
 * **Example**
 * ```kotlin
 * chain().of.endless().trace.breaks.print().calls()
 * ```
 * … does the same as …
 *
 * ```kotlin
 * chain().of.endless().calls()
 * ```
 * … with the only difference that the return value of
 *
 * ```kotlin
 * chain().of.endless()
 * ```
 *
 * will be printed.
 */
@Suppress("GrazieInspection", "DEPRECATION", "unused")
@Deprecated("Don't forget to remove after you finished debugging.", replaceWith = ReplaceWith("this"))
internal fun <T> T.trace(
    caption: CharSequence? = null,
    render: Renderer = { it.toString() },
    highlight: Boolean = true,
    out: Printer? = null,
    inspect: Inspector<T>? = null
): T {
    buildString {
        caption?.also {
            append(caption.let { if (highlight) it.highlighted else it })
            append(' ')
        }
        appendWrapped(
            render(this@trace).let { if (highlight) it.highlightedStrongly else it },
            if (highlight) "⟨".highlighted to "⟩".highlighted else "⟨" to "⟩",
        )
        inspect?.also {
            append(" ")
            appendWrapped(
                render(inspect(this@trace)).let { if (highlight) it.highlightedStrongly else it },
                if (highlight) "{".highlighted to "}".highlighted else "{" to "}",
            )
        }
    }.also { out?.invoke(it) ?: println(it) }
    return this
}

private fun StringBuilder.appendWrapped(value: String, brackets: Pair<String, String>) {
    val separator = if (value.isMultiline) '\n' else ' '
    append(brackets.first)
    append(separator)
    append(value)
    append(separator)
    append(brackets.second)
}

private val CharSequence.isMultiline: Boolean get() = lineSequence().take(2).count() > 1

private val CharSequence.highlighted: String
    get() = lineSequence().joinToString("\n") { "\u001b[1;36m$it\u001B[0m" }

private val CharSequence.highlightedStrongly: String
    get() = lineSequence().joinToString("\n") { "\u001b[96m$it\u001B[0m" }
