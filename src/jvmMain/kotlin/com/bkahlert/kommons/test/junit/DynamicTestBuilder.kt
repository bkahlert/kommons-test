package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.KommonsTest
import com.bkahlert.kommons.test.SLF4J
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.assertingDisplayName
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.catchingDisplayName
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.displayNameFor
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.expectingDisplayName
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.throwingDisplayName
import com.bkahlert.kommons.test.junit.PathSource.Companion.sourceUri
import com.bkahlert.kommons.test.junit.SimpleIdResolver.Companion.simpleId
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.net.URI
import java.util.stream.Stream

/** Builds tests with no subjects using a [DynamicTestsWithoutSubjectBuilder]. */
public fun testing(init: DynamicTestsWithoutSubjectBuilder.() -> Unit): Stream<DynamicNode> =
    DynamicTestsWithoutSubjectBuilder.build(init)

/** Builder for tests (and test containers) with no subjects. */
public class DynamicTestsWithoutSubjectBuilder(
    public val addDynamicNode: (DynamicNode) -> Unit,
) {

    /**
     * Expects this subject to fulfil the given [assertion].
     *
     * **Usage:** `<subject> asserting { <assertion> }`
     */
    @JvmName("infixAsserting")
    public infix fun <T> T.asserting(assertion: Assertion<T>) {
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(caller.assertingDisplayName(this, assertion), caller.sourceUri) {
            asClue(assertion)
        }
        addDynamicNode(test)
    }

    /**
     * Expects the [subject] to fulfil the given [assertion].
     *
     * **Usage:** `asserting(<subject>) { <assertion> }`
     */
    public fun <T> asserting(subject: T, assertion: Assertion<T>) {
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(caller.assertingDisplayName(subject, assertion), caller.sourceUri) {
            subject.asClue(assertion)
        }
        addDynamicNode(test)
    }

    /**
     * Expects the subject returned by [action] to fulfil the
     * assertion returned by [AssertionBuilder].
     *
     * **Usage:** `expecting { <action> } it { <assertion> }`
     *
     * **Usage:** `expecting { <action> } that { it.<assertion> }`
     */
    public fun <R> expecting(description: String? = null, action: () -> R): AssertionBuilder<R> {
        var additionalAssertion: Assertion<R>? = null
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(description ?: caller.expectingDisplayName(action), caller.sourceUri) {
            additionalAssertion?.also {
                val subject = action()
                subject.asClue(it)
            } ?: throw IllegalUsageException("expecting", caller.sourceUri)
        }
        addDynamicNode(test)
        return AssertionBuilder { assertion: Assertion<R> ->
            additionalAssertion = assertion
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
    public fun <R> expectCatching(action: () -> R): AssertionBuilder<Result<R>> {
        var additionalAssertion: Assertion<Result<R>>? = null
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(caller.catchingDisplayName(action), caller.sourceUri) {
            additionalAssertion?.also {
                val subject = runCatching(action)
                subject.asClue(it)
            } ?: throw IllegalUsageException("expectCatching", caller.sourceUri)
        }
        addDynamicNode(test)
        return AssertionBuilder { assertion: Assertion<Result<R>> ->
            additionalAssertion = assertion
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
    public inline fun <reified E : Throwable> expectThrows(noinline action: () -> Any?): AssertionBuilder<E> {
        var additionalAssertion: Assertion<E>? = null
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(throwingDisplayName(E::class), caller.sourceUri) {
            shouldThrow<E>(action).asClue(additionalAssertion ?: {})
        }
        addDynamicNode(test)
        return AssertionBuilder { assertion: Assertion<E> ->
            additionalAssertion = assertion
        }
    }

    public companion object {
        public inline fun build(
            init: DynamicTestsWithoutSubjectBuilder.() -> Unit,
        ): Stream<DynamicNode> = buildList {
            DynamicTestsWithoutSubjectBuilder { add(it) }.init()
        }.stream()
    }
}

/**
 * Builds tests with the specified [subject] using a [DynamicTestsWithSubjectBuilder].
 */
public fun <T> testing(subject: T, init: DynamicTestsWithSubjectBuilder<T>.() -> Unit): Stream<DynamicNode> =
    DynamicTestsWithSubjectBuilder.build(subject, init)

/**
 * Builds tests for each of the specified [subjects] using a [DynamicTestsWithSubjectBuilder].
 *
 * The name for each container is heuristically derived but can also be explicitly specified using [containerNamePattern]
 * which supports curly placeholders `{}` like [SLF4J] does.
 */
public fun <T> testingAll(
    vararg subjects: T,
    containerNamePattern: String? = null,
    init: DynamicTestsWithSubjectBuilder<T>.() -> Unit,
): Stream<DynamicContainer> = subjects.asList().testingAll(containerNamePattern, init)

/**
 * Builds tests for each of subject of this [Collection] using a [DynamicTestsWithSubjectBuilder].
 *
 * The name for each container is heuristically derived but can also be explicitly specified using [containerNamePattern]
 * which supports curly placeholders `{}` like [SLF4J] does.
 */
public fun <T> Iterable<T>.testingAll(
    containerNamePattern: String? = null,
    init: DynamicTestsWithSubjectBuilder<T>.() -> Unit,
): Stream<DynamicContainer> = toList()
    .also { require(it.isNotEmpty()) { "At least one subject must be provided for testing." } }
    .map { subject ->
        dynamicContainer(
            "for ${displayNameFor(subject, containerNamePattern)}",
            PathSource.currentUri,
            testing(subject, init)
        )
    }.stream()

/**
 * Builds tests for each of subject of this [Sequence] using a [DynamicTestsWithSubjectBuilder].
 *
 * The name for each container is heuristically derived but can also be explicitly specified using [containerNamePattern]
 * which supports curly placeholders `{}` like [SLF4J] does.
 */
public fun <T> Sequence<T>.testingAll(
    containerNamePattern: String? = null,
    init: DynamicTestsWithSubjectBuilder<T>.() -> Unit,
): Stream<DynamicContainer> = toList().testingAll(containerNamePattern, init)

/**
 * Builds tests for each of entry of this [Map] using a [DynamicTestsWithSubjectBuilder].
 *
 * The name for each container is heuristically derived but can also be explicitly specified using [containerNamePattern]
 * which supports curly placeholders `{}` like [SLF4J] does.
 */
public fun <K, V> Map<K, V>.testingAll(
    containerNamePattern: String? = null,
    init: DynamicTestsWithSubjectBuilder<Map.Entry<K, V>>.() -> Unit,
): Stream<DynamicContainer> = entries.testingAll(containerNamePattern, init)

/** Builder for tests (and test containers) with the specified [subject]. */
public class DynamicTestsWithSubjectBuilder<T>(
    public val subject: T,
    public val addDynamicNode: (DynamicNode) -> Unit,
) {

    /**
     * Expects this subject to fulfil the given [assertion].
     *
     * ***Note:** The surrounding test subject is ignored.*
     *
     * **Usage:** `<subject> asserting { <assertion> }`
     */
    public infix fun <T> T.asserting(assertion: Assertion<T>) {
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(caller.assertingDisplayName(this, assertion), caller.sourceUri) {
            asClue(assertion)
        }
        addDynamicNode(test)
    }

    /**
     * Expects the [subject] to fulfil the given [assertion].
     *
     * **Usage:** `asserting(<subject>) { <assertion> }`
     */
    public infix fun asserting(assertion: Assertion<T>) {
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(caller.assertingDisplayName(subject, assertion), caller.sourceUri) {
            subject.asClue(assertion)
        }
        addDynamicNode(test)
    }

    /**
     * Expects the [subject] transformed by [action] to fulfil the
     * assertion returned by [AssertionBuilder].
     *
     * **Usage:** `expecting { 𝘴𝘶𝘣𝘫𝘦𝘤𝘵.<action> } it { <assertion> }`
     *
     * **Usage:** `expecting { 𝘴𝘶𝘣𝘫𝘦𝘤𝘵.<action> } that { it.<assertion> }`
     */
    public fun <R> expecting(description: String? = null, action: T.() -> R): AssertionBuilder<R> {
        var additionalAssertion: Assertion<R>? = null
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(description ?: caller.expectingDisplayName(action), caller.sourceUri) {
            additionalAssertion?.also {
                withClue(subject) {
                    val aspect = subject.action()
                    aspect.asClue(it)
                }
            } ?: throw IllegalUsageException("expecting", caller.sourceUri)
        }
        addDynamicNode(test)
        return AssertionBuilder { assertion: Assertion<R> ->
            additionalAssertion = assertion
        }
    }

    /**
     * Expects the [Result] of the [subject] transformed by [action] to fulfil the
     * assertion returned by [AssertionBuilder].
     *
     * **Usage:** `expectCatching { 𝘴𝘶𝘣𝘫𝘦𝘤𝘵.<action> } it { <assertion> }`
     *
     * **Usage:** `expectCatching { 𝘴𝘶𝘣𝘫𝘦𝘤𝘵.<action> } that { it.<assertion> }`
     */
    public fun <R> expectCatching(action: T.() -> R): AssertionBuilder<Result<R>> {
        var additionalAssertion: Assertion<Result<R>>? = null
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(caller.catchingDisplayName(action), caller.sourceUri) {
            additionalAssertion?.also {
                withClue(subject) {
                    val aspect = subject.runCatching(action)
                    aspect.asClue(it)
                }
            } ?: throw IllegalUsageException("expectCatching", caller.sourceUri)
        }
        addDynamicNode(test)
        return AssertionBuilder { assertion: Assertion<Result<R>> ->
            additionalAssertion = assertion
        }
    }

    /**
     * Expects an exception [E] to be thrown when transforming the [subject] with [action]
     * and to optionally fulfil the assertion returned by [AssertionBuilder].
     *
     * **Usage:** `expectThrows<Exception> { 𝘴𝘶𝘣𝘫𝘦𝘤𝘵.<action> }`
     *
     * **Usage:** `expectThrows<Exception> { 𝘴𝘶𝘣𝘫𝘦𝘤𝘵.<action> } it { <assertion> }`
     *
     * **Usage:** `expectThrows<Exception> { 𝘴𝘶𝘣𝘫𝘦𝘤𝘵.<action> } that { it.<assertion> }`
     */
    public inline fun <reified E : Throwable> expectThrows(noinline action: T.() -> Any?): AssertionBuilder<E> {
        var additionalAssertion: Assertion<E>? = null
        val caller = KommonsTest.locateCall()
        val test = dynamicTest(throwingDisplayName(E::class), caller.sourceUri) {
            withClue(subject) {
                shouldThrow<E> { subject.action() }.asClue(additionalAssertion ?: {})
            }
        }
        addDynamicNode(test)
        return AssertionBuilder { assertion: Assertion<E> ->
            additionalAssertion = assertion
        }
    }

    public companion object {

        public fun <T> build(
            subject: T,
            init: DynamicTestsWithSubjectBuilder<T>.() -> Unit,
        ): Stream<DynamicNode> = buildList {
            DynamicTestsWithSubjectBuilder(subject) { add(it) }.init()
        }.stream()
    }
}


/** Exception thrown if the API is incorrectly used. */
public class IllegalUsageException(function: String, caller: URI?) : IllegalArgumentException(
    "$function { … } call was not finished with \"that { … }\"".let {
        caller?.let { uri -> "$it at " + uri.path + ":" + uri.query.takeLastWhile { it.isDigit() } } ?: it
    }
)


/**
 * Extension that checks if [expecting] or [expectCatching]
 * where incorrectly used.
 *
 * ***Important:**
 * For this extension to work, it needs to be registered.*
 *
 * > The most convenient way to register this extension
 * > for all tests is by adding the line **`com.bkahlert.kommons.test.junit.IllegalUsageCheck`** to the
 * > file **`resources/META-INF/services/org.junit.jupiter.api.extension.Extension`**.
 */
internal class IllegalUsageCheck : AfterEachCallback {

    override fun afterEach(context: ExtensionContext) {
        val id: SimpleId = context.simpleId
        val illegalUsage = illegalUsages[id]
        if (illegalUsage != null) {
            if (!context.illegalUsageExpected) throw illegalUsage
        } else if (context.illegalUsageExpected) {
            error("${IllegalUsageException::class} expected but not thrown.")
        }
    }

    /**
     * Extension to signal that an [IllegalUsageException] is expected.
     * Consequently, the tests fails if no such exception is thrown.
     */
    class ExpectIllegalUsageException : AfterEachCallback {
        override fun afterEach(context: ExtensionContext) {
            context.illegalUsageExpected = true
        }
    }

    companion object {

        private const val ILLEGAL_USAGE_KEY: String = "ILLEGAL_USAGE"

        var ExtensionContext.illegalUsageExpected: Boolean
            get() = getTestStore<IllegalUsageCheck>().get(ILLEGAL_USAGE_KEY, Boolean::class.java) == true
            set(value) = getTestStore<IllegalUsageCheck>().put(ILLEGAL_USAGE_KEY, value)

        val illegalUsages: MutableMap<SimpleId, IllegalUsageException> = mutableMapOf()
    }
}
