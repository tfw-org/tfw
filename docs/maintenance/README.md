# TFW Maintenance Guides

This section documents the procedures used to develop, maintain, test, and release TFW. It is intended for contributors and maintainers working on the TFW source repository.

The guides complement the user documentation: they explain how to work on the framework itself rather than how to use its public APIs.

## Guides

### [Development](development.md)

Development practices and workflows for making changes to TFW, including building the project, running tests, and following the project's development conventions.

### [Releasing](deployment.md)

The release process for TFW, including version preparation, publishing artifacts to Maven Central, and verifying the results of a release.

### [Fuzzing](fuzzing.md)

How to build and run TFW fuzzers, work with the fuzzing infrastructure, and use fuzzing results to improve robustness.

## Keeping the guides current

These guides should describe the repository's actual configuration and workflows. When a relevant build configuration, GitHub Actions workflow, or development procedure changes, update the corresponding guide so that it remains a reliable reference.

Where a procedure is automated, document the automation and how to verify its results rather than presenting an obsolete manual procedure as the standard approach.
