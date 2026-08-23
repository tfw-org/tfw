# Contributing to TFW

Thank you for your interest in contributing to TFW.

TFW is an open-source Java framework for building highly scalable and maintainable applications. Contributions are welcome, including bug fixes, improvements, tests, documentation, performance improvements, and new functionality.

## Project Quality Standard

TFW has a deliberately high standard for reliability and testing.

The long-term goal is for all production code to be extremely reliable, thoroughly tested, and continuously verified by the project's automated quality and security tooling.

Passing compilation and a basic test suite is not considered sufficient. TFW uses multiple complementary forms of verification, including:

- Unit and integration testing.
- Mutation testing with PIT.
- Fuzz testing with ClusterFuzzLite.
- Code coverage analysis.
- Static analysis and formatting checks.
- Security and dependency analysis.
- SonarCloud analysis.
- Other automated repository checks.

The expectation is that all applicable GitHub Actions workflows and Maven verification checks pass completely.

### Current Repository Status

The repository is not yet completely at this standard.

Significant effort is being made to bring the entire repository, including existing code and tests, to the intended level of reliability and verification.

Existing deficiencies should not be interpreted as permission to introduce additional deficiencies. New or substantially modified code should meet the target standard from the outset. When practical, contributors are encouraged to improve existing code toward that standard.

The goal is to steadily reach a state where the automated checks provide strong evidence that TFW is correct, reliable, secure, and maintainable.

## Before You Start

For significant changes, please open or comment on an issue before beginning work. This gives the project an opportunity to discuss the proposed change and its design before substantial development effort is invested.

For small fixes, such as documentation corrections or straightforward bug fixes, you may proceed directly to a pull request.

Please check existing issues and pull requests before starting work.

## Development Environment

TFW is a Maven project and maintains Java 8 compatibility for its generated library artifacts.

The project's continuous integration currently verifies the build using Java 21 and Java 25 across the Zulu, Temurin, and Corretto distributions.

Before submitting a pull request, make sure that `mvn verify` completes successfully.

The complete GitHub Actions verification is broader than `mvn verify`, so a successful local build does not by itself mean that a pull request is ready to merge.

## Testing

Tests are a fundamental part of TFW development.

New functionality and bug fixes should include appropriate tests. Tests should provide meaningful evidence of correct behavior rather than merely increasing code coverage.

Where appropriate, tests should cover:

- Normal behavior and important edge cases.
- Invalid or exceptional inputs.
- Failure and recovery behavior.
- State transitions and transactional behavior.
- Interactions between components.

Tests should be deterministic unless nondeterminism is specifically what is being tested.

### Mutation Testing

TFW uses PIT mutation testing to evaluate whether tests can detect incorrect implementations. Mutation testing complements code coverage by measuring test effectiveness rather than simply measuring which code executes.

The PIT profile can be run locally with the `pitest` Maven profile.

### Fuzz Testing

TFW also uses ClusterFuzzLite for automated fuzz testing.

Fuzz testing explores inputs and execution paths that may not be anticipated by conventional tests. It is particularly useful for finding boundary conditions, malformed inputs, unexpected state combinations, and other defects.

Fuzzing failures should be treated as potential defects and investigated rather than suppressed. When practical, a discovered defect should also receive a deterministic regression test.

## Code Quality

TFW uses automated tools including Spotless/Palantir Java Format, Error Prone, PMD, JaCoCo, PIT, SonarCloud, and CycloneDX.

Contributors should work with these tools rather than around them.

Fix the underlying problem when a check fails. Do not disable, bypass, weaken, or suppress a check simply to make a build pass. Legitimate exceptions should be explained in the pull request.

## Pull Requests

Pull requests should:

- Explain what the change does and why it is needed.
- Include appropriate tests.
- Keep unrelated changes out of the pull request.
- Follow the project's code and documentation conventions.
- Pass all applicable automated checks.
- Avoid introducing new warnings, quality violations, or security findings.
- Preserve or improve the reliability of the affected code.

All applicable workflows and Maven verification checks must pass completely before a change is considered ready for merge.

This may include:

- Compilation and unit tests.
- Formatting and static analysis.
- Code coverage.
- Mutation testing.
- Fuzz testing with ClusterFuzzLite.
- CodeQL.
- Dependency and security checks.
- SonarCloud.
- Repository security checks.

If a check fails, investigate and correct the underlying problem.

Changes to workflows or build configuration that weaken or bypass quality checks require particular scrutiny and should be clearly justified.

## Public API Changes

TFW is a framework, so public API changes require particular care.

Before changing an existing API, consider compatibility, existing behavior, documentation, and how the change can be thoroughly tested.

For substantial API changes, discuss the design in an issue before implementing it.

## Improving Existing Code

The repository is actively being brought toward its target quality standard. Contributors will therefore encounter existing code that does not yet meet that standard.

When working in such areas, improvements are encouraged when they can be made without unnecessarily expanding the scope of the change. Examples include adding missing tests, improving inadequate tests, addressing static-analysis findings, correcting reliability problems, and adding appropriate fuzz coverage.

Avoid combining large unrelated cleanup efforts with a focused functional change. Broader improvements are generally better handled separately.

## Security

Please do not report security vulnerabilities through public GitHub issues.

Use the repository's security reporting mechanism so vulnerabilities can be evaluated privately.

Security findings from automated checks should be investigated and addressed rather than simply suppressed.

## Commit Messages

Use clear and descriptive commit messages that describe the change being introduced.

## Review Process

All changes are reviewed through GitHub pull requests.

Maintainers may request changes to improve correctness, reliability, testing, security, API design, maintainability, documentation, or consistency with the existing architecture.

A change may be rejected even when its functional behavior appears correct if it does not meet the project's reliability and verification requirements.

When in doubt, prioritize correctness, reliability, testability, and maintainability over speed of implementation.

Thank you for helping improve TFW.
