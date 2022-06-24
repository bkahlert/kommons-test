package com.bkahlert.kommons.test.com.bkahlert.kommons.debug

import com.bkahlert.kommons.test.test
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldNotContain
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class RenderingKtTest {

    @Test fun to_compact_string() = test {
        runtimeException.toCompactString() should {
            it shouldStartWith "RuntimeException: Something happened at.(RenderingKtTest.kt:"
            it shouldEndWith ")"
            it shouldNotContain "\n"
        }
        emptyException.toCompactString() should {
            it shouldStartWith "RuntimeException at.(RenderingKtTest.kt:"
            it shouldEndWith ")"
            it shouldNotContain "\n"
        }

        Result.failure<String>(runtimeException).toCompactString() should {
            it shouldStartWith "RuntimeException: Something happened at.(RenderingKtTest.kt:"
            it shouldEndWith ")"
            it shouldNotContain "\n"
        }
        Result.failure<String>(emptyException).toCompactString() should {
            it shouldStartWith "RuntimeException at.(RenderingKtTest.kt:"
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

internal val emptyException = RuntimeException()
internal val runtimeException = RuntimeException(
    "Something happened\n" +
        " ➜ A dump has been written to:\n" +
        "   - file:///var/folders/…/file.log (unchanged)\n" +
        "   - file:///var/folders/…/file.ansi-removed.log (ANSI escape/control sequences removed)\n" +
        " ➜ The last lines are:\n" +
        "    raspberry\n" +
        "    Login incorrect\n" +
        "    raspberrypi login:"
)
