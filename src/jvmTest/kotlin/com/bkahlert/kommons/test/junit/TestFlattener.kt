package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.junit.TestFlattener.flatten
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import kotlin.streams.asSequence

/** Utility to convert hierarchical tests to a flat sequence of tests. */
internal object TestFlattener {

    /** Converts this test hierarchy to a flat sequence of tests. */
    fun Iterable<DynamicNode>.flatten(): Sequence<DynamicTest> = asSequence().flatten()

    /** Converts this test hierarchy to a flat sequence of tests. */
    fun Sequence<DynamicNode>.flatten(): Sequence<DynamicTest> = flatMap { it.flatten() }

    /** Converts this test hierarchy to a flat sequence of tests. */
    private fun DynamicNode.flatten(): Sequence<DynamicTest> = when (this) {
        is DynamicContainer -> flatten()
        is DynamicTest -> flatten()
        else -> error("Unknown ${DynamicNode::class.simpleName} type ${this::class}")
    }

    /** Converts this test to a sequence containing this test. */
    private fun DynamicTest.flatten(): Sequence<DynamicTest> = sequenceOf(this)

    /** Converts this test container to a flat sequence containing all contained tests. */
    private fun DynamicContainer.flatten(): Sequence<DynamicTest> = children.asSequence().flatten()
}

/** Runs all tests in this test hierarchy as a flat sequence of tests. */
fun Iterable<DynamicNode>.execute() {
    flatten().forEach { test: DynamicTest -> test.execute() }
}

/** Runs this test. */
fun DynamicTest.execute() {
    executable.execute()
}
