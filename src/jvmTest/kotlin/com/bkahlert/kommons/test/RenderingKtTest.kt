package com.bkahlert.kommons.test

import com.bkahlert.kommons.emptyException
import com.bkahlert.kommons.runtimeException
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldNotContain
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class RenderingKtTest {

    @Test fun to_compact_string() = tests {
        runtimeException.toCompactString() should {
            it shouldStartWith "RuntimeException: Something happened at.(StringsKtTest.kt:"
            it shouldEndWith ")"
            it shouldNotContain "\n"
        }
        emptyException.toCompactString() should {
            it shouldStartWith "RuntimeException at.(StringsKtTest.kt:"
            it shouldEndWith ")"
            it shouldNotContain "\n"
        }

        Result.failure<String>(runtimeException).toCompactString() should {
            it shouldStartWith "RuntimeException: Something happened at.(StringsKtTest.kt:"
            it shouldEndWith ")"
            it shouldNotContain "\n"
        }
        Result.failure<String>(emptyException).toCompactString() should {
            it shouldStartWith "RuntimeException at.(StringsKtTest.kt:"
            it shouldEndWith ")"
            it shouldNotContain "\n"
        }

        Result.success("good").toCompactString() shouldBe "\"good\""
        Result.success(Paths.get("/path")).toCompactString() shouldBe "file:///path"
        Result.success(emptyList<Any>()).toCompactString() shouldBe "[]"
        Result.success(arrayOf("a", "b")).toCompactString() shouldBe Result.success(listOf("a", "b")).toCompactString()
        LineSeparators.joinToString("") { "line$it" }.toCompactString() shouldBe "\"line⏎line⏎line⏎line⏎line⏎line\""
    }
}
