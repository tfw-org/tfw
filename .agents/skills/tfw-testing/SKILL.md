---
name: tfw-testing
description: Write, review, and improve tests for the TFW Java framework. Use when adding regression tests, testing new or modified behavior, investigating test failures, evaluating coverage, or running mutation testing.
---

# TFW Testing

## Purpose

Guide AI agents in writing meaningful, reliable tests for the TFW
Java framework.

Tests must provide evidence that the implementation satisfies its
intended contract. Merely executing code or increasing a coverage
percentage is not sufficient.

Follow the project's quality expectations in `CONTRIBUTING.md`
and its testing and validation procedures in
`docs/maintenance/development.md`.

## Before Writing Tests

1. Read the production code and its relevant interfaces.
2. Understand the documented behavior and invariants.
3. Inspect existing tests for the affected package and related
   functionality.
4. Identify normal behavior, boundary conditions, invalid inputs,
   exceptional conditions, and relevant interactions.
5. Determine whether the behavior is synchronous, asynchronous,
   stateful, or dependent on external resources.
6. Consult `tfw-architecture` when testing TSM or immutable
   architectural behavior.

Do not assume that an existing test demonstrates the complete
or correct way to test a behavior. Existing tests can contain
legacy patterns or incomplete coverage.

## Test Organization and Conventions

- Put tests under `src/test/java`.
- Mirror the production package structure.
- Name test classes after the production class or behavior being
  tested, using the `Test` suffix.
- Follow the existing JUnit Jupiter conventions.
- Use AssertJ assertions, consistent with the existing test suite.
- Keep test classes and methods package-private unless there is
  a specific reason for broader visibility.
- Follow the repository's configured Java formatting.
- Prefer focused tests with clear names that describe the behavior
  being verified.

Inspect nearby tests before introducing a new testing library,
framework, or test convention. Do not add dependencies merely
because another testing approach is available.

## Test Design

### Verify behavior, not implementation details

Tests should verify externally observable behavior and documented
contracts rather than duplicating the implementation.

- Test public behavior and relevant package-level contracts.
- Avoid tests that depend unnecessarily on private implementation
  details.
- Assert meaningful results, state transitions, and side effects.
- Include assertions that would fail if the intended behavior
  were broken.
- Avoid assertions that merely confirm an object was constructed
  or a method was invoked when the actual result matters.

### Cover important cases

Consider the cases relevant to the behavior being changed:

- Normal and expected behavior.
- Boundary values and empty inputs.
- Null and invalid arguments where the API defines their behavior.
- Exceptional conditions and failure paths.
- Resource ownership, cleanup, and lifecycle behavior.
- State transitions and interactions with other components.
- Regression cases for previously reported defects.

Do not add redundant tests simply to increase the test count.
Each test should provide distinct and useful verification.

### Keep tests deterministic

Tests should be deterministic unless nondeterminism is specifically
what is being tested.

- Avoid dependence on execution order.
- Avoid shared mutable state between tests.
- Avoid arbitrary sleeps as a substitute for synchronization.
- Do not depend on network services, external systems, or
  machine-specific state unless the test explicitly requires it.
- Clean up resources created by a test.
- Make test failures reproducible and diagnosable.

When testing concurrent or asynchronous behavior, use the
appropriate synchronization mechanism rather than assuming that
an operation has completed.

## TFW-Specific Testing

### Transactional State Management (TSM)

TSM tests should verify behavior across the relevant transaction
and state-change cycle, not merely the behavior of an isolated
method.

Depending on the change, consider:

- Initiator state changes and their consequences.
- Component execution and dependency ordering.
- Validator behavior before processor execution.
- Converter and synchronizer state propagation.
- Commit behavior after transaction processing.
- Rollback behavior and rollback-related commits.
- Event channel state and visibility across branches.
- Component addition, removal, and lifecycle behavior.
- Error handling and transaction recovery.

Use the existing TSM test infrastructure and helper classes
where appropriate. Inspect nearby tests such as `RootTest`,
`ConverterTest`, `ValidatorTest`, and `RollbackTest` for examples
of constructing roots, event channels, components, and test
transactions.

**Wait for asynchronous work to finish.** When a test uses
`BasicTransactionQueue`, call `waitTilEmpty()` at the appropriate
points before asserting results. Do not use arbitrary delays to
guess when transaction processing has completed.

When testing transaction failures, use an appropriate
`TransactionExceptionHandler` or existing test helper to verify
the actual failure behavior. Do not silently discard exceptions.

### Immutable data structures

Tests for `tfw.immutable` should verify the contracts of the
relevant interfaces and implementations.

Depending on the implementation, consider:

- Values returned at valid indices.
- Empty and boundary-length structures.
- Large lengths and index calculations.
- Composition, slicing, and strided access.
- Correct behavior of lazy or cached implementations.
- Resource ownership and `close()` behavior.
- Array-backed implementations and ownership-transfer semantics.
- Consistency between related implementations and adapters.

Do not assume that every implementation has identical internal
behavior. Test the documented contract while respecting
implementation-specific ownership and lifecycle rules.

### Swing and GUI tests

TFW includes Swing-related tests using AssertJ Swing.

GUI tests must respect Swing's event-dispatch-thread requirements.
Follow the existing use of `GuiActionRunner`,
`FailOnThreadViolationRepaintManager`, and AssertJ Swing fixtures.

Some GUI tests are disabled when the environment is headless.
The project's Maven CI workflow installs `tightvncserver` and
runs verification through `execute-on-vnc.sh` to provide a
graphical display.

Do not disable or skip GUI tests merely to avoid environment
problems. First determine whether the display, VNC setup, or
event-dispatch-thread handling is responsible for the failure.

## Coverage

