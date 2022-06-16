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

## [0.1.0] - 2022-06-16

### Added

### tests

Write a bunch of tests conveniently in a single test:

```kotlin
@Test fun test_contain() = tests {
    "foo bar" shouldContain "Foo"
    "foo bar" shouldContain "foo"
    "foo bar" shouldContain "baz"
}
```

[unreleased]: https://github.com/bkahlert/kommons-test/compare/v0.1.0...HEAD

[0.1.0]: https://github.com/bkahlert/kommons-test/releases/tag/v0.1.0
