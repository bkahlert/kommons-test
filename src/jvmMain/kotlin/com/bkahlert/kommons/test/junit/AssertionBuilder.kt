package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.KommonsTest
import com.bkahlert.kommons.test.junit.PathSource.Companion.sourceUri
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow

/** An assertion testing a subject. */
public typealias Assertion<T> = T.() -> Unit

/** Builder that allows adding additional assertions using [it] or [that]. */
@JvmInline
public value class AssertionBuilder<T>(
    /** Function that can be used to verify assertions passed to [it] or [that]. */
    public val applyAssertions: (Assertion<T>) -> Unit,
) {
    /** Specifies [assertions] with the subject in the receiver `this`. */
    @Suppress("NOTHING_TO_INLINE")
    public inline infix fun it(noinline assertions: T.() -> Unit): Unit = applyAssertions(assertions)

    /** Specifies [assertions] with the subject passed the single parameter `it`. */
    @Suppress("NOTHING_TO_INLINE")
    public inline infix fun that(noinline assertions: (T) -> Unit): Unit = applyAssertions(assertions)
}

/**
 * Expects this subject to fulfil the given [assertion].
 *
 * **Usage:** `<subject> asserting { <assertion> }`
 */
@JvmName("infixAsserting")
public infix fun <T> T.asserting(assertion: Assertion<T>): Unit =
    asClue(assertion)

/**
 * Expects the [subject] to fulfil the given [assertion].
 *
 * **Usage:** `asserting(<subject>) { <assertion> }`
 */
public fun <T> asserting(subject: T, assertion: Assertion<T>): Unit =
    subject.asClue(assertion)

/**
 * Expects the subject returned by [action] to fulfil the
 * assertion returned by [AssertionBuilder].
 *
 * **Usage:** `expecting { <action> } it { <assertion> }`
 *
 * **Usage:** `expecting { <action> } that { it.<assertion> }`
 */
public fun <T> expecting(action: () -> T): AssertionBuilder<T> {
    val caller = KommonsTest.locateCall()
    val id = SimpleId.from(caller)
    IllegalUsageCheck.illegalUsages[id] = IllegalUsageException("expecting", caller.sourceUri)
    val subject = action()
    return AssertionBuilder { assertion: Assertion<T> ->
        IllegalUsageCheck.illegalUsages.remove(id)
        subject.asClue(assertion)
    }
}

/**
 * Expects the [Result] returned by [action] to fulfil the
 * assertion returned by [AssertionBuilder].
 *
 * **Usage:** `expectCatching { <action> } it { <assertion> }`
 *
 * **Usage:** `expectCatching { <action> } that { it.<assertion> }`
 */
public fun <T> expectCatching(action: () -> T): AssertionBuilder<Result<T>> {
    val caller = KommonsTest.locateCall()
    val id = SimpleId.from(caller)
    IllegalUsageCheck.illegalUsages[id] = IllegalUsageException("expectCatching", caller.sourceUri)
    val result = runCatching(action)
    return AssertionBuilder { additionalAssertion: Assertion<Result<T>> ->
        IllegalUsageCheck.illegalUsages.remove(id)
        result.asClue(additionalAssertion)
    }
}

/**
 * Expects an exception [E] to be thrown when running [action]
 * and to optionally fulfil the assertion returned by [AssertionBuilder].
 *
 * **Usage:** `expectThrows<Exception> { <action> }`
 *
 * **Usage:** `expectThrows<Exception> { <action> } it { <assertion> }`
 *
 * **Usage:** `expectThrows<Exception> { <action> } that { it.<assertion> }`
 */
@Suppress("GrazieInspection")
public inline fun <reified E : Throwable> expectThrows(noinline action: () -> Any?): AssertionBuilder<E> {
    val exception = shouldThrow<E>(action)
    return AssertionBuilder { additionalAssertion: Assertion<E> ->
        exception.asClue(additionalAssertion)
    }
}
