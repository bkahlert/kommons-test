package com.bkahlert.kommons.test

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.paths.shouldBeADirectory
import io.kotest.matchers.paths.shouldBeAFile
import io.kotest.matchers.paths.shouldNotExist
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE
import java.nio.file.attribute.PosixFilePermission.OWNER_READ
import java.nio.file.attribute.PosixFilePermission.OWNER_WRITE
import kotlin.io.path.getPosixFilePermissions

class PathsKtTest {

    @Nested
    inner class RandomPath {

        @Test
        fun `should create inside receiver path`(@TempDir tempDir: Path) = test {
            tempDir.randomPath().isSubPathOf(tempDir) shouldBe true
        }

        @Test
        fun `should not exist`(@TempDir tempDir: Path) = test {
            tempDir.randomPath().shouldNotExist()
        }
    }

    @Nested
    inner class RandomDirectory {

        @Test
        fun `should create inside receiver path`(@TempDir tempDir: Path) = test {
            tempDir.randomDirectory().isSubPathOf(tempDir) shouldBe true
        }

        @Test
        fun `should create directory`(@TempDir tempDir: Path) = test {
            tempDir.randomDirectory().shouldBeADirectory()
        }

        @Test
        fun `should create directory inside non-existent parent`(@TempDir tempDir: Path) = test {
            tempDir.randomPath().randomDirectory().shouldBeADirectory()
        }
    }

    @Nested
    inner class RandomFile {

        @Test
        fun `should create inside receiver path`(@TempDir tempDir: Path) = test {
            tempDir.randomFile().isSubPathOf(tempDir) shouldBe true
        }

        @Test
        fun `should create regular file`(@TempDir tempDir: Path) = test {
            tempDir.randomFile().shouldBeAFile()
        }

        @Test
        fun `should create regular file inside non-existent parent`(@TempDir tempDir: Path) = test {
            tempDir.randomPath().randomFile().shouldBeAFile()
        }
    }

    @Test fun temp_dir() = test {
        tempDir() should { tmp ->
            tmp.isSubPathOf(KommonsTest.Temp) shouldBe true
            tmp.shouldBeADirectory()
            tmp.getPosixFilePermissions() shouldContainExactly setOf(
                OWNER_READ,
                OWNER_WRITE,
                OWNER_EXECUTE
            )

            tmp.tempDir() should { sub ->
                sub.isSubPathOf(tmp) shouldBe true
                sub.shouldBeADirectory()
                sub.getPosixFilePermissions() shouldContainExactly setOf(
                    OWNER_READ,
                    OWNER_WRITE,
                    OWNER_EXECUTE
                )
            }


            val nonExistentParent = tmp.randomPath()
            nonExistentParent.tempDir() should {
                it.isSubPathOf(nonExistentParent) shouldBe true
                it.shouldBeADirectory()
                it.getPosixFilePermissions() shouldContainExactly setOf(
                    OWNER_READ,
                    OWNER_WRITE,
                    OWNER_EXECUTE
                )
            }
        }
    }

    @Test fun temp_file() {
        tempFile() should { tmp ->
            tmp.isSubPathOf(KommonsTest.Temp) shouldBe true
            tmp.shouldBeAFile()
            tmp.getPosixFilePermissions() shouldContainExactly setOf(
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.OWNER_EXECUTE
            )
        }
        tempDir().let { tmp ->
            tmp.tempFile() should { sub ->
                sub.isSubPathOf(tmp) shouldBe true
                sub.shouldBeAFile()
                sub.getPosixFilePermissions() shouldContainExactly setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE
                )
            }

            val nonExistentParent = tmp.randomPath()
            nonExistentParent.tempFile() should {
                it.isSubPathOf(nonExistentParent) shouldBe true
                it.shouldBeAFile()
                it.getPosixFilePermissions() shouldContainExactly setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE
                )
            }
        }
    }
}
