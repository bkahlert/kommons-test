package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.KommonsTest
import com.bkahlert.kommons.test.SLF4J
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.assertingDisplayName
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.catchingDisplayName
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.displayNameFor
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.expectingDisplayName
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.property
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.throwingDisplayName
import com.bkahlert.kommons.test.junit.PathSource.Companion.currentUri
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

/** Builds tests with no subjects using a [DynamicTestsWithoutSubjectBuilder]. */
public fun testing(init: DynamicTestsWithoutSubjectBuilder.() -> Unit): List<DynamicNode> =
    DynamicTestsWithoutSubjectBuilder.build(init)

/** Builder for tests (and test containers) with no subjects. */
public class DynamicTestsWithoutSubjectBuilder(public val tests: MutableList<DynamicNode>) {

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
        tests.add(test)
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
        tests.add(test)
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
        tests.add(test)
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
        tests.add(test)
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
        tests.add(test)
        return AssertionBuilder { assertion: Assertion<E> ->
            additionalAssertion = assertion
        }
    }

    public companion object {
        public inline fun build(init: DynamicTestsWithoutSubjectBuilder.() -> Unit): List<DynamicNode> =
            mutableListOf<DynamicNode>().also { DynamicTestsWithoutSubjectBuilder(it).init() }
    }
}

/**
 * Builds tests with the specified [subject] using a [DynamicTestsWithSubjectBuilder].
 */
public fun <T> testing(subject: T, init: DynamicTestsWithSubjectBuilder<T>.() -> Unit): List<DynamicNode> =
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
): List<DynamicContainer> = subjects.asList().testingAll(containerNamePattern, init)

/**
 * Builds tests for each of subject of this [Collection] using a [DynamicTestsWithSubjectBuilder].
 *
 * The name for each container is heuristically derived but can also be explicitly specified using [containerNamePattern]
 * which supports curly placeholders `{}` like [SLF4J] does.
 */
public fun <T> Iterable<T>.testingAll(
    containerNamePattern: String? = null,
    init: DynamicTestsWithSubjectBuilder<T>.() -> Unit,
): List<DynamicContainer> = toList()
    .also { require(it.isNotEmpty()) { "At least one subject must be provided for testing." } }
    .run {
        map { subject -> dynamicContainer("for ${displayNameFor(subject, containerNamePattern)}", DynamicTestsWithSubjectBuilder.build(subject, init)) }
    }

/**
 * Builds tests for each of subject of this [Sequence] using a [DynamicTestsWithSubjectBuilder].
 *
 * The name for each container is heuristically derived but can also be explicitly specified using [containerNamePattern]
 * which supports curly placeholders `{}` like [SLF4J] does.
 */
public fun <T> Sequence<T>.testingAll(
    containerNamePattern: String? = null,
    init: DynamicTestsWithSubjectBuilder<T>.() -> Unit,
): List<DynamicContainer> = toList().testingAll(containerNamePattern, init)

/**
 * Builds tests for each of entry of this [Map] using a [DynamicTestsWithSubjectBuilder].
 *
 * The name for each container is heuristically derived but can also be explicitly specified using [containerNamePattern]
 * which supports curly placeholders `{}` like [SLF4J] does.
 */
public fun <K, V> Map<K, V>.testingAll(
    containerNamePattern: String? = null,
    init: DynamicTestsWithSubjectBuilder<Map.Entry<K, V>>.() -> Unit,
): List<DynamicContainer> = entries.testingAll(containerNamePattern, init)

/** Builder for tests (and test containers) with the specified [subject]. */
public class DynamicTestsWithSubjectBuilder<T>(public val subject: T, public val callback: (DynamicNode) -> Unit) {

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
        callback(test)
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
        callback(test)
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
        callback(test)
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
        callback(test)
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
        callback(test)
        return AssertionBuilder { assertion: Assertion<E> ->
            additionalAssertion = assertion
        }
    }

    /**
     * Builds a [DynamicContainer] using the specified [name] and the
     * specified [DynamicTestsWithSubjectBuilder] based [init] to build the child nodes.
     */
    public fun group(description: String? = null, init: DynamicTestsWithSubjectBuilder<T>.() -> Unit) {
        callback(dynamicContainer(displayNameFor(subject, description), currentUri, build(subject, init).stream()))
    }

    /**
     * Builds a new test tree testing the aspect returned by [transform].
     */
    public fun <R> with(description: String? = null, transform: T.() -> R): PropertyTestBuilder<R> {
        val aspect = subject.transform()
        val nodes = mutableListOf<DynamicNode>()
        callback(dynamicContainer(description?.takeUnless { it.isBlank() } ?: ("with".property(transform) + " " + displayNameFor(aspect)),
            currentUri,
            nodes.stream()))
        return CallbackCallingPropertyTestBuilder(aspect) { nodes.add(it) }
    }

    /**
     * Builder for testing a property of the test subject.
     */
    public interface PropertyTestBuilder<T> {
        /**
         * Builds tests for this property using a [DynamicTestsWithSubjectBuilder].
         */
        public infix fun testing(block: DynamicTestsWithSubjectBuilder<T>.() -> Unit): T
    }

    public companion object {

        private class CallbackCallingPropertyTestBuilder<T>(
            private val aspect: T,
            private val callback: (DynamicNode) -> Unit,
        ) : PropertyTestBuilder<T> {
            override fun testing(block: DynamicTestsWithSubjectBuilder<T>.() -> Unit): T {
                DynamicTestsWithSubjectBuilder(aspect, callback).block()
                return aspect
            }
        }

        /**
         * Builds an arbitrary test trees to test all necessary aspect of the specified [subject].
         */
        public fun <T> build(subject: T, init: DynamicTestsWithSubjectBuilder<T>.() -> Unit): List<DynamicNode> =
            mutableListOf<DynamicNode>().apply {
                DynamicTestsWithSubjectBuilder(subject) { add(it) }.init()
            }.toList()
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