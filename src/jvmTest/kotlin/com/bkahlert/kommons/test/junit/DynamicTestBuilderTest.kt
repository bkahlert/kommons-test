package com.bkahlert.kommons.test.junit

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory

class DynamicTestBuilderTest {

    @TestFactory
    fun `testing with subject`() = test("subject") {

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

    @TestFactory
    fun `testing without subject`() = dynamicTests {

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


    @Nested
    inner class TestingSingleSubject {

        @TestFactory
        fun `as parameter`() = test("subject") {

            "other" asserting { shouldBe("other") }
            asserting { shouldBe("subject") }
            expecting { length } it { shouldBeGreaterThan(5) }
            expecting { length } that { it.shouldBeGreaterThan(5) }
            expectCatching { length } it { isSuccess.shouldBeTrue() }
            expectCatching { length } that { it.isSuccess.shouldBeTrue() }
            expectThrows<RuntimeException> { throw RuntimeException() }
            expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
            expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }

            group("group") {

                asserting { shouldBe("subject") }
                expecting { length } it { shouldBeGreaterThan(5) }
                expecting { length } that { it.shouldBeGreaterThan(5) }
                expectCatching { length } it { isSuccess.shouldBeTrue() }
                expectCatching { length } that { it.isSuccess.shouldBeTrue() }
                expectThrows<RuntimeException> { throw RuntimeException() }
                expectThrows<RuntimeException> { throw RuntimeException() } it { message.isNullOrEmpty() }
                expectThrows<RuntimeException> { throw RuntimeException() } that { it.message.isNullOrEmpty() }
            }

            with { reversed() } then {

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

        @TestFactory
        fun `string subject`() = test("foo") {
            asserting { shouldBe("foo") }
        }

        @TestFactory
        fun `list subject`() = test(listOf("foo", "bar")) {
            expecting { joinToString("+") } that { it.shouldBe("foo+bar") }
        }
    }

    @Nested
    inner class TestingMultipleSubjects {

        @TestFactory
        fun `as parameters`() = testEach("subject 1", "subject 2", "subject 3") {

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

            group("integrated strikt") {
                expecting { length } it { shouldBeGreaterThan(0) }
                expecting { length } that { it.shouldBeGreaterThan(0) }

                with { reversed() }.then {
                    asserting { shouldNotBe("automatically named test") }
                }
            }

            expecting { length } it {
                shouldBeGreaterThan(0).shouldBeLessThan(10)
                shouldBe(9)
            }
        }

        @TestFactory
        fun `string subjects`() = testEach("foo", "bar") {
            asserting { shouldHaveLength(3) }
        }

        @TestFactory
        fun `list subjects`() = testEach(listOf("foo", "bar"), listOf("bar", "baz")) {
            expecting { joinToString("+") } that { it.shouldBe("foo+bar") }
        }
    }
}
