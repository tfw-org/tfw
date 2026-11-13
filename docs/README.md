# TFW Documentation

This directory contains documentation for using TFW and for developing and maintaining the framework.

## User guides

These guides explain the concepts and features provided by TFW's main packages.

### [Immutable Data](immutable.md)

Learn about the `tfw.immutable` package, including immutable arrays, matrices, input streams, composition, lazy evaluation, buffering, caching, and choosing an appropriate abstraction.

### [Transaction and State Management](tsm.md)

Learn about the `tfw.tsm` package, including components, event channels, transactions, state changes, reactive inputs, rollback, commit processing, and hierarchical application structure.

For a first look at TFW, the [HelloWorld example](../src/main/java/tfw/tsm/demo/HelloWorld.java) demonstrates a minimal TSM application.

## Maintenance guides

These guides describe the workflows used to develop, test, and maintain the TFW repository. They are intended primarily for contributors and maintainers.

See the [Maintenance Guides](maintenance/README.md) for:

- **Development** — setting up the development environment, building TFW, testing changes, and following development practices.
- **Releasing** — the steps used to prepare and publish a TFW release.
- **Fuzzing** — building and running TFW fuzzers locally.

## API reference and project resources

- [Current development Javadocs](https://tfw-org.github.io/tfw/javadoc/)
- [Latest released Javadocs](https://tfw-org.github.io/tfw/javadoc/latest/)
- [TFW on Maven Central](https://central.sonatype.com/artifact/io.github.tfw-org/tfw)
- [Contributing to TFW](../CONTRIBUTING.md)
- [Security policy](../SECURITY.md)

This documentation is an evolving resource. Guides and examples may be expanded as the project develops.
