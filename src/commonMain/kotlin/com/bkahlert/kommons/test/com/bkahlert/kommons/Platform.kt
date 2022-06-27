package com.bkahlert.kommons.test.com.bkahlert.kommons

/** Platforms this program can be run on. */
internal expect sealed interface Platform {

    /** Whether this program is running in debug mode. */
    val isDebugging: Boolean

    /** JavaScript based platform, e.g. browser. */
    sealed interface JS : Platform {

        /** Browser platform */
        object Browser : JS

        /** NodeJS platform */
        object NodeJS : JS
    }

    /** Java virtual machine. */
    object JVM : Platform

    companion object {

        /** The platforms this program runs on. */
        internal val Current: Platform
    }
}
