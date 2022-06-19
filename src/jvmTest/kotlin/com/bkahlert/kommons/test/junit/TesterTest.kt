package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.junit.IllegalUsageCheck.ExpectIllegalUsageException
import com.bkahlert.kommons.test.junit.TestFlattener.flatten
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import java.util.stream.Stream
import kotlin.streams.asStream

class TesterTest {

    @Nested
    inner class TestFlattening {

        @Test
        fun `should flatten`() {
            val tests = DynamicTestBuilderTest().TestingSingleSubject().`as parameter`().flatten()
            tests.toList() shouldHaveSize 17
        }
    }

    @Nested
    inner class TestLabelling {
        private operator fun String.not() = trimIndent()

        private val testsWithSubject = DynamicTestBuilderTest().`testing with subject`()

        @Suppress("DANGEROUS_CHARACTERS", "NonAsciiCharacters")
        @TestFactory
        fun `↓ ↓ ↓ ↓ ↓ tests with subject | compare here | in test runner output ↓ ↓ ↓ ↓ ↓`() = testsWithSubject

        @TestFactory
        fun `should label test with subject automatically`(): Stream<DynamicTest> = testsWithSubject.flatten().map { it.displayName }.zip(
            sequenceOf(
                !"""
                ❕ ❮ other ❯ isEqualTo("other")
            """,
                !"""
                ❕ isEqualTo("subject")
            """,
                !"""
                ❔ length isGreaterThan(5)
            """,
                !"""
                ❔ length
            """,
                !"""
                ❓ length isSuccess()
            """,
                !"""
                ❗ RuntimeException
            """,
                !"""
                ❗ RuntimeException
            """,
            )
        ).map { (label, expected) -> dynamicTest("👆 $expected") { label shouldBe expected } }.asStream()


        private val testsWithoutSubject = DynamicTestBuilderTest().`testing without subject`()

        @Suppress("DANGEROUS_CHARACTERS", "NonAsciiCharacters")
        @TestFactory
        fun `↓ ↓ ↓ ↓ ↓ tests without subject | compare here | in test runner output ↓ ↓ ↓ ↓ ↓`() = testsWithoutSubject

        @TestFactory
        fun `should label test without subject automatically`(): Stream<DynamicTest> = testsWithoutSubject.flatten().map { it.displayName }.zip(
            sequenceOf(
                !"""
                ❕ ❮ other ❯ isEqualTo("other")
            """,
                !"""
                ❕ isEqualTo("subject")
            """,
                !"""
                ❔ ❮ "subject".length ❯ isGreaterThan(5)
            """,
                !"""
                ❔ ❮ "subject".length ❯
            """,
                !"""
                ❓ ❮ "subject".length ❯ isSuccess()
            """,
                !"""
                ❗ RuntimeException
            """,
                !"""
                ❗ RuntimeException
            """,
            )
        ).map { (label, expected) -> dynamicTest("👆 $expected") { label shouldBe expected } }.asStream()
    }

