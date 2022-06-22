![Kommons Debug — Logo](docs/kommons-header.svg)

# Kommons [![Download from Maven Central](https://img.shields.io/maven-central/v/com.bkahlert.kommons/kommons-test?color=FFD726&label=Maven%20Central&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDI1LjEuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkxheWVyXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4IgoJIHZpZXdCb3g9IjAgMCA1MTIgNTEyIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA1MTIgNTEyOyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI%2BCjxnPgoJPGRlZnM%2BCgkJPHBhdGggaWQ9IlNWR0lEXzFfIiBkPSJNMTAxLjcsMzQ1LjJWMTY3TDI1Niw3Ny45TDQxMC40LDE2N3YxNzguMkwyNTYsNDM0LjNMMTAxLjcsMzQ1LjJ6IE0yNTYsNkwzOS42LDEzMS4ydjI0OS45TDI1Niw1MDYKCQkJbDIxNi40LTEyNC45VjEzMS4yTDI1Niw2eiIvPgoJPC9kZWZzPgoJPHVzZSB4bGluazpocmVmPSIjU1ZHSURfMV8iICBzdHlsZT0ib3ZlcmZsb3c6dmlzaWJsZTtmaWxsOiNGRkZGRkY7Ii8%2BCgk8Y2xpcFBhdGggaWQ9IlNWR0lEXzJfIj4KCQk8dXNlIHhsaW5rOmhyZWY9IiNTVkdJRF8xXyIgIHN0eWxlPSJvdmVyZmxvdzp2aXNpYmxlOyIvPgoJPC9jbGlwUGF0aD4KPC9nPgo8L3N2Zz4K)](https://search.maven.org/search?q=g:com.bkahlert.kommons%20AND%20a:kommons-test) [![Download from GitHub Packages](https://img.shields.io/github/v/release/bkahlert/kommons-test?color=69B745&label=GitHub&logo=GitHub&logoColor=fff&style=round)](https://github.com/bkahlert/kommons-test/releases/latest) <!--[![Download from Bintray JCenter](https://img.shields.io/bintray/v/bkahlert/koodies/koodies?color=69B745&label=Bintray%20JCenter&logo=JFrog-Bintray&logoColor=fff&style=round)](https://bintray.com/bkahlert/koodies/koodies/_latestVersion)--> [![Build Status](https://img.shields.io/github/workflow/status/bkahlert/kommons-test/build?label=Build&logo=github&logoColor=fff)](https://github.com/bkahlert/kommons-test/actions/workflows/build.yml) [![Repository Size](https://img.shields.io/github/repo-size/bkahlert/kommons-test?color=01818F&label=Repo%20Size&logo=Git&logoColor=fff)](https://github.com/bkahlert/kommons-test) [![Repository Size](https://img.shields.io/github/license/bkahlert/kommons-test?color=29ABE2&label=License&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA1OTAgNTkwIiAgeG1sbnM6dj0iaHR0cHM6Ly92ZWN0YS5pby9uYW5vIj48cGF0aCBkPSJNMzI4LjcgMzk1LjhjNDAuMy0xNSA2MS40LTQzLjggNjEuNC05My40UzM0OC4zIDIwOSAyOTYgMjA4LjljLTU1LjEtLjEtOTYuOCA0My42LTk2LjEgOTMuNXMyNC40IDgzIDYyLjQgOTQuOUwxOTUgNTYzQzEwNC44IDUzOS43IDEzLjIgNDMzLjMgMTMuMiAzMDIuNCAxMy4yIDE0Ny4zIDEzNy44IDIxLjUgMjk0IDIxLjVzMjgyLjggMTI1LjcgMjgyLjggMjgwLjhjMCAxMzMtOTAuOCAyMzcuOS0xODIuOSAyNjEuMWwtNjUuMi0xNjcuNnoiIGZpbGw9IiNmZmYiIHN0cm9rZT0iI2ZmZiIgc3Ryb2tlLXdpZHRoPSIxOS4yMTIiIHN0cm9rZS1saW5lam9pbj0icm91bmQiLz48L3N2Zz4%3D)](https://github.com/bkahlert/kommons-test/blob/master/LICENSE)

<!-- C21E73 -->

## About

