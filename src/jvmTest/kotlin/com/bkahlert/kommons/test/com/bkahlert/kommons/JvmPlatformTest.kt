package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.testAll
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class JvmPlatformTest {

    @Test fun current() = testAll {
        Platform.Current shouldBe Platform.JVM
    }
}
