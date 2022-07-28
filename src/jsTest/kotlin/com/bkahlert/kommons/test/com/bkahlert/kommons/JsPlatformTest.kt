package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.testAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test

class JsPlatformTest {

    @Test fun current() = testAll {
        Platform.Current.shouldBeInstanceOf<Platform.JS>()
    }

    @Test fun is_debugging() = testAll {
        Platform.Current.isDebugging shouldBe false
    }
}
