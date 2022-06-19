package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.ansiRemoved
import com.bkahlert.kommons.test.tests
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.platform.engine.ConfigurationParameters
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.TestDescriptor.Type
import org.junit.platform.engine.TestExecutionResult
import org.junit.platform.engine.TestSource
import org.junit.platform.engine.TestTag
import org.junit.platform.engine.UniqueId
import org.junit.platform.launcher.TestIdentifier
import org.junit.platform.launcher.TestPlan
import java.util.Optional

class TestExecutionReporterTest {

    @Test fun no_tests() = tests {
        testExecutionReporterOutput(0, 0, 0).ansiRemoved shouldBe """
            ⁉︎ no tests executed
        """.trimIndent()
    }

    @Test fun aborted_tests() = tests {
        testExecutionReporterOutput(0, 0, 1).ansiRemoved shouldBe """
            ! the test crashed
        """.trimIndent()
        testExecutionReporterOutput(0, 0, 2).ansiRemoved shouldBe """
            ! all 2 tests crashed
        """.trimIndent()
        testExecutionReporterOutput(1, 0, 2).ansiRemoved shouldBe """
            ! 2 out of 3 tests crashed
        """.trimIndent()
    }

    @Test fun failed_tests() = tests {
        testExecutionReporterOutput(0, 1, 0).ansiRemoved shouldBe """
            ✘︎ the test failed
        """.trimIndent()
        testExecutionReporterOutput(0, 2, 0).ansiRemoved shouldBe """
            ✘︎ all 2 tests failed
        """.trimIndent()
        testExecutionReporterOutput(1, 2, 0).ansiRemoved shouldBe """
            ✘︎ 2 out of 3 tests failed
        """.trimIndent()
    }

    @Test fun aborted_and_failed_tests() = tests {
        testExecutionReporterOutput(1, 2, 1).ansiRemoved shouldBe """
            ✘︎ 2 out of 4 tests failed
            ! 1 out of 4 tests crashed
        """.trimIndent()
        testExecutionReporterOutput(1, 1, 2).ansiRemoved shouldBe """
            ✘︎ 1 out of 4 tests failed
            ! 2 out of 4 tests crashed
        """.trimIndent()
    }

    @Test fun successful_tests() = tests {
        testExecutionReporterOutput(1, 0, 0).ansiRemoved shouldBe """
            ✔︎ the test passed within 0s
        """.trimIndent()
        testExecutionReporterOutput(2, 0, 0).ansiRemoved shouldBe """
            ✔︎ all 2 tests passed within 0s
        """.trimIndent()
    }
}

private fun testExecutionReporterOutput(
    passed: Int,
    failed: Int,
    aborted: Int,
): String {
    val testPlan = TestPlan.from(emptyList(), configurationParameters())
    val lines = mutableListOf<String>()
    TestExecutionReporter { lines.add(it) }.apply {
        testPlanExecutionStarted(testPlan)
        repeat(passed) { executionFinished(testIdentifier(), TestExecutionResult.successful()) }
        repeat(failed) { executionFinished(testIdentifier(), TestExecutionResult.failed(RuntimeException())) }
        repeat(aborted) { executionFinished(testIdentifier(), TestExecutionResult.aborted(RuntimeException())) }
        testPlanExecutionFinished(testPlan)
    }
    return lines.joinToString("\n")
}

private fun configurationParameters(vararg entries: Pair<String?, String>) =
    object : ConfigurationParameters {
        override fun get(key: String?): Optional<String> =
            Optional.ofNullable(entries.firstOrNull { it.first == key }?.second)

        override fun getBoolean(key: String?): Optional<Boolean> =
            get(key).flatMap { Optional.ofNullable(it.toBoolean()) }

        override fun size(): Int = entries.size
        override fun keySet(): MutableSet<String> = entries.mapNotNull { it.first }.toMutableSet()
    }

private fun testIdentifier(testDescriptor: TestDescriptor = testDescriptor()): TestIdentifier =
    TestIdentifier.from(testDescriptor)

private fun testDescriptor(): TestDescriptor =
    object : TestDescriptor {
        override fun getUniqueId(): UniqueId =
            UniqueId.parse("[class:foo.FooTest]/[method:bar(baz.Baz)]")

        override fun getDisplayName(): String = "bar(Baz)"
        override fun getTags(): MutableSet<TestTag> = mutableSetOf()
        override fun getSource(): Optional<TestSource> = Optional.empty()
        override fun getParent(): Optional<TestDescriptor> = Optional.empty()
        override fun setParent(parent: TestDescriptor?) = throw RuntimeException("not implemented")
        override fun getChildren(): MutableSet<out TestDescriptor> = mutableSetOf()
        override fun addChild(descriptor: TestDescriptor?) = throw RuntimeException("not implemented")
        override fun removeChild(descriptor: TestDescriptor?) = throw RuntimeException("not implemented")
        override fun removeFromHierarchy() = throw RuntimeException("not implemented")
        override fun getType(): Type = Type.TEST
        override fun findByUniqueId(uniqueId: UniqueId?): Optional<out TestDescriptor> = throw RuntimeException("not implemented")
    }