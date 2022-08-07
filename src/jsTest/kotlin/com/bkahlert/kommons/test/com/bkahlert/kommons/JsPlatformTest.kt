package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.testAll
import io.kotest.matchers.collections.shouldBeOneOf
import kotlin.test.Test

class JsPlatformTest {

    @Test fun current() = testAll {
        Platform.Current.shouldBeOneOf(Platform.Browser, Platform.NodeJS)
    }
}
