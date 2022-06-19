package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.tests
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DynamicContainer
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

            group {
                asserting { shouldBe("subject") }
                expecting { length } it { shouldBeGreaterThan(5) }
                expecting { length } that { it.shouldBeGreaterThan(5) }
                expectCatching { length } it { isSuccess.shouldBeTrue() }
                expectCatching { length } that { it.isSuccess.shouldBeTrue() }
                expectThrows<RuntimeException> { throw RuntimeException() }
                expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }
            }

            with { reversed() } testing {
                @Suppress("SpellCheckingInspection")
                asserting { shouldBe("tcejbus") }
                expecting { length } it { shouldBeGreaterThan(5) }
                expecting { length } that { it.shouldBeGreaterThan(5) }
                expectCatching { length } it { isSuccess.shouldBeTrue() }
                expectCatching { length } that { it.isSuccess.shouldBeTrue() }
                expectThrows<RuntimeException> { throw RuntimeException() }
                expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }
            }
        }

    internal val failingTestsWithSubject
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

            group {
                asserting { shouldBe("fail") }
                expecting { length } it { shouldBe("fail") }
                expecting { length } that { it.shouldBe("fail") }
                expectCatching { length } it { shouldBe("fail") }
                expectCatching { length } that { it.shouldBe("fail") }
                expectThrows<RuntimeException> { shouldBe("fail") }
                expectThrows<RuntimeException> { throw RuntimeException() } it { shouldBe("fail") }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.shouldBe("fail") }
            }

            with { reversed() } testing {
                asserting { shouldBe("fail") }
                expecting { length } it { shouldBe("fail") }
                expecting { length } that { it.shouldBe("fail") }
                expectCatching { length } it { shouldBe("fail") }
                expectCatching { length } that { it.shouldBe("fail") }
                expectThrows<RuntimeException> { shouldBe("fail") }
                expectThrows<RuntimeException> { throw RuntimeException() } it { shouldBe("fail") }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.shouldBe("fail") }
            }
        }

    private val testsWithSubjects
        get() = testingAll("subject 1", "subject 1", "subject 1") {
            "other" asserting { shouldBe("other") }
            asserting { shouldStartWith("subject") }
            expecting { length } it { shouldBeGreaterThan(5) }
            expecting { length } that { it.shouldBeGreaterThan(5) }
            expectCatching { length } it { isSuccess.shouldBeTrue() }
            expectCatching { length } that { it.isSuccess.shouldBeTrue() }
            expectThrows<RuntimeException> { throw RuntimeException() }
            expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }

            group {
                asserting { shouldStartWith("subject") }
                expecting { length } it { shouldBeGreaterThan(5) }
                expecting { length } that { it.shouldBeGreaterThan(5) }
                expectCatching { length } it { isSuccess.shouldBeTrue() }
                expectCatching { length } that { it.isSuccess.shouldBeTrue() }
                expectThrows<RuntimeException> { throw RuntimeException() }
                expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }
            }

            with { reversed() } testing {
                @Suppress("SpellCheckingInspection")
                asserting { shouldEndWith("tcejbus") }
                expecting { length } it { shouldBeGreaterThan(5) }
                expecting { length } that { it.shouldBeGreaterThan(5) }
                expectCatching { length } it { isSuccess.shouldBeTrue() }
                expectCatching { length } that { it.isSuccess.shouldBeTrue() }
                expectThrows<RuntimeException> { throw RuntimeException() }
                expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }
            }
        }

    private val failingTestsWithSubjects
        get() = testingAll("subject 1", "subject 1", "subject 1") {
            "other" asserting { shouldBe("fail") }
            asserting { shouldBe("fail") }
            expecting { length } it { shouldBe("fail") }
            expecting { length } that { it.shouldBe("fail") }
            expectCatching { length } it { shouldBe("fail") }
            expectCatching { length } that { it.shouldBe("fail") }
            expectThrows<RuntimeException> { shouldBe("fail") }
            expectThrows<RuntimeException> { throw RuntimeException() } it { shouldBe("fail") }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.shouldBe("fail") }

            group {
                asserting { shouldStartWith("subject") }
                expecting { length } it { shouldBe("fail") }
                expecting { length } that { it.shouldBe("fail") }
                expectCatching { length } it { shouldBe("fail") }
                expectCatching { length } that { it.shouldBe("fail") }
                expectThrows<RuntimeException> { shouldBe("fail") }
                expectThrows<RuntimeException> { throw RuntimeException() } it { shouldBe("fail") }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.shouldBe("fail") }
            }

            with { reversed() } testing {
                @Suppress("SpellCheckingInspection")
                asserting { shouldEndWith("tcejbus") }
                expecting { length } it { shouldBe("fail") }
                expecting { length } that { it.shouldBe("fail") }
                expectCatching { length } it { shouldBe("fail") }
                expectCatching { length } that { it.shouldBe("fail") }
                expectThrows<RuntimeException> { shouldBe("fail") }
                expectThrows<RuntimeException> { throw RuntimeException() } it { shouldBe("fail") }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.shouldBe("fail") }
            }
        }

    @Test fun tests_without_subject() = tests {
        var currentLine = 37
        testsWithoutSubject.filterIsInstance<DynamicTest>().map { test ->
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
        var currentLine = 63
        testsWithSubject.filterIsInstance<DynamicTest>().map { test ->
            test.displayName to test.testSourceString?.substringAfterLast("?")
        }.shouldContainExactly(
            """❕ ❮ "other" ❯ shouldBe("other")""" to "line=${currentLine++}&column=14",
            """❕ ❮ "subject" ❯ shouldBe("subject")""" to "line=${currentLine++}&column=13",
            """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=13",
            """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=13",
            """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=13",
            """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=13",
            """❗ RuntimeException""" to "line=1&column=1",
            """❗ RuntimeException""" to "line=1&column=1",
            """❗ RuntimeException""" to "line=1&column=1",
        )

        testsWithSubject.filterIsInstance<DynamicContainer>().shouldHaveSize(2) should {
            it.first() should { groupContainer ->
                currentLine += 4
                groupContainer.displayName shouldBe "group"
                groupContainer.testSourceString shouldEndWith "line=${currentLine++}&column=13"
                groupContainer.children.asList().filterIsInstance<DynamicTest>().map { test ->
                    test.displayName to test.testSourceString?.substringAfterLast("?")
                }.shouldContainExactly(
                    """❕ ❮ "subject" ❯ shouldBe("subject")""" to "line=${currentLine++}&column=17",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=17",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=17",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=17",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=17",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                )
            }
            it.last() should { withContainer ->
                currentLine += 5
                withContainer.displayName shouldStartWith "with " shouldEndWith " ❮ \"${"subject".reversed()}\" ❯"
                withContainer.testSourceString shouldEndWith "line=${currentLine++}&column=13"
                currentLine++
                withContainer.children.asList().filterIsInstance<DynamicTest>().map { test ->
                    test.displayName to test.testSourceString?.substringAfterLast("?")
                }.shouldContainExactly(
                    """❕ ❮ "${"subject".reversed()}" ❯ shouldBe("${"subject".reversed()}")""" to "line=${currentLine++}&column=17",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=17",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=17",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=17",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine}&column=17",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                )
            }
        }
    }

    @TestFactory fun run_tests_with_subject() = testsWithSubject

    @TestFactory fun run_failing_tests_with_subject() = failingTestsWithSubject.transform { it.toExceptionExpectingTest<AssertionError>() }


    @Test fun tests_with_subjects() = tests {
        var currentLine = 63
        testsWithSubjects.filterIsInstance<DynamicTest>().map { test ->
            test.displayName to test.testSourceString?.substringAfterLast("?")
        }.shouldContainExactly(
            """❕ ❮ "other" ❯ shouldBe("other")""" to "line=${currentLine++}&column=14",
            """❕ ❮ "subject" ❯ shouldBe("subject")""" to "line=${currentLine++}&column=13",
            """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=13",
            """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=13",
            """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=13",
            """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=13",
            """❗ RuntimeException""" to "line=1&column=1",
            """❗ RuntimeException""" to "line=1&column=1",
            """❗ RuntimeException""" to "line=1&column=1",
        )

        // TODO test three groups

        testsWithSubjects.filterIsInstance<DynamicContainer>().shouldHaveSize(2) should {
            it.first() should { groupContainer ->
                currentLine += 4
                groupContainer.displayName shouldBe "group"
                groupContainer.testSourceString shouldEndWith "line=${currentLine++}&column=13"
                groupContainer.children.asList().filterIsInstance<DynamicTest>().map { test ->
                    test.displayName to test.testSourceString?.substringAfterLast("?")
                }.shouldContainExactly(
                    """❕ ❮ "subject" ❯ shouldBe("subject")""" to "line=${currentLine++}&column=17",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=17",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=17",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=17",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=17",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                )
            }
            it.last() should { withContainer ->
                currentLine += 5
                withContainer.displayName shouldStartWith "with " shouldEndWith " ❮ \"${"subject".reversed()}\" ❯"
                withContainer.testSourceString shouldEndWith "line=${currentLine++}&column=13"
                currentLine++
                withContainer.children.asList().filterIsInstance<DynamicTest>().map { test ->
                    test.displayName to test.testSourceString?.substringAfterLast("?")
                }.shouldContainExactly(
                    """❕ ❮ "${"subject".reversed()}" ❯ shouldBe("${"subject".reversed()}")""" to "line=${currentLine++}&column=17",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=17",
                    """❔ length shouldBeGreaterThan(5)""" to "line=${currentLine++}&column=17",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine++}&column=17",
                    """❓ length isSuccess.shouldBeTrue()""" to "line=${currentLine}&column=17",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                    """❗ RuntimeException""" to "line=1&column=1",
                )
            }
        }
    }

    @TestFactory fun run_tests_with_subjects() = testsWithSubjects

    @TestFactory fun run_failing_tests_with_subjects() = failingTestsWithSubjects.transform { it.toExceptionExpectingTest<AssertionError>() }

    // TODO test multiple
    // TODO "with kotlin.String.() -> ..."
    // TODO write documentation

    @Nested
    inner class TestingSingleSubject {


        @TestFactory
        fun `string subject`() = testing("foo") {
            asserting { shouldBe("foo") }
        }

        @TestFactory
        fun `list subject`() = testing(listOf("foo", "bar")) {
            expecting { sorted().joinToString("+") } that { it.shouldBe("bar+foo") }
        }
    }

    @Nested
    inner class TestingMultipleSubjects {

        @TestFactory
        fun `as parameters`() = testingAll("subject 1", "subject 2", "subject 3") {
            expecting { length } it { shouldBeGreaterThan(0) }
            expecting { length } that { it.shouldBeGreaterThan(0) }

            expectThrows<RuntimeException> { throw RuntimeException() }
            expectCatching { "nope" } it { isSuccess.shouldBeTrue() }
            expectCatching { "nope" } that { it.isSuccess.shouldBeTrue() }

            "foo" asserting { shouldBe("foo") }
            expecting { "$this-foo" } it { shouldStartWith("subject ") }
            expecting { "$this-foo" } that { it.shouldStartWith("subject ") }
            expectCatching { error(this) } it { shouldBeFailure().shouldBeInstanceOf<IllegalStateException>() }
            expectCatching { error(this) } that { it.shouldBeFailure().shouldBeInstanceOf<IllegalStateException>() }
            expectThrows<IllegalStateException> { error(this) }
            expectThrows<IllegalStateException> { error(this) } it { message shouldContain "subject" }
            expectThrows<IllegalStateException> { error(this) } that { it.message shouldContain "subject" }

            group("group") {
                expecting("test") { length } it { shouldBeGreaterThan(0) }
                expecting("test") { length } that { it.shouldBeGreaterThan(0) }

                group("nested group") {
                    asserting { shouldNotBe("automatically named test") }
                }
            }

            expecting { length } it {
                shouldBeGreaterThan(0).shouldBeLessThan(10)
                shouldBe(9)
            }
        }

        @TestFactory
        fun `string subjects`() = testingAll("foo", "bar") {
            asserting { shouldHaveLength(3) }
        }

        @TestFactory
        fun `list subjects`() = testingAll(listOf("foo", "bar"), listOf("bar", "foo")) {
            expecting { sorted().joinToString("+") } that { it.shouldBe("bar+foo") }
        }
    }

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
