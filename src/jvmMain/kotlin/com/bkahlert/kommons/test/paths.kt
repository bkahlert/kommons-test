package com.bkahlert.kommons.test

import com.bkahlert.kommons.test.com.bkahlert.kommons.createTempFile
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import java.net.URI
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Path
import java.util.jar.JarOutputStream
import kotlin.io.path.outputStream
import kotlin.io.path.pathString

/**
 * Creates an empty `jar` file in the system's temp directory.
 *
 * @see toNewJarFileSystem
 */
public fun Path.createTempJarFile(prefix: String? = null, suffix: String? = ".jar"): Path =
    createTempFile(prefix, suffix).apply {
        JarOutputStream(outputStream().buffered()).use { }
    }

/**
 * Attempts to create a [FileSystem] from this existing `jar` file.
 *
 * @see createTempJarFile
 */
public fun Path.toNewJarFileSystem(vararg env: Pair<String, Any?>): FileSystem =
    FileSystems.newFileSystem(URI.create("jar:${toUri()}"), env.toMap())

/**
 * Creates an empty `jar` file system the default temp directory.
 *
 * @see createTempJarFile
 * @see toNewJarFileSystem
 */
public fun Path.createTempJarFileSystem(base: String = "", extension: String = ".jar"): FileSystem =
    createTempJarFile(base, extension).toNewJarFileSystem()


// TODO test and make public
internal fun Path.shouldEndWith(vararg names: String) = this should endWith(*names)
internal fun Path.shouldNotEndWith(vararg names: String) = this shouldNot endWith(*names)

internal fun endWith(vararg names: String) = object : Matcher<Path> {
    override fun test(value: Path): MatcherResult = MatcherResult(
        value.map { it.pathString }.takeLast(names.size) == names.asList(),
        { "Path $value should end with $names" },
        { "Path $value should not end with $names" })
}
