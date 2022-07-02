# Changelog

## [Unreleased]

### Added

*none

### Changed

*none*

### Deprecated

*none*

### Removed

*none*

### Fixed

## [0.4.0] - 2022-07-02

### Added

- matchers: shouldMatchGlob / shouldNotMatchGlob / matchGlob
- matchers: shouldMatchCurly / shouldNotMatchCurly / matchCurly

## [0.3.6] - 2022-06-30

### Changes

- display codepoint name (otherwise hex index) for displayed test names for single characters

## [0.3.5] - 2022-06-29

### Fixed

- reading `com.bkahlert.kommons.test.junit.launcher.reporter.disabled` no more throws on old JUnit versions

## [0.3.4] - 2022-06-29

### Fixed

- default to "Function" if renderType fails to reflect a lambda

## [0.3.3] - 2022-06-28

### Changed

- show parent class if available for automatically named tests

## [0.3.2] - 2022-06-27

### Added

Predefined timeout tags:

- `@OneMinuteTimeout`
- `@TwoMinutesTimeout` / `@Slow` (also adds the tag `slow`)
- `@FiveMinutesTimeout`
- `@TenMinutesTimeout`
- `@FifteenMinutesTimeout`
- `@ThirtyMinutesTimeout`

## [0.3.1] - 2022-06-26

### Added

Set default timeout for a test to 10 seconds.

## [0.3.0] - 2022-06-25

### Added

#### Fixtures

- [GifImageFixture](src/commonMain/kotlin/com/bkahlert/kommons/test/fixtures/GifImageFixture.kt)  
  A GIF image consisting of a red and white pixel.
- [SvgImageFixture](src/commonMain/kotlin/com/bkahlert/kommons/test/fixtures/SvgImageFixture.kt)  
  An SVG image with the animated Kommons logo.
- [HtmlDocumentFixture](src/commonMain/kotlin/com/bkahlert/kommons/test/fixtures/HtmlDocumentFixture.kt)  
  An HTML document that renders "Hello World!" on a red white striped background.
- [TextDocumentFixture](src/commonMain/kotlin/com/bkahlert/kommons/test/fixtures/TextDocumentFixture.kt)  
  A text document containing different line separators.  
  An UTF-8 encoded character can take between one and four bytes;  
  this document includes at least one character for each encoding length.

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

[unreleased]: https://github.com/bkahlert/kommons-test/compare/v0.4.0...HEAD

[0.4.0]: https://github.com/bkahlert/kommons-test/compare/v0.3.6...v0.4.0

[0.3.6]: https://github.com/bkahlert/kommons-test/compare/v0.3.5...v0.3.6

[0.3.5]: https://github.com/bkahlert/kommons-test/compare/v0.3.4...v0.3.5

[0.3.4]: https://github.com/bkahlert/kommons-test/compare/v0.3.3...v0.3.4

[0.3.3]: https://github.com/bkahlert/kommons-test/compare/v0.3.2...v0.3.3

[0.3.2]: https://github.com/bkahlert/kommons-test/compare/v0.3.1...v0.3.2

[0.3.1]: https://github.com/bkahlert/kommons-test/compare/v0.3.0...v0.3.1

[0.3.0]: https://github.com/bkahlert/kommons-test/compare/v0.2.0...v0.3.0

[0.2.0]: https://github.com/bkahlert/kommons-test/compare/v0.1.0...v0.2.0

[0.1.0]: https://github.com/bkahlert/kommons-test/releases/tag/v0.1.0