    @Nested
    inner class PlainAssertionsTest {

        @Test
        fun `should run asserting`() {
            var testSucceeded = false
            asserting("subject") { testSucceeded = this == "subject" }
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run receiver asserting`() {
            var testSucceeded = false
            "subject" asserting { testSucceeded = this == "subject" }
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating it`() {
            var testSucceeded = false
            expecting { "subject" } it { testSucceeded = this == "subject" }
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating that`() {
            var testSucceeded = false
            expecting { "subject" } that { testSucceeded = it == "subject" }
            testSucceeded.shouldBeTrue()
        }

        @Test
        @ExtendWith(ExpectIllegalUsageException::class)
        fun `should throw on incomplete evaluating`() {
            expecting { "subject" }
        }

        @Test
        fun `should run expectCatching it`() {
            var testSucceeded = false
            expectCatching { throw RuntimeException("message") } it { testSucceeded = exceptionOrNull()!!.message == "message" }
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run expectCatching that`() {
            var testSucceeded = false
            expectCatching { throw RuntimeException("message") } that { testSucceeded = it.exceptionOrNull()!!.message == "message" }
            testSucceeded.shouldBeTrue()
        }

        @Test
        @ExtendWith(ExpectIllegalUsageException::class)
        fun `should throw on incomplete expectCatching`() {
            expectCatching { throw RuntimeException("message") }
        }

        @Test
        fun `should run expectThrows`() {
            var testSucceeded = false
            expectThrows<RuntimeException> { throw RuntimeException("message") } that { testSucceeded = it.message == "message" }
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should not throw on evaluating only throwable type`() {
            shouldNotThrowAny { expectThrows<RuntimeException> { throw RuntimeException("message") } }
        }
    }

    @Nested
    inner class DynamicTestsWithSubjectBuilderTest {

        @Test
        fun `should run asserting`() {
            var testSucceeded = false
            val tests = testEach("subject") {
                asserting { testSucceeded = this == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run receiver asserting`() {
            var testSucceeded = false
            val tests = testEach("subject") {
                "other" asserting { testSucceeded = this == "other" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating it`() {
            var testSucceeded = false
            val tests = testEach("subject") {
                expecting("expectation") { "$it.prop" } it { testSucceeded = this == "subject.prop" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating that`() {
            var testSucceeded = false
            val tests = testEach("subject") {
                expecting("expectation") { "$it.prop" } that { testSucceeded = it == "subject.prop" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should throw on incomplete evaluating`() {
            val tests = testEach("subject") {
                expecting("expectation") { "$it.prop" }
            }
            shouldThrow<IllegalUsageException> { tests.execute() }
                .message shouldContain "not finished"
        }

        @Test
        fun `should run expectCatching it`() {
            var testSucceeded = false
            val tests = testEach("subject") {
                expectCatching { throw RuntimeException(this) } it { testSucceeded = exceptionOrNull()!!.message == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run expectCatching that`() {
            var testSucceeded = false
            val tests = testEach("subject") {
                expectCatching { throw RuntimeException(this) } that { testSucceeded = it.exceptionOrNull()!!.message == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should throw on incomplete expectCatching`() {
            val tests = testEach("subject") {
                expectCatching<Any?> { throw RuntimeException(this) }
            }
            shouldThrow<IllegalUsageException> { tests.execute() }
                .message shouldContain "not finished"
        }

        @Test
        fun `should run expectThrows`() {
            var testSucceeded = false
            val tests = testEach("subject") {
                expectThrows<RuntimeException> { throw RuntimeException(this) } that { testSucceeded = it.message == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should not throw on evaluating only throwable type`() {
            val tests = testEach("subject") {
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
            val tests = dynamicTests {
                asserting { testSucceeded = true }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run receiver asserting`() {
            var testSucceeded = false
            val tests = dynamicTests {
                "other" asserting { testSucceeded = this == "other" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating it`() {
            var testSucceeded = false
            val tests = dynamicTests {
                expecting("expectation") { "subject" } it { testSucceeded = this == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run evaluating that`() {
            var testSucceeded = false
            val tests = dynamicTests {
                expecting("expectation") { "subject" } that { testSucceeded = it == "subject" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should throw on incomplete evaluating`() {
            val tests = dynamicTests {
                expecting("expectation") { "subject" }
            }
            shouldThrow<IllegalUsageException> { tests.execute() }
                .message shouldContain "not finished"
        }

        @Test
        fun `should run expectCatching it`() {
            var testSucceeded = false
            val tests = dynamicTests {
                expectCatching<Any?> { throw RuntimeException("message") } it { testSucceeded = exceptionOrNull()!!.message == "message" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should run expectCatching that`() {
            var testSucceeded = false
            val tests = dynamicTests {
                expectCatching<Any?> { throw RuntimeException("message") } that { testSucceeded = it.exceptionOrNull()!!.message == "message" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should throw on incomplete expectCatching`() {
            val tests = dynamicTests {
                expectCatching<Any?> { throw RuntimeException("message") }
            }
            shouldThrow<IllegalUsageException> { tests.execute() }
                .message shouldContain "not finished"
        }

        @Test
        fun `should run expectThrows`() {
            var testSucceeded = false
            val tests = dynamicTests {
                expectThrows<RuntimeException> { throw RuntimeException("message") } that { testSucceeded = it.message == "message" }
            }
            tests.execute()
            testSucceeded.shouldBeTrue()
        }

        @Test
        fun `should not throw on evaluating only throwable type`() {
            val tests = dynamicTests {
                expectThrows<RuntimeException> { throw RuntimeException("message") }
            }
            shouldNotThrowAny { tests.execute() }
        }
    }
}
