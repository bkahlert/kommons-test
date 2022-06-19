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

## [0.2.0] - 2022-06-19

### Added

#### testAll

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

#### testEnum

Write a bunch of tests conveniently for all enum entries in a single test:

```kotlin
enum class FooBar { foo_bar, FOO_BAR }

@Test fun test_contain() = testEnum<FooBar> {
    it.name shouldContain "foo"
    it.name shouldContain "bar"
    it.name shouldContain "BAR"
}
```

### JUnit 5 Features

#### Parameter Resolvers

- Unique ID
- Simple ID
- DisplayName

## [0.1.0] - 2022-06-16

### Added

#### tests

Write a bunch of tests conveniently in a single test:

```kotlin
@Test fun test_contain() = tests {
    "foo bar" shouldContain "Foo"
    "foo bar" shouldContain "foo"
    "foo bar" shouldContain "baz"
}
```

[unreleased]: https://github.com/bkahlert/kommons-test/compare/v0.2.0...HEAD

[0.2.0]: https://github.com/bkahlert/kommons-test/compare/v0.1.0...v0.2.0

[0.1.0]: https://github.com/bkahlert/kommons-test/releases/tag/v0.1.0
