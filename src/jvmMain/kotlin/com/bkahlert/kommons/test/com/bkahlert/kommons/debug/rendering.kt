package com.bkahlert.kommons.test.com.bkahlert.kommons.debug

import com.bkahlert.kommons.test.com.bkahlert.kommons.quoted
import com.bkahlert.kommons.test.rootCause
import java.nio.file.Path
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.jvm.reflect

private fun renderType(type: KType, simplified: Boolean): String =
    if (simplified) type.toString().split('.').last() else type.toString()

/**
 * Renders the type of this function.
 */
internal fun Function<*>.renderFunctionType(simplified: Boolean = true): String =
    buildString { this@renderFunctionType.renderFunctionTypeTo(this, simplified) }

/**
 * Renders the type of this function to the specified [out].
 * @see renderFunctionType
 */
internal fun Function<*>.renderFunctionTypeTo(out: StringBuilder, simplified: Boolean) {
    when (val kFunction = if (this is KFunction<*>) this else reflect()) {
        is KFunction<*> -> {
            val parametersByKind = kFunction.parameters.groupBy { it.kind }
            parametersByKind.getOrDefault(KParameter.Kind.INSTANCE, emptyList()).forEach {
                out.append(renderType(it.type, simplified))
                out.append(".")
            }
            parametersByKind.getOrDefault(KParameter.Kind.EXTENSION_RECEIVER, emptyList()).forEach {
                out.append(renderType(it.type, simplified))
                out.append(".")
            }
            kFunction.name.takeUnless { it == "<anonymous>" }?.also { out.append(it) }
            out.append("(")
            parametersByKind.getOrDefault(KParameter.Kind.VALUE, emptyList()).forEachIndexed { index: Int, param: KParameter ->
                if (index > 0) out.append(", ")
                out.append(renderType(param.type, simplified))
            }
            out.append(")")
            out.append(" -> ")
            out.append(renderType(kFunction.returnType, simplified))
        }
        else -> out.append("Function")
    }
}


internal val LineSeparators: Array<String> = arrayOf(
    "\u000D\u000A",
    "\u000A",
    "\u000D",
    "\u0085",
    "\u2029",
    "\u2028",
)

internal fun Any?.toCompactString(): String = when (this) {
    is Path -> toUri().toString()
    is ByteArray -> "0x" + joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
    is Array<*> -> toList().toCompactString()
    is Iterable<*> -> joinToString(prefix = "[", postfix = "]") { it.toCompactString() }
    is Process -> also { waitFor() }.exitValue().toString()
    is CharSequence -> split(*LineSeparators).joinToString(separator = "⏎").removeSuffix("⏎").quoted
    else -> when (this) {
        null -> "␀"
        Unit -> ""
        else -> {
            val string = toString()
            string.split(*LineSeparators).joinToString(separator = "⏎").removeSuffix("⏎")
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
