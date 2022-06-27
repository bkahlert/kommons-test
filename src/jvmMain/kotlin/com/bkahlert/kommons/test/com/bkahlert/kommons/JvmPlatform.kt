package com.bkahlert.kommons.test.com.bkahlert.kommons

/** Platforms this program can be run on. */
internal actual sealed interface Platform {

    /** Whether this program is running in debug mode. */
    actual val isDebugging: Boolean

    /** JavaScript based platform, e.g. browser. */
    actual sealed interface JS : Platform {
        /** Browser platform */
        actual object Browser : JS {
            override val isDebugging: Boolean = false
        }

        /** NodeJS platform */
        actual object NodeJS : JS {
            override val isDebugging: Boolean = false
        }
    }

    /** Java virtual machine. */
    actual object JVM : Platform {

        /**
         * Contains the context ClassLoader for the current [Thread].
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

        override val isDebugging: Boolean
            get() = jvmArgs.any { it.startsWith("-agentlib:jdwp") } || jvmJavaAgents.any { it.contains("debugger") }
    }

    actual companion object {
        /** The platforms this program runs on. */
        actual val Current: Platform
            get() = JVM
    }
}

/**
 * Attempts to load the [Class] with the given [name] using this [ClassLoader].
 *
 * Returns `null` if the class can't be loaded.
 */
internal fun ClassLoader.loadClassOrNull(name: String): Class<*>? = kotlin.runCatching { loadClass(name) }.getOrNull()