TFW uses JaCoCo for code coverage.

Run the coverage-enabled verification build with:

```bash
./mvnw -Pcoverage verify
```

Coverage reports are generated in XML and HTML formats under
the Maven build output directory.

Use coverage to identify code that tests do not exercise, including
relevant branches and failure paths.

Coverage is not proof of correctness. A test can execute a line
without verifying its result. Add assertions and meaningful test
cases rather than optimizing only for a percentage.

The current JaCoCo configuration excludes the `tfw.math` classes
that are based on JDK math implementations. Consult `pom.xml`
for the authoritative exclusion list.

## Mutation Testing

TFW uses PIT to evaluate whether tests detect changes to program
behavior.

Run the configured mutation-testing profile with:

```bash
./mvnw -Ppitest test
```

The current PIT configuration targets TFW classes and tests while
excluding `tfw.math`. Consult `pom.xml` for the authoritative
configuration.

When reviewing mutation results:

- Investigate surviving mutants in code affected by the change.
- Determine whether a surviving mutant represents a missing
  assertion, missing test case, equivalent behavior, or another
  legitimate limitation.
- Add tests that distinguish the intended behavior from the
  mutated behavior when practical.
- Review uncovered code and test-selection problems as well as
  mutation scores.
- Do not suppress or exclude mutations merely to improve a score.

A report stating that no mutations were possible does not establish
that the tests provide strong coverage. Determine why no mutations
were generated and whether the relevant production code was
actually analyzed.

Mutation testing can take substantially longer than the normal
verification build. Use it when appropriate and inspect the
generated reports and CI results.

## Fuzz Testing

Fuzz testing complements deterministic unit and integration tests.
It explores input combinations and execution paths that may not
be covered by conventional tests.

For fuzzing-specific procedures, consult
`docs/maintenance/fuzzing.md` and the repository's current
fuzzing configuration.

### Privileged ARM64 emulation setup

The local fuzzing instructions include an optional Docker command
using `--privileged` to register AMD64 emulation on ARM64 hosts.

This operation grants the container broad privileges on the host.
An AI agent must not execute this command automatically.

Before running it:

1. Explain to the user why AMD64 emulation is needed and what
   the privileged operation does.
2. Obtain the user's explicit approval for this specific operation.
3. Verify that the `tonistiigi/binfmt` image comes from the
   official upstream project and resolve its published digest
   from a trusted source.
4. Use the immutable image digest (`@sha256:...`) rather than
   relying on a mutable image tag.
5. Do not invent or assume an image digest.

For example, after independently verifying the digest, the
command should use this form:

```bash
docker run --privileged --rm \
    tonistiigi/binfmt@sha256:<verified-digest> \
    --install amd64
```

If approval is not provided, do not execute the privileged
command or attempt an equivalent privileged operation.

When a fuzzing run discovers a defect:

- Treat the finding as a potential production defect.
- Reproduce and investigate the failure.
- Add a deterministic regression test when practical.
- Fix the underlying problem rather than suppressing the finding.

Do not assume that conventional tests make fuzz testing
unnecessary, or that fuzzing replaces deterministic regression
tests.

## Investigating Test Failures

When a test fails:

1. Read the complete failure output and stack trace.
2. Identify the assertion or operation that failed.
3. Determine whether the failure is caused by the change,
   a pre-existing defect, or the test environment.
4. Reproduce the failure with the narrowest useful test scope.
5. Inspect related implementation and test code.
6. Correct the underlying problem.
7. Run the focused test again.
8. Run the broader verification build.

For GUI-related failures, check the display and VNC environment.
For asynchronous TSM failures, check transaction completion and
exception handling.

Do not make a test pass by weakening assertions, removing relevant
cases, adding arbitrary delays, suppressing exceptions, or
disabling checks.

If a test must be changed because the intended contract has
legitimately changed, update the test to verify the new contract
and explain the change.

## Validation Workflow

For a typical production-code change:

1. Add or update tests that verify the intended behavior.
2. Run the focused test or test class.
3. Run the full Maven verification build:

   ```bash
   ./mvnw -B verify
   ```

4. Run coverage analysis when it is useful for evaluating
   the changed code:

   ```bash
   ./mvnw -Pcoverage verify
   ```

5. Run mutation testing when appropriate:

   ```bash
   ./mvnw -Ppitest test
   ```

6. Inspect the test results and generated reports.
7. Review the complete diff to ensure that tests and production
   changes are consistent.
8. Check the applicable GitHub Actions results.

Use the Maven Wrapper rather than assuming a particular
system Maven installation. On Windows, use `mvnw.cmd`.

A focused test passing does not replace the full verification
build. A successful local build does not replace checking
the actual CI results.

## Completion Criteria

Before considering testing complete:

- [ ] Tests cover the intended behavior.
- [ ] Relevant boundary and failure cases are addressed.
- [ ] Tests are deterministic and properly isolated.
- [ ] Asynchronous operations are synchronized correctly.
- [ ] Resources are cleaned up appropriately.
- [ ] Focused tests pass.
- [ ] The full Maven verification build passes.
- [ ] Coverage and mutation results have been reviewed when
      appropriate to the change.
- [ ] No relevant test failures or quality findings have been
      hidden or bypassed.
- [ ] Applicable CI checks have completed successfully.

Do not claim that tests or validation passed unless the results
have actually been verified.

## Important References

- `CONTRIBUTING.md`
- `docs/maintenance/development.md`
- `docs/maintenance/fuzzing.md`
- `pom.xml`
- `src/test/java/`
- `src/fuzz/java/`
- `.github/workflows/maven.yml`
- `execute-on-vnc.sh`

Consult the current files rather than relying on remembered
commands, plugin versions, or workflow behavior.
