package com.bkahlert.kommons.test.com.bkahlert.kommons

/** The running program. */
internal actual object Program {

    /**
     * The context ClassLoader for the current [Thread].
     *
     * The context [ClassLoader] is provided by the creator of the [Thread] for use
     * by code running in this thread when loading classes and resources.
     */
    val contextClassLoader: ClassLoader
        get() = Thread.currentThread().contextClassLoader

    private val jvmArgs: List<String>
        get() {
            val classLoader = contextClassLoader
            return classLoader.loadClassOrNull("java.lang.management.ManagementFactory")?.let {
                val runtimeMxBean: Any = it.getMethod("getRuntimeMXBean").invoke(null)
                val runtimeMxBeanClass: Class<*> = classLoader.loadClass("java.lang.management.RuntimeMXBean")
                val inputArgs: Any = runtimeMxBeanClass.getMethod("getInputArguments").invoke(runtimeMxBean)
                (inputArgs as? List<*>)?.map { arg -> arg.toString() }
            } ?: emptyList()
        }

    private val jvmJavaAgents: List<String>
        get() = jvmArgs.filter { it.startsWith("-javaagent") }

    private val intellijTraits: List<String>
        get() = listOf("jetbrains", "intellij", "idea", "idea_rt.jar")

    /** Whether this program is started by [IDEA IntelliJ](https://www.jetbrains.com/lp/intellij-frameworks/). */
    val isIntelliJ: Boolean
        get() = runCatching { jvmJavaAgents.any { it.containsAny(intellijTraits, ignoreCase = true) } }.getOrElse { false }

    /** Whether this program is running in debug mode. */
    actual val isDebugging: Boolean
        get() = jvmArgs.any { it.startsWith("-agentlib:jdwp") } || jvmJavaAgents.any { it.contains("debugger") }
}

/**
 * Returns `true` if this character sequence contains any of the specified [others] as a substring.
 *
 * @param ignoreCase `true` to ignore the character case when comparing strings. By default `false`.
 */
private fun <T : CharSequence> CharSequence.containsAny(others: Iterable<T>, ignoreCase: Boolean = false): Boolean =
    others.any { contains(it, ignoreCase = ignoreCase) }

/**
 * Attempts to load the [Class] with the given [name] using this [ClassLoader].
 *
 * Returns `null` if the class can't be loaded.
 */
public fun ClassLoader.loadClassOrNull(name: String): Class<*>? = kotlin.runCatching { loadClass(name) }.getOrNull()
