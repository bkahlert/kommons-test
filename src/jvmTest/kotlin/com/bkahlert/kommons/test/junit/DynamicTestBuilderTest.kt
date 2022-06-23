package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.tests
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream
import kotlin.streams.asSequence

class DynamicTestBuilderTest {

    private val DynamicNode.testSourceString: String? get() = testSourceUri.map { it.toString() }.orElse(null)
    private fun <T> Stream<T>.asList() = asSequence().toList()

    private val testsWithoutSubject
        get() = testing {
            "other" asserting { shouldBe("other") }
            asserting("subject") { shouldBe("subject") }
            expecting { "subject".length } it { shouldBeGreaterThan(5) }
            expecting { "subject".length } that { it.shouldBeGreaterThan(5) }
            expectCatching { "subject".length } it { isSuccess.shouldBeTrue() }
            expectCatching { "subject".length } that { it.isSuccess.shouldBeTrue() }
            expectThrows<RuntimeException> { throw RuntimeException() }
            expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }
        }

    private val failingTestsWithoutSubject
        get() = testing {
            "other" asserting { shouldBe("fail") }
            asserting("subject") { shouldBe("fail") }
            expecting { "subject".length } it { shouldBe("fail") }
            expecting { "subject".length } that { it.shouldBe("fail") }
            expectCatching { "subject".length } it { shouldBe("fail") }
            expectCatching { "subject".length } that { it.shouldBe("fail") }
            expectThrows<RuntimeException> { shouldBe("fail") }
            expectThrows<RuntimeException> { throw RuntimeException() } it { shouldBe("fail") }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.shouldBe("fail") }
        }

    private val testsWithSubject
        get() = testing("subject") {
            "other" asserting { shouldBe("other") }
            asserting { shouldBe("subject") }
            expecting { length } it { shouldBeGreaterThan(5) }
            expecting { length } that { it.shouldBeGreaterThan(5) }
            expectCatching { length } it { isSuccess.shouldBeTrue() }
            expectCatching { length } that { it.isSuccess.shouldBeTrue() }
            expectThrows<RuntimeException> { throw RuntimeException() }
            expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }
        }

    private val failingTestsWithSubject
        get() = testing("subject") {
            "other" asserting { shouldBe("fail") }
            asserting { shouldBe("fail") }
            expecting { length } it { shouldBe("fail") }
            expecting { length } that { it.shouldBe("fail") }
            expectCatching { length } it { shouldBe("fail") }
            expectCatching { length } that { it.shouldBe("fail") }
            expectThrows<RuntimeException> { shouldBe("fail") }
            expectThrows<RuntimeException> { throw RuntimeException() } it { shouldBe("fail") }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.shouldBe("fail") }
        }

    private val testsWithSubjects
        get() = testingAll("subject 1", "subject 2", "subject 3") {
            "other" asserting { shouldBe("other") }
            asserting { shouldStartWith("subject") }
            expecting { length } it { shouldBeGreaterThan(5) }
            expecting { length } that { it.shouldBeGreaterThan(5) }
            expectCatching { length } it { isSuccess.shouldBeTrue() }
            expectCatching { length } that { it.isSuccess.shouldBeTrue() }
            expectThrows<RuntimeException> { throw RuntimeException() }
            expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }
        }

    private val failingTestsWithSubjects
        get() = testingAll("subject 1", "subject 2", "subject 3") {
            "other" asserting { shouldBe("fail") }
            asserting { shouldBe("fail") }
            expecting { length } it { shouldBe("fail") }
            expecting { length } that { it.shouldBe("fail") }
            expectCatching { length } it { shouldBe("fail") }
            expectCatching { length } that { it.shouldBe("fail") }
            expectThrows<RuntimeException> { shouldBe("fail") }
            expectThrows<RuntimeException> { throw RuntimeException() } it { shouldBe("fail") }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.shouldBe("fail") }
        }


    @Test fun tests_without_subject() = tests {
        var currentLine = 30
        testsWithoutSubject.asList().filterIsInstance<DynamicTest>().map { test ->
            test.displayName to test.testSourceString?.substringAfterLast("?")
        }.shouldContainExactly(
            """❕ ❮ "other" ❯ shouldBe("other")""" to "line=${currentLine++}&column=14",
            """❕ ❮ "subject" ❯ shouldBe("subject")""" to "line=${currentLine++}&column=13",
            """❔ ❮ "subject".length ❯ shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=13",
            """❔ ❮ "subject".length ❯ shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=13",
            """❓ ❮ "subject".length ❯ isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=13",
            """❓ ❮ "subject".length ❯ isSuccess.shouldBeTrue()""" to "line=${currentLine}&column=13",
            """❗ RuntimeException""" to "line=1&column=1",
            """❗ RuntimeException""" to "line=1&column=1",
            """❗ RuntimeException""" to "line=1&column=1",
        )
    }

    @TestFactory fun run_tests_without_subject() = testsWithoutSubject

    @TestFactory fun run_failing_tests_without_subject() = failingTestsWithoutSubject.transform { it.toExceptionExpectingTest<AssertionError>() }


    @Test fun tests_with_subject() = tests {
        var currentLine = 56
        testsWithSubject.asList().filterIsInstance<DynamicTest>().map { test ->
            test.displayName to test.testSourceString?.substringAfterLast("?")
        }.shouldContainExactly(
            """❕ ❮ "other" ❯ shouldBe("other")""" to "line=${currentLine++}&column=14",
            """❕ ❮ "subject" ❯ shouldBe("subject")""" to "line=${currentLine++}&column=13",
            """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=13",
            """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=13",
            """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=13",
            """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine}&column=13",
            """❗ RuntimeException""" to "line=1&column=1",
            """❗ RuntimeException""" to "line=1&column=1",
            """❗ RuntimeException""" to "line=1&column=1",
        )
    }

    @TestFactory fun run_tests_with_subject() = testsWithSubject

    @TestFactory fun run_failing_tests_with_subject() = failingTestsWithSubject.transform { it.toExceptionExpectingTest<AssertionError>() }


    @Test fun tests_with_subjects() = tests {
        val firstLine = 82
        testsWithSubjects.asList() should { containers ->
            containers shouldHaveSize 3
            for (i in 1..3) {
                val container = containers[i - 1]
                container.displayName shouldBe "for ❮ \"subject $i\" ❯"
                container.testSourceString shouldEndWith "line=${firstLine - 1}&column=9"
                container.children.asList().filterIsInstance<DynamicTest>().map { test ->
                    test.displayName to test.testSourceString?.substringAfterLast("?")
                }.shouldContainExactly(
                    """❕ ❮ "other" ❯ shouldBe("other")""" to "line=${firstLine + 0}&column=14",
                    """❕ ❮ "subject $i" ❯ shouldStartWith("subject")""" to "line=${firstLine + 1}&column=13",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${firstLine + 2}&column=13",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${firstLine + 3}&column=13",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${firstLine + 4}&column=13",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${firstLine + 5}&column=13",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                )
            }
        }
    }

    @TestFactory fun run_tests_with_subjects() = testsWithSubjects

    @TestFactory fun run_failing_tests_with_subjects() = failingTestsWithSubjects.transform { it.toExceptionExpectingTest<AssertionError>() }


    @Nested
    inner class DynamicTestsWithSubjectBuilderTest {

        @Test
        fun `should run asserting`() {
            var testSucceeded = false
            val tests = testingAll("subject") {
                asserting { testSucceeded = this == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run receiver asserting`() {
            var testSucceeded = false
            val tests = testingAll("subject") {
                "other" asserting { testSucceeded = this == "other" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating it`() {
            var testSucceeded = false
            val tests = testingAll("subject") {
                expecting("expectation") { "$this.prop" } it { testSucceeded = this == "subject.prop" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating that`() {
            var testSucceeded = false
            val tests = testingAll("subject") {
                expecting("expectation") { "$this.prop" } that { testSucceeded = it == "subject.prop" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should throw on incomplete evaluating`() {
            val tests = testingAll("subject") {
                expecting("expectation") { "$this.prop" }
            }
            shouldThrow<IllegalUsageException> { tests.execute() }
                .message shouldContain "not finished"
        }

        @Test
        fun `should run expectCatching it`() {
            var testSucceeded = false
            val tests = testingAll("subject") {
                expectCatching { throw RuntimeException(this) } it { testSucceeded = exceptionOrNull()!!.message == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run expectCatching that`() {
            var testSucceeded = false
            val tests = testingAll("subject") {
                expectCatching { throw RuntimeException(this) } that { testSucceeded = it.exceptionOrNull()!!.message == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should throw on incomplete expectCatching`() {
            val tests = testingAll("subject") {
                expectCatching<Any?> { throw RuntimeException(this) }
            }
            shouldThrow<IllegalUsageException> { tests.execute() }
                .message shouldContain "not finished"
        }

        @Test
        fun `should run expectThrows`() {
            var testSucceeded = false
            val tests = testingAll("subject") {
                expectThrows<RuntimeException> { throw RuntimeException(this) } that { testSucceeded = it.message == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should not throw on evaluating only throwable type`() {
            val tests = testingAll("subject") {
                expectThrows<RuntimeException> { throw RuntimeException(this) }
            }
            shouldNotThrowAny { tests.execute() }
        }
    }

    @Nested
    inner class DynamicTestsWithoutSubjectBuilderTest {

        @Test
        fun `should run asserting`() {
            var testSucceeded = false
            val tests = testing {
                asserting { testSucceeded = true }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run receiver asserting`() {
            var testSucceeded = false
            val tests = testing {
                "other" asserting { testSucceeded = this == "other" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating it`() {
            var testSucceeded = false
            val tests = testing {
                expecting("expectation") { "subject" } it { testSucceeded = this == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating that`() {
            var testSucceeded = false
            val tests = testing {
                expecting("expectation") { "subject" } that { testSucceeded = it == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should throw on incomplete evaluating`() {
            val tests = testing {
                expecting("expectation") { "subject" }
            }
            shouldThrow<IllegalUsageException> { tests.execute() }
                .message shouldContain "not finished"
        }

        @Test
        fun `should run expectCatching it`() {
            var testSucceeded = false
            val tests = testing {
                expectCatching<Any?> { throw RuntimeException("message") } it { testSucceeded = exceptionOrNull()!!.message == "message" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run expectCatching that`() {
            var testSucceeded = false
            val tests = testing {
                expectCatching<Any?> { throw RuntimeException("message") } that { testSucceeded = it.exceptionOrNull()!!.message == "message" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should throw on incomplete expectCatching`() {
            val tests = testing {
                expectCatching<Any?> { throw RuntimeException("message") }
            }
            shouldThrow<IllegalUsageException> { tests.execute() }
                .message shouldContain "not finished"
        }

        @Test
        fun `should run expectThrows`() {
            var testSucceeded = false
            val tests = testing {
                expectThrows<RuntimeException> { throw RuntimeException("message") } that { testSucceeded = it.message == "message" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should not throw on evaluating only throwable type`() {
            val tests = testing {
                expectThrows<RuntimeException> { throw RuntimeException("message") }
            }
            shouldNotThrowAny { tests.execute() }
        }
    }
}
