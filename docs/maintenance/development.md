# TFW Development Guide

This guide describes the standard workflow for developing and validating changes to TFW. It is intended for contributors working on the TFW source repository.

For release and publishing procedures, see [Releasing](releasing.md). For fuzzing-specific workflows, see [Fuzzing](fuzzing.md).

## Development environment

### Java

TFW targets **Java 8** for its compiled library code. However, the development build uses newer JDKs. The project's continuous integration currently tests with Java 21 and Java 25.

Use a supported, current JDK for development, and verify changes against the project's CI configuration when compatibility matters. The Java version used to build the project is distinct from the Java version targeted by the compiled library.

### Maven

Use the Maven Wrapper included in the repository rather than relying on a separately installed Maven version. The wrapper selects the Maven distribution configured by the project and helps keep local builds consistent with CI.

On Linux and macOS, run:

```bash
./mvnw -version
```

On Windows, use:

```bat
mvnw.cmd -version
```

The wrapper configuration is maintained in `.mvn/wrapper/maven-wrapper.properties`. The current POM also enforces a minimum Maven version, so do not bypass the wrapper or change its configuration casually.

### Clone and build the repository

Clone the repository and enter the working directory:

```bash
git clone https://github.com/tfw-org/tfw.git
cd tfw
```

Before making changes, check the working tree and run the project build. This establishes a baseline and helps distinguish pre-existing problems from problems introduced by a change.

The standard Maven validation command is:

```bash
./mvnw -B verify
```

The `verify` lifecycle runs the build's configured validation steps, including compilation, tests, and quality checks bound to the Maven lifecycle.

On Windows, substitute `mvnw.cmd` for `./mvnw`.

## Making changes

### Work on a branch

Make changes on a dedicated branch rather than committing directly to `master`. Keep changes focused so that they can be reviewed and tested independently.

Before committing, inspect the changes:

```bash
git status
git diff
```

Review both the intended source changes and any generated or modified files. Do not include unrelated changes in the commit.

### Follow the existing design

TFW contains two principal packages:

- **`tfw.immutable`** provides abstractions and implementations for immutable data access.
- **`tfw.tsm`** provides transactional state management and component composition.

When changing either package, follow the existing interfaces, implementation patterns, tests, and documentation. Avoid introducing a new abstraction or dependency when the existing design already provides the required functionality.

The `tfw.immutable` interfaces separate data access from the implementation that supplies the data. Their implementations may use lazy evaluation, composition, or internal buffering where appropriate. Changes to these implementations should preserve their documented contracts and should be evaluated against their intended access patterns.

For `tfw.tsm`, pay particular attention to component contracts, state dependencies, transaction processing, and the behavior of changes across a complete transaction.

These are design considerations, not substitutes for checking the relevant source code and tests.

### Formatting and source consistency

The Maven build includes the Spotless Maven plugin, configured with Palantir Java Format for Java source files and formatting rules for `pom.xml`. Formatting checks are part of the build.

If a formatting check fails, inspect the reported differences and apply the project's configured formatting where appropriate. The following command can apply Spotless formatting:

```bash
./mvnw spotless:apply
```

Review the resulting diff before committing. Formatting should not obscure unrelated changes or introduce accidental modifications to files outside the intended scope.

The build also performs configured static analysis and other quality checks. Resolve problems introduced by your change, and investigate unexpected failures rather than assuming that a successful compilation alone is sufficient.

## Testing and validation

### Run the full verification build

The primary validation command is:

```bash
./mvnw -B verify
```

The project's CI workflow uses this command through `execute-on-vnc.sh`:

```bash
./execute-on-vnc.sh ./mvnw -B verify
```

The wrapper script starts a virtual VNC display before running the command. This accommodates GUI-related tests in the test suite. The CI environment installs `tightvncserver` before executing the build.

If the full test suite fails locally, determine whether the failure is caused by the change, the local environment, or a pre-existing issue. For GUI-related failures, check the display and VNC requirements before concluding that the code itself is defective.

### Test the relevant behavior

In addition to running the full build, make sure that the tests exercise the behavior changed by your work.

Depending on the change, this may include:

- Normal behavior and boundary conditions.
- Invalid inputs and exceptional conditions.
- Resource ownership and cleanup.
- Interaction with other components or implementations.
- Regression tests for previously reported problems.

Tests should verify the intended contract rather than merely reproduce the implementation's current behavior.

### Coverage and mutation testing

TFW uses JaCoCo for code coverage and PIT for mutation testing. These tools provide complementary information: coverage identifies code exercised by tests, while mutation testing evaluates whether tests detect selected changes to program behavior.

The project configures Maven profiles for these tasks. To run the coverage-enabled verification build:

```bash
./mvnw -Pcoverage verify
```

To run the configured mutation-testing profile:

```bash
./mvnw -Ppitest test
```

These analyses can take longer than a normal build. Use them when appropriate to the change, and consult the repository's current configuration and CI results for the precise reports and expectations.

## Continuous integration

The main Maven CI workflow is defined in [`.github/workflows/maven.yml`](../../.github/workflows/maven.yml).

It runs on pushes and pull requests targeting `master`. Its build matrix covers:

- Java 21 and Java 25.
- Zulu, Temurin, and Corretto JDK distributions.

The workflow runs the Maven verification build through `execute-on-vnc.sh`. It also checks whether the build has modified tracked files, helping detect unintended changes produced during verification.

Other workflows provide additional checks, including CodeQL analysis. The status of the required and applicable checks on a pull request should be reviewed before merging.

A successful local build is useful evidence, but it does not replace checking the actual CI results.

## Before submitting a change

Use this checklist before opening or updating a pull request:

- [ ] The change is limited to its intended purpose.
- [ ] Relevant tests have been added or updated.
- [ ] The Maven verification build passes.
- [ ] Formatting and configured quality checks pass.
- [ ] Generated or modified files have been reviewed.
- [ ] Documentation has been updated where necessary.
- [ ] The final diff contains no accidental or unrelated changes.
- [ ] Applicable GitHub Actions checks have completed successfully.

Explain the purpose of the change and any important design decisions in the pull request description. If a check cannot be completed, state the reason clearly rather than implying that it passed.

## Keeping development documentation current

Build commands, Java versions, Maven settings, and CI workflows can change. When modifying these configurations, update this guide if its instructions are affected.

For authoritative details, consult the current `pom.xml`, Maven Wrapper configuration, and workflow files in the repository.
