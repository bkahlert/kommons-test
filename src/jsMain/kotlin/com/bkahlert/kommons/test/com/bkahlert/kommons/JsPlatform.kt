package com.bkahlert.kommons.test.com.bkahlert.kommons

/** Platforms a program can run on. */
internal actual enum class Platform {

    /** Browser platform */
    Browser,

    /** NodeJS platform */
    NodeJS,

    /** Java virtual machine */
    JVM;

    actual companion object {
        /** The platform this program runs on. */
        actual val Current: Platform by lazy {
            runCatching { kotlinx.browser.window }.fold({ Browser }, { NodeJS })
        }
    }
}