**Kommons Test** is a Kotlin Multiplatform Library to ease testing.

This library is based on [Kotest Assertions](https://kotest.io/docs/assertions/assertions.html).

It provides you with a small number of helper functions to help
you write most tests as common Kotlin tests.

Why? Because every common test you write saves you from having to write
it for each supported platform separately.


## Installation / Setup

Kommons Debug is hosted on GitHub with releases provided on Maven Central.

* **Gradle** `testImplementation("com.bkahlert.kommons:kommons-test:0.2.0")`
* **Gradle** `implementation("com.bkahlert.kommons:kommons-test:0.2.0")` *(for MPP projects)*

* **Maven**
  ```xml
  <dependency>
      <groupId>com.bkahlert.kommons</groupId>
      <artifactId>kommons-test</artifactId>
      <version>0.2.0</version>
      <scope>test</scope>
  </dependency>
  ```

## Features

### tests

Write a bunch of tests conveniently in a single test:

```kotlin
@Test fun test_contain() = tests {
    "foo bar" shouldContain "Foo"
    "foo bar" shouldContain "foo"
    "foo bar" shouldContain "baz"
}
```

The above tests has three assertions of which the first and last fail
when run with the following output:

```
The following 2 assertions failed:
1) "foo bar" should include substring "Foo"
    at sample.Tests.test_contain(Tests.kt:1)
2) "foo bar" should include substring "baz"
    at sample.Tests.test_contain(Tests.kt:3)
```

### testAll

Write a bunch of tests conveniently for multiple subjects in a single test:

```kotlin
@Test fun test_contain() = testAll("foo bar", "FOO BAR") {
    it shouldContain "foo"
    it shouldContain "bar"
    it shouldContain "BAR"
}

// The following invocations are equivalent: 
testAll("foo bar", "FOO BAR") { /* ... */ }
listOf("foo bar", "FOO BAR").testAll { /* ... */ }
sequenceOf("foo bar", "FOO BAR").testAll { /* ... */ }
mapOf("key1" to "foo bar", "key2" to "FOO BAR").testAll { (_, v) -> /* ... */ }
```

The above tests has three assertions of which the first and second fail
when run with the following output:

```
0 elements passed but expected 2

The following elements passed:
--none--

The following elements failed:
"foo bar" => "foo bar" should include substring "BAR"
"FOO BAR" => 
The following 2 assertions failed:
1) "FOO BAR" should include substring "foo"
    at sample.Tests.test_contain(Tests.kt:1)
2) "FOO BAR" should include substring "bar"
    at sample.Tests.test_contain(Tests.kt:2)
```

### testEnum

Write a bunch of tests conveniently for all enum entries in a single test:

```kotlin
enum class FooBar { foo_bar, FOO_BAR }

@Test fun test_contain() = testEnum<FooBar> {
    it.name shouldContain "foo"
    it.name shouldContain "bar"
    it.name shouldContain "BAR"
}
```

The above tests has three assertions of which the first and second fail
when run with the following output:

```
0 elements passed but expected 2

The following elements passed:
--none--

The following elements failed:
foo_bar => "foo_bar" should include substring "BAR"
FOO_BAR => 
The following 2 assertions failed:
1) "FOO_BAR" should include substring "foo"
    at sample.Tests.test_contain(Tests.kt:3)
2) "FOO_BAR" should include substring "bar"
    at sample.Tests.test_contain(Tests.kt:4)
```

## JVM Features

### Test Builders

Write concise tests with one of the following test builders:

- `testing`
- `testingEach`

```kotlin
class SubjectsTest {

    // create three test containers - one for each subject 
    @TestFactory fun subjects() = testEach(
        "subject 1",
        "subject 2",
        "subject 3",
    ) {
        // all assertions will be run as separate JUnit tests 
        expecting { length } it { shouldBeGreaterThan(0) }
        // if you prefer to do assertions using `it` use the `that` infix 
        expecting { length } that { it.shouldBeGreaterThan(0) }

        // assert potential exceptions 
        expectCatching { "nope" } it { shouldBeSuccess() }
        expectCatching { throw RuntimeException() } it { shouldBeFailure() }

        // assert definitive exceptions 
        expectThrows<RuntimeException> { throw RuntimeException() }

        // the display name for each expectation is computed automatically
        // the following expectation will render as `"$this-foo" shouldStartWith("subject ")`
        expecting { "$this-foo" } it { shouldStartWith("subject ") }

        // this expectation will render as `error(this) shouldBeFailure().shouldBeInstanceOf<IllegalStateException>()`
        expectCatching { error(this) } that { it.shouldBeFailure().shouldBeInstanceOf<IllegalStateException>() }
    }
}
```

A proper [JUnit test source URI](https://junit.org/junit5/docs/current/user-guide/#writing-tests-dynamic-tests-uri-test-source)
similar to `file:///home/dev/my-project/src/jvmTest/kotlin/pkg/test/SubjectsTest.kt?line=16&column=9` is created
automatically for each expectation.

Depending on your IDE a simple double-click on a failed test should bring you exactly to where you typed your expectation.

### Source File Location

Find the class directory, the source directory or the source file itself of a class.

```kotlin
Foo::class.findClassesDirectoryOrNull()  // /home/john/dev/project/build/classes/kotlin/jvm/test
Foo::class.findSourceDirectoryOrNull()   // /home/john/dev/project/src/jvmTest/kotlin
Foo::class.findSourceFileOrNull()        // /home/john/dev/project/src/jvmTest/kotlin/packages/source.kt
```

### Source Code Analysis

Ever wondered what the code that triggered an exception looks like?

Doing so was never easier with `getLambdaBodyOrNull`:

```kotlin
val caught = catchException {
    foo {
        bar {
            val now = Instant.now()
            throw RuntimeException("failed at $now")
        }
    }
}

val bodyOfBarCall = caught.getLambdaBodyOrNull()?.body
// """
// val now = Instant.now()
// throw RuntimeException("failed at ${'$'}now")
// """


val bodyOfFooCall = caught.getLambdaBodyOrNull("foo")?.body
// """
// bar {
//     val now = Instant.now()
//     throw RuntimeException("failed at $now")
// }
// """


// helper
private fun <R> foo(block: () -> R): R = block()
private fun <R> bar(block: () -> R): R = block()

private inline fun catchException(block: () -> Nothing): Throwable =
    try {
        block()
    } catch (e: Throwable) {
        e
    }

```

## JUnit 5 Features

### Opinionated Defaults

JUnit based tests will be started with the following settings by default:

```properties
# concise test names with no parameter list
junit.jupiter.displayname.generator.default=\
  com.bkahlert.kommons.test.junit.MethodNameOnlyDisplayNameGenerator
# run top-level test containers in parallel
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.classes.default=concurrent
junit.jupiter.execution.parallel.config.strategy=dynamic
junit.jupiter.execution.parallel.config.dynamic.factor=5
# run tests inside a test tree sequentially
junit.jupiter.execution.parallel.mode.default=same_thread
# auto-detect extensions located in META-INF/services
junit.jupiter.extensions.autodetection.enabled=true
# instantiate test classes only once for all tests
# same as annotating all test classes with @TestInstance(PER_CLASS)
junit.jupiter.testinstance.lifecycle.default=per_class
# enable constructor dependency injection for Spring tests
spring.test.constructor.autowire.mode=all
```

### Reporting

Tests results are printed at the end of a test run
by [TestExecutionReporter](src/jvmMain/kotlin/com/bkahlert/kommons/test/junit/launcher/TestExecutionReporter.kt) as follows:

```log
120 tests within 1.8s: ✘︎ 2 failed, ‼ 3 crashed, ✔︎ 113 passed, 2 ignored
```

Or if all went well:

```log
120 tests within 1.55s: ✔︎ all passed
```

This feature is enabled by default but can be disabled by setting:

```properties
com.bkahlert.kommons.test.junit.launcher.reporter.disabled=true
```

### Parameter Resolvers

#### Unique ID

```kotlin
class UniqueIdResolverTest {
    @Nested inner class NestedTest {
        @Test fun test_name(uniqueId: UniqueId) = tests {
            uniqueId.segments.first() // "[engine:junit-jupiter]"
            uniqueId.segments.last()  // "[method:test_name(org.junit.platform.engine.UniqueId)]"
        }
    }
}
```

#### Simple ID

```kotlin
class SimpleIdResolverTest {
    @Nested inner class NestedTest {
        @Test fun test_name(simpleId: SimpleId) = tests {
            simpleId.segments.first() // "SimpleIdResolverTest"
            simpleId.segments.last()  // "test_name"
            simpleId.toString()       // "SimpleIdResolverTest.test_name"
        }
    }
}
```

#### DisplayName

```kotlin
class DisplayNameResolverTest {
    @Nested inner class NestedTest {
        @Test fun `test name`(displayName: DisplayName) = tests {
            displayName.displayName         // "test_name"
            displayName.composedDisplayName // "DisplayNameResolverTest ➜ NestedTest ➜ test_name"
        }
    }
}
```

#### ExtensionContext

```kotlin
class ExtensionContextResolverTest {
    @Nested inner class NestedTest {
        @Test fun `test name`(extensionContext: ExtensionContext) = tests {
            extensionContext.simpleId   // "ExtensionContextResolverTest.NestedTest.test_name"
        }
    }
}
```

### System Property Extension

This extension allows you to set the system properties
for the duration of a text execution.

Tests that use this annotation are guaranteed to not run concurrently.

```kotlin
class SystemPropertiesTest {
    @Test
    @SystemProperty(name = "foo", value = "bar")
    fun test() {
        System.getProperty("foo") // "bar"
    }
}
```

### Extension Authoring

For authors of JUnit extensions `getStore` and `getTestStore` can
be used to obtain differently namespaced stores.

Reified variants of `getTyped`, `getTypedOrDefault`, `getTypedOrComputeIfAbsent`, and `removeTyped`
can be used in place of their type-safe counterparts that require
a class instance argument.

```kotlin
class MyExtension : BeforeAllCallback, BeforeEachCallback {

    override fun beforeAll(context: ExtensionContext) {
        // store the moment the tests were started in the container store
        context.getStore<MyExtension>().put("start", Instant.now())

        // will throw an exception because there is no current test
        context.getTestStore<MyExtension>().put("foo", "bar")
    }

    override fun beforeEach(context: ExtensionContext) {
        // returns the moment the tests were started
        context.getStore<MyExtension>().getTyped<Instant>("start")

        // returns null, because the store is namespaced with the test itself
        context.getTestStore<MyExtension>().getTyped<Instant>("start")
    }
}
```

### Launching Tests

Launch JUnit tests programmatically using `launchTests`.

Use [KotlinDiscoverySelectors](src/jvmMain/kotlin/com/bkahlert/kommons/test/junit/launcher/KotlinDiscoverySelectors.kt) to easily select the tests to run
explicitly using
`selectKotlinClass`, `selectKotlinMemberFunction`,
`selectKotlinNestedClass`, `selectKotlinNestedMemberFunction`.

Alternatively use `select` to no longer have to write the full paths to your tests
yourself.

```kotlin
class FooTest {
    @Test
    fun test_foo() {
        "foo" shouldBe "foo"
    }
}

class BarTest {
    @Test
    fun test_bar() {
        "bar" shouldBe "baz"
    }

    @Nested
    inner class BazTest {
        @Test
        fun test_baz() {
            "baz" shouldBe "baz"
        }
    }
}

fun main() {
    // launches all FooTest tests and BazTest.test_bat()
    launchTests(
        select(FooTest::class),
        select(BazTest::test_baz),
    )

    // same as above but with classic discovery function
    launchTests(
        selectClass(FooTest::class.java),
        selectNestedMethod(listOf(BarTest::class.java), BazTest::class.java, "test_baz"),
    )

    // customizes how tests are discovered and run
    launchTests(
        select(FooTest::class),
        select(BazTest::test_baz),
    ) {
        request {
            // customize discovery request
            parallelExecutionEnabled(true)
        }
        config {
            // customize launcher configuration
        }
        launcher {
            // customize launcher
        }
    }
}
```

## Contributing

Want to contribute? Awesome! The most basic way to show your support is to star the project, or to raise issues. You can also support this project by making
a [PayPal donation](https://www.paypal.me/bkahlert) to ensure this journey continues indefinitely!

Thanks again for your support, it is much appreciated! :pray:

## License

MIT. See [LICENSE](LICENSE) for more details.
