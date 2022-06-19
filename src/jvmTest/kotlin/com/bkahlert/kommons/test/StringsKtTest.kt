package com.bkahlert.kommons.test

import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldNotContain
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class StringsKtTest {

    private val emptyException = RuntimeException()

    private val runtimeException = RuntimeException(
        "Something happened\n" +
            " ➜ A dump has been written to:\n" +
            "   - file:///var/folders/…/file.log (unchanged)\n" +
            "   - file:///var/folders/…/file.ansi-removed.log (ANSI escape/control sequences removed)\n" +
            " ➜ The last lines are:\n" +
            "    raspberry\n" +
            "    Login incorrect\n" +
            "    raspberrypi login:"
    )

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

        Result.success("good").toCompactString() shouldBe "good"
        Result.success(Paths.get("/path")).toCompactString().toCompactString() shouldBe "file:///path"
        Result.success(emptyList<Any>()).toCompactString() shouldBe "[]"
        Result.success(arrayOf("a", "b")).toCompactString() shouldBe Result.success(listOf("a", "b")).toCompactString()
        LineSeparators.joinToString("") { "line$it" }.toCompactString() shouldBe "line⏎line⏎line⏎line⏎line⏎line"
    }
}
