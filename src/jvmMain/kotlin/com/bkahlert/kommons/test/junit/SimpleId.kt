package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.withPrefix
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.UniqueId.Segment

/**
 * Simplified form of a [UniqueId] that only uses simple class names
 * and a formatting that strives for readability.
 *
 * In contrast to JUnit's the [UniqueId]
 * this simplified variant cannot guarantee uniqueness
 * in case of equally named classes in different packages.
 */
public data class SimpleId(
    /** Segments that make up this [SimpleId]. */
    public val segments: List<String>,
) : CharSequence by segments.joinToString(".") {
    override fun toString(): String = segments.joinToString(".")

    public companion object {

        /** Creates a [SimpleId] from the specified [StackTraceElement]. */
        public fun from(stackTraceElement: StackTraceElement): SimpleId = SimpleId(buildList {
            stackTraceElement.className.split('$').forEach {
                add(simplifyClass(it))
            }
            add(stackTraceElement.methodName.replace(" ", "_"))
        })

        /** Creates a [SimpleId] from the specified [uniqueId]. */
        public fun from(uniqueId: UniqueId): SimpleId = SimpleId(uniqueId
            .segments
            .map { simplifySegment(it) }
            .filter { it.isNotBlank() })

        private fun simplifySegment(node: Segment): String = with(node) {
            when (type) {
                "engine" -> ""
                "class" -> simplifyClass(value)
                "nested-class" -> simplifyClass(value)
                "method" -> simplifyMethod(value)
                "test-factory" -> simplifyMethod(value)
                "dynamic-container" -> value.removePrefix("#").withPrefix("container-")
                "dynamic-test" -> value.removePrefix("#").withPrefix("test-")
                else -> value
            }
        }

        private fun simplifyClass(value: String): String = value.split(".").last()

        private fun simplifyMethod(value: String): String {
            fun formatArgs(args: String) = args.split(",")
                .filter { it.isNotBlank() && it != SimpleId::class.qualifiedName }
                .joinToString("") { "-" + simplifyClass(it) }

            return value.split("(").let { it.first().replace(" ", "_") + formatArgs(it.last().removeSuffix(")")) }
        }
    }
}
