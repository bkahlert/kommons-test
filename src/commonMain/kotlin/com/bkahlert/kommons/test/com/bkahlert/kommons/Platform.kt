package com.bkahlert.kommons.test.com.bkahlert.kommons

/** Platforms a program can run on. */
internal expect enum class Platform {

    /** Browser platform */
    Browser,

    /** NodeJS platform */
    NodeJS,

    /** Java virtual machine */
    JVM;

    companion object {
        /** The platform this program runs on. */
        val Current: Platform
    }
}
