package com.bkahlert.kommons

import com.bkahlert.kommons.test.shouldHaveRootCauseInstanceOf
import com.bkahlert.kommons.test.shouldHaveRootCauseMessage
import com.bkahlert.kommons.test.testAll
import kotlin.test.Test

class ExceptionsMatchersKtTest {

    @Test
    fun root_cause() = testAll(
        IllegalArgumentException("error message"),
        IllegalStateException(IllegalArgumentException("error message")),
        RuntimeException(IllegalStateException(IllegalArgumentException("error message"))),
        Error(RuntimeException(IllegalStateException(IllegalArgumentException("error message")))),
        RuntimeException(Error(RuntimeException(IllegalStateException(IllegalArgumentException("error message"))))),
    ) { ex ->
        ex.shouldHaveRootCauseInstanceOf<IllegalArgumentException>()
        ex.shouldHaveRootCauseMessage("error message")
    }
}
