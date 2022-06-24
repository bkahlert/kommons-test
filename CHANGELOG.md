# Changelog

## [Unreleased]

### Added

*none*

### Changed

*none*

### Deprecated

*none*

### Removed

*none*

### Fixed

## [0.2.0] - 2022-06-24

### Added

#### testAll

Write a bunch of soft assertions conveniently for multiple subjects in a single test:

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

#### testEnum

Write a bunch of soft assertions conveniently for all enum entries in a single test:

```kotlin
enum class FooBar { foo_bar, FOO_BAR }

@Test fun test_contain() = testEnum<FooBar> {
    it.name shouldContain "foo"
    it.name shouldContain "bar"
    it.name shouldContain "BAR"
}
```

### JVM Features

#### Source File Location

Find the class directory, the source directory or the source file itself of a class.

```kotlin
Foo::class.findClassesDirectoryOrNull()  // /home/john/dev/project/build/classes/kotlin/jvm/test
Foo::class.findSourceDirectoryOrNull()   // /home/john/dev/project/src/jvmTest/kotlin
Foo::class.findSourceFileOrNull()        // /home/john/dev/project/src/jvmTest/kotlin/packages/source.kt
```

#### Source Code Analysis

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
```

### JUnit 5 Features

#### Parameter Resolvers

- Unique ID
- Simple ID
- DisplayName
- ExtensionContext

#### System Property Extension

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

#### Extension Authoring

For authors of JUnit extensions `getStore` and `getTestStore` can
be used to obtain differently namespaced stores.

Reified variants of `getTyped`, `getTypedOrDefault`, `getTypedOrComputeIfAbsent`, and `removeTyped`
can be used in place of their type-safe counterparts that require
a class instance argument.

#### Launching Tests

Launch JUnit tests programmatically using `launchTests`.

Use `KotlinDiscoverySelectors` to easily select the tests to run explicitly using
`selectKotlinClass`, `selectKotlinMemberFunction`,
`selectKotlinNestedClass`, `selectKotlinNestedMemberFunction`.

Alternatively use `select` to no longer have to write the full paths to your tests
yourself.

## [0.1.0] - 2022-06-16

### Added

#### test

Write a bunch of soft assertions conveniently in a single test:

```kotlin
@Test fun test_contain() = test {
    "foo bar" shouldContain "Foo"
    "foo bar" shouldContain "foo"
    "foo bar" shouldContain "baz"
}
```

[unreleased]: https://github.com/bkahlert/kommons-test/compare/v0.2.0...HEAD

[0.2.0]: https://github.com/bkahlert/kommons-test/compare/v0.1.0...v0.2.0

[0.1.0]: https://github.com/bkahlert/kommons-test/releases/tag/v0.1.0
