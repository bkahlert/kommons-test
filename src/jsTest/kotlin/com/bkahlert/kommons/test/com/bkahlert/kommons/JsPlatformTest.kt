package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test

class JsPlatformTest {

    @Test fun current() = test {
        Platform.Current.shouldBeInstanceOf<Platform.JS>()
    }

    @Test fun is_debugging() = test {
        Platform.Current.isDebugging shouldBe false
    }
}
