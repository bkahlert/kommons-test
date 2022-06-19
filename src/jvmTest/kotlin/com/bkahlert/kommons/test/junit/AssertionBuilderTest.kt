package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.junit.IllegalUsageCheck.ExpectIllegalUsageException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

class AssertionBuilderTest {

    @Test fun infix_asserting() {
        shouldNotThrowAny { "subject" asserting { shouldBe("subject") } }
        shouldThrow<AssertionError> { "subject" asserting { shouldBe("other") } }
            .message shouldBe """
                subject
                expected:<"other"> but was:<"subject">
            """.trimIndent()
    }

    @Test fun asserting() {
        shouldNotThrowAny { asserting("subject") { shouldBe("subject") } }
        shouldThrow<AssertionError> { asserting("subject") { shouldBe("other") } }
            .message shouldBe """
                subject
                expected:<"other"> but was:<"subject">
            """.trimIndent()
    }

    @Test fun expecting() {
        shouldNotThrowAny { expecting { "subject" } it { shouldBe("subject") } }
        shouldNotThrowAny { expecting { "subject" } that { it.shouldBe("subject") } }

        shouldThrow<AssertionError> { expecting { "subject" } it { shouldBe("other") } }
            .message shouldBe """
                subject
                expected:<"other"> but was:<"subject">
            """.trimIndent()

        shouldThrow<AssertionError> { expecting { "subject" } that { it.shouldBe("other") } }
            .message shouldBe """
                subject
                expected:<"other"> but was:<"subject">
            """.trimIndent()
    }

    @Test
    @ExtendWith(ExpectIllegalUsageException::class)
    fun incomplete_expecting() {
        expecting { "subject" }
    }


    @Test fun expect_catching() {
        shouldNotThrowAny { expectCatching { throw IllegalArgumentException("message") } it { shouldBeFailure() } }
        shouldNotThrowAny { expectCatching { throw IllegalArgumentException("message") } that { it.shouldBeFailure() } }

        shouldThrow<AssertionError> { expectCatching { throw IllegalArgumentException("message") } it { shouldBeFailure().message shouldBe "other" } }
            .message shouldBe """
                Failure(java.lang.IllegalArgumentException: message)
                expected:<"other"> but was:<"message">
            """.trimIndent()
        shouldThrow<AssertionError> { expectCatching { throw IllegalArgumentException("message") } that { it.shouldBeFailure().message shouldBe "other" } }
            .message shouldBe """
                Failure(java.lang.IllegalArgumentException: message)
                expected:<"other"> but was:<"message">
            """.trimIndent()

        shouldThrow<AssertionError> { expectCatching { "subject" } it { shouldBeFailure() } }
            .message shouldBe """
                Success(subject)
                Result should be a failure but was subject
            """.trimIndent()
        shouldThrow<AssertionError> { expectCatching { "subject" } that { it.shouldBeFailure() } }
            .message shouldBe """
                Success(subject)
                Result should be a failure but was subject
            """.trimIndent()
    }

    @Test
    @ExtendWith(ExpectIllegalUsageException::class)
    fun incomplete_expect_catching() {
        expectCatching { throw IllegalArgumentException("message") }
    }

    @Test
    fun expect_throws() {
        shouldNotThrowAny { expectThrows<IllegalArgumentException> { throw IllegalArgumentException("message") } }
        shouldNotThrowAny { expectThrows<IllegalArgumentException> { throw IllegalArgumentException("message") } it { message shouldBe "message" } }
        shouldNotThrowAny { expectThrows<IllegalArgumentException> { throw IllegalArgumentException("message") } that { it.message shouldBe "message" } }

        shouldThrow<AssertionError> { expectThrows<IllegalStateException> { throw IllegalArgumentException("message") } }
            .message shouldBe """
                Expected exception java.lang.IllegalStateException but a IllegalArgumentException was thrown instead.
            """.trimIndent()
        shouldThrow<AssertionError> { expectThrows<IllegalArgumentException> { throw IllegalArgumentException("message") } it { message shouldBe "other" } }
            .message shouldBe """
                java.lang.IllegalArgumentException: message
                expected:<"other"> but was:<"message">
            """.trimIndent()
        shouldThrow<AssertionError> { expectThrows<IllegalArgumentException> { throw IllegalArgumentException("message") } that { it.message shouldBe "other" } }
            .message shouldBe """
                java.lang.IllegalArgumentException: message
                expected:<"other"> but was:<"message">
            """.trimIndent()
    }
}
