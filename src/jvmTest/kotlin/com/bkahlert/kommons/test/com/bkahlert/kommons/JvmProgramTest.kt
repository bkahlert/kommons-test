package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.testAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.test.Test

class JvmProgramTest {

    @Test fun context_class_loader() = testAll {
        Program.contextClassLoader shouldNotBe null
    }

    @Test fun load_class_or_null() = testAll {
        Program.contextClassLoader.loadClassOrNull(randomString()) shouldBe null
        Program.contextClassLoader.loadClassOrNull("java.lang.String") shouldBe String::class.java
    }

    @Test fun is_debugging() = testAll {
        Program.isDebugging shouldBe false
    }
}
