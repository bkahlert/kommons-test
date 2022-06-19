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
import kotlin.io.path.getPosixFilePermissions

class PathsKtTest {

    @Nested
    inner class RandomPath {

        @Test
        fun `should create inside receiver path`(@TempDir tempDir: Path) = tests {
            tempDir.randomPath().isSubPathOf(tempDir) shouldBe true
        }

        @Test
        fun `should not exist`(@TempDir tempDir: Path) = tests {
            tempDir.randomPath().shouldNotExist()
        }
    }

    @Nested
    inner class RandomDirectory {

        @Test
        fun `should create inside receiver path`(@TempDir tempDir: Path) = tests {
            tempDir.randomDirectory().isSubPathOf(tempDir) shouldBe true
        }

        @Test
        fun `should create directory`(@TempDir tempDir: Path) = tests {
            tempDir.randomDirectory().shouldBeADirectory()
        }

        @Test
        fun `should create directory inside non-existent parent`(@TempDir tempDir: Path) = tests {
            tempDir.randomPath().randomDirectory().shouldBeADirectory()
        }
    }

    @Nested
    inner class RandomFile {

        @Test
        fun `should create inside receiver path`(@TempDir tempDir: Path) = tests {
            tempDir.randomFile().isSubPathOf(tempDir) shouldBe true
        }

        @Test
        fun `should create regular file`(@TempDir tempDir: Path) = tests {
            tempDir.randomFile().shouldBeAFile()
        }

        @Test
        fun `should create regular file inside non-existent parent`(@TempDir tempDir: Path) = tests {
            tempDir.randomPath().randomFile().shouldBeAFile()
        }
    }

    @Test fun temp_dir() = tests {
        tempDir() should { tmp ->
            tmp.isSubPathOf(KommonsTest.Temp) shouldBe true
            tmp.shouldBeADirectory()
            tmp.getPosixFilePermissions() shouldContainExactly setOf(
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.OWNER_EXECUTE
            )

            tmp.tempDir() should { sub ->
                sub.isSubPathOf(tmp) shouldBe true
                sub.shouldBeADirectory()
                sub.getPosixFilePermissions() shouldContainExactly setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE
                )
            }


            val nonExistentParent = tmp.randomPath()
            nonExistentParent.tempDir() should {
                it.isSubPathOf(nonExistentParent) shouldBe true
                it.shouldBeADirectory()
                it.getPosixFilePermissions() shouldContainExactly setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE
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
