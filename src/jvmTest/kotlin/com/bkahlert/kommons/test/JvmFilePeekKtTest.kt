package com.bkahlert.kommons.test

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.paths.shouldBeADirectory
import io.kotest.matchers.paths.shouldBeAFile
import io.kotest.matchers.paths.shouldExist
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldEndWith
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.io.path.pathString

class JvmFilePeekKtTest {

    @Test fun find_classes_directory_or_null() = testAll(
        { this::class.findClassesDirectoryOrNull() },
        { javaClass.findClassesDirectoryOrNull() },
        { StaticClass::class.findClassesDirectoryOrNull() },
        { StaticClass::class.java.findClassesDirectoryOrNull() },
        { InnerClass::class.findClassesDirectoryOrNull() },
        { InnerClass::class.java.findClassesDirectoryOrNull() },
    ) { compute ->
        compute().shouldNotBeNull() should { dir ->
            dir.shouldBeADirectory()
            dir.map { it.pathString }.takeLast(3).shouldContainExactly("kotlin", "jvm", "test")
        }

        String::class.findClassesDirectoryOrNull().shouldBeNull()
    }

    @Test fun find_source_directory_or_null() = testAll(
        { this::class.findSourceDirectoryOrNull() },
        { javaClass.findSourceDirectoryOrNull() },
        { StaticClass::class.findSourceDirectoryOrNull() },
        { StaticClass::class.java.findSourceDirectoryOrNull() },
        { InnerClass::class.findSourceDirectoryOrNull() },
        { InnerClass::class.java.findSourceDirectoryOrNull() },
    ) { compute ->
        compute().shouldNotBeNull() should { dir ->
            dir.shouldBeADirectory()
            dir.map { it.pathString }.takeLast(3).shouldContainExactly("src", "jvmTest", "kotlin")
        }

        String::class.findSourceDirectoryOrNull().shouldBeNull()
    }

    @Test fun find_source_file_or_null() = testAll(
        { this::class.findSourceFileOrNull() },
        { javaClass.findSourceFileOrNull() },
        { StaticClass::class.findSourceFileOrNull() },
        { StaticClass::class.java.findSourceFileOrNull() },
        { InnerClass::class.findSourceFileOrNull() },
        { InnerClass::class.java.findSourceFileOrNull() },
    ) { compute ->
        compute().shouldNotBeNull() should { file ->
            file.shouldBeAFile()
            file.map { it.pathString }.takeLast(8).shouldContainExactly(
                "src", "jvmTest", "kotlin", "com", "bkahlert", "kommons", "debug", "ReflectKtTest.kt"
            )
        }

        String::class.findSourceFileOrNull().shouldBeNull()
    }

    inner class StaticClass
    inner class InnerClass

    @Test fun get_caller_file_info() = tests {
        FilePeekMPP.getCallerFileInfo(
            try {
                throw RuntimeException()
            } catch (e: Throwable) {
                e.stackTrace.first()
            }
        ).shouldNotBeNull() should {
            it.lineNumber shouldBe 17
            it.sourceFileName should { name ->
                name shouldEndWith "src/jvmTest/kotlin/com/bkahlert/kommons/JvmFilePeekKtTest.kt"
                Paths.get(name).shouldExist()
            }
            it.line shouldBe "throw RuntimeException()"
            it.methodName shouldBe "get_caller_file_info"
        }
    }

    @Test fun lambda_body_get_or_null() = tests {
        fun <R> call(block: () -> R): R = block()

        kotlin.runCatching {
            call { throw RuntimeException() }
        }.exceptionOrNull()?.stackTrace?.first()?.let {
            LambdaBody.getOrNull(it)
        } shouldBe "throw RuntimeException()"
    }
}
