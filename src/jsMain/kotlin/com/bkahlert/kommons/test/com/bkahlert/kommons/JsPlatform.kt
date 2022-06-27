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
        override val isDebugging: Boolean = false
    }

    actual companion object {
        private val currentPlatform by lazy {
            runCatching { kotlinx.browser.window }.fold({ JS.Browser }, { JS.NodeJS })
        }

        /** The platforms this program runs on. */
        actual val Current: Platform
            get() = currentPlatform
    }
}
