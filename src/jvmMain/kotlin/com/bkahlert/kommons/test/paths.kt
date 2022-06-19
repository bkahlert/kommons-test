package com.bkahlert.kommons.test

import java.net.URI
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.util.jar.JarOutputStream
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.outputStream
import kotlin.io.path.setPosixFilePermissions
import kotlin.io.path.writeBytes
import kotlin.random.Random

/**
 * Returns this [Path] with all parent directories created.
 *
 * Example: If directory `/some/where` existed and this method was called on `/some/where/resides/a/file`,
 * the missing directories `/some/where/resides` and `/some/where/resides/a` would be created.
 */
private fun Path.createParentDirectories(): Path = apply { parent?.takeUnless { it.exists() }?.createDirectories() }

/**
 * Returns whether this path is a sub path of [path].
 */
internal fun Path.isSubPathOf(path: Path): Boolean = normalize().toAbsolutePath().startsWith(path.normalize().toAbsolutePath())

/** Creates a random string of the specified [length] made up of the specified [allowedCharacters]. */
private fun randomString(length: Int = 16, vararg allowedCharacters: Char = (('0'..'9') + ('a'..'z') + ('A'..'Z')).toCharArray()): String =
    buildString(length) { repeat(length) { append(allowedCharacters[Random.nextInt(0, allowedCharacters.size)]) } }


/**
 * Returns this [Path] with a path segment added.
 *
 * The path segment is created based on [base] and [extension] and a random
 * string in between.
 *
 * The newly created [Path] is guaranteed to not already exist.
 */
public tailrec fun Path.randomPath(base: String = randomString(4), extension: String = ""): Path {
    val minLength = 6
    val length = base.length + extension.length
    val randomSuffix = randomString((minLength - length).coerceAtLeast(3))
    val randomPath = resolve("${base.takeUnless { it.isEmpty() }?.let { "$it--" } ?: ""}$randomSuffix$extension")
    return randomPath.takeUnless { it.exists() } ?: randomPath(base, extension)
}


/*
 * Random directories / files
 */

/**
 * Creates a random directory inside this [Path].
 *
 * Eventually missing directories are automatically created.
 */
public fun Path.randomDirectory(base: String = randomString(4), extension: String = "-tmp"): Path =
    randomPath(base, extension).createDirectories()

/**
 * Creates a random file inside this [Path].
 *
 * Eventually missing directories are automatically created.
 */
public fun Path.randomFile(base: String = randomString(4), extension: String = ".tmp"): Path =
    randomPath(base, extension).createParentDirectories().createFile()


/*
 * Temporary directories / files
 */

/**
 * Checks if this path is inside of one of the System's temporary directories,
 * or throws an [IllegalArgumentException] otherwise.
 */
public fun Path.requireTempSubPath(): Path =
    apply {
        require(fileSystem != FileSystems.getDefault() || isSubPathOf(KommonsTest.Temp)) {
            "${normalize().toAbsolutePath()} is not inside ${KommonsTest.Temp}."
        }
    }

/**
 * Creates a temporary directory inside the system's temporary directory.
 *
 * The POSIX permissions are set to `700`.
 */
public fun tempDir(base: String = "", extension: String = ""): Path =
    KommonsTest.Temp.tempDir(base, extension)

/**
 * Creates a temporary file inside the system's temporary directory.
 *
 * The POSIX permissions are set to `700`.
 */
public fun tempFile(base: String = "", extension: String = ""): Path =
    KommonsTest.Temp.tempFile(base, extension)

/**
 * Creates a temporary directory inside this temporary directory.
 *
 * The POSIX permissions are set to `700`.
 *
 * Attempting to create a temporary directory outside the system's temporary directory will
 * throw an [IllegalArgumentException].
 */
public fun Path.tempDir(base: String = "", extension: String = ""): Path =
    requireTempSubPath().randomDirectory(base, extension).apply {
        setPosixFilePermissions(setOf(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE))
    }

/**
 * Creates a temporary file inside this temporary directory.
 *
 * The POSIX permissions are set to `700`.
 *
 * Attempting to create a temporary directory outside the system's temporary directory will
 * throw an [IllegalArgumentException].
 */
public fun Path.tempFile(base: String = "", extension: String = ""): Path =
    requireTempSubPath().randomFile(base, extension).apply {
        setPosixFilePermissions(setOf(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE))
    }


/** Creates a single file with the specified [name] in this directory with the specified [content]. */
public fun Path.singleFile(
    name: String = "example.html",
    content: String = "content $name",
): Path = resolve(name).apply {
    writeBytes(content.encodeToByteArray())
    check(exists()) { "Failed to create single file." }
}

/** Creates a random directory with the specified [base] in this directory containing a [singleFile] and a directory with another [singleFile]. */
public fun Path.directoryWithTwoFiles(
    base: String = randomString(4),
): Path = randomDirectory(base).apply {
    singleFile()
    resolve("sub-dir").createDirectories().singleFile("config.txt")
    check(listDirectoryEntries().size == 2) { "Failed to create directory with two files." }
}


/**
 * Creates an empty `jar` file in the system's temp directory.
 *
 * @see asNewJarFileSystem
 */
public fun Path.tempJar(base: String = "", extension: String = ".jar"): Path =
    tempFile(base, extension).apply {
        JarOutputStream(outputStream().buffered()).use { }
    }

/**
 * Attempts to create a [FileSystem] from an existing `jar` file.
 *
 * @see tempJar
 */
public fun Path.asNewJarFileSystem(vararg env: Pair<String, Any?>): FileSystem =
    FileSystems.newFileSystem(URI.create("jar:${toUri()}"), env.toMap())

/**
 * Creates an empty `jar` file system the system's temp directory.
 *
 * @see tempJar
 * @see asNewJarFileSystem
 */
public fun Path.tempJarFileSystem(base: String = "", extension: String = ".jar"): FileSystem =
    tempJar(base, extension).asNewJarFileSystem()
