package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.tests
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.platform.engine.UniqueId

class SimpleIdResolverTest {

    @Test fun test_name(simpleId: SimpleId, uniqueId: UniqueId) = tests {
        simpleId shouldBe SimpleId.from(uniqueId)
    }

    @Nested
    inner class NestedTest {

        @Test fun test_name(simpleId: SimpleId, uniqueId: UniqueId) = tests {
            simpleId shouldBe SimpleId.from(uniqueId)
        }
    }
}
