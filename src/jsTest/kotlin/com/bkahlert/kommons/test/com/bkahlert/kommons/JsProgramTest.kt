package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.testAll
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class JsProgramTest {

    @Test fun is_debugging() = testAll {
        Program.isDebugging shouldBe false
    }
}
