package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.com.bkahlert.kommons.Platform.JVM
import com.bkahlert.kommons.test.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.test.Test

class JvmPlatformTest {

    @Test fun current() = test {
        Platform.Current shouldBe JVM
    }

    @Test fun context_class_loader() = test {
        JVM.contextClassLoader shouldNotBe null
    }

    @Test fun load_class_or_null() = test {
        JVM.contextClassLoader.loadClassOrNull(randomString()) shouldBe null
        JVM.contextClassLoader.loadClassOrNull("java.lang.String") shouldBe String::class.java
    }

    @Test fun is_debugging() = test {
        Platform.Current.isDebugging shouldBe false
    }
}
