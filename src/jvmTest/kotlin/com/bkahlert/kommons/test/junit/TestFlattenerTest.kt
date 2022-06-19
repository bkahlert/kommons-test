package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.junit.TestFlattener.flatten
import com.bkahlert.kommons.test.tests
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test

class TestFlattenerTest {

    @Test fun flatten() = tests {
        val tests = DynamicTestBuilderTest().failingTestsWithSubject.flatten()
        tests.toList() shouldHaveSize 25
    }
}
