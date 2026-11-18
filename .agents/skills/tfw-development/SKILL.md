---
name: tfw-development
description: Make changes to the TFW Java framework following its development conventions, repository practices, implementation patterns, and quality expectations. Use when implementing features, fixing bugs, refactoring code, or modifying the TFW repository.
---

# TFW Development

## Purpose

Guide AI agents in making correct, focused, maintainable changes
to the TFW source repository.

Follow the project's documented development practices and quality
expectations. The existing source code, tests, and documentation
are authoritative; do not invent conventions or assume that a
proposed implementation fits the project without investigating it.

## Before Making Changes

1. Understand the requested change and its intended behavior.
2. Check relevant issues, pull requests, and existing implementations
   when that context is available.
3. Read the relevant source code, interfaces, tests, and documentation.
4. Identify affected packages, public APIs, and dependent functionality.
5. Consult `tfw-architecture` when a change involves architectural
   decisions or TSM/immutable design constraints.
6. For significant changes, discuss the design before implementation
   rather than prematurely committing to an approach.

Do not assume that a request's proposed implementation is necessarily
the best way to achieve its intended result.

## Implementation Practices

- Follow existing naming, formatting, and implementation conventions.
- Prefer focused changes over broad refactoring.
- Reuse existing abstractions and functionality where appropriate.
- Avoid unnecessary dependencies and new abstractions.
- Preserve public API compatibility unless a change intentionally
  requires otherwise.
- Consider error handling, resource ownership, lifecycle behavior,
  and thread safety where relevant.
- Keep production code and tests consistent with documented contracts.
- Do not modify unrelated files or introduce incidental cleanup
  into a focused change.
- Do not weaken quality checks or suppress findings merely to make
  a build pass.

For architectural decisions, follow the `tfw-architecture` skill
and the relevant TFW documentation.

## Repository Development Practices

- Work on a dedicated branch; do not commit directly to `master`.
- Inspect the working tree before and after making changes.
- Keep changes focused and reviewable.
- Follow `CONTRIBUTING.md` and `docs/maintenance/development.md`.
- Use the Maven Wrapper rather than assuming a particular system
  Maven installation.
- Remember that TFW targets Java 8 bytecode even though development
  and CI use newer JDKs.
- Review generated or automatically modified files before including
  them in a change.

## Quality Expectations

TFW has a high standard for correctness, reliability, and
maintainability.

- Passing compilation alone is not sufficient.
- New functionality and bug fixes should include meaningful tests.
- Tests should verify intended behavior, including relevant edge
  cases and failure conditions.
- Preserve existing behavior unless the change explicitly intends
  to alter it.
- Investigate failures rather than bypassing or weakening checks.
- Do not claim that a check passed unless its result has been verified.

Consult `tfw-testing` for detailed testing practices and
`tfw-workflows` for CI/CD and GitHub Actions procedures.

## Development Workflow

For a typical change:

1. Establish the current state of the working tree and relevant tests.
2. Understand the existing implementation and its contracts.
3. Develop the smallest appropriate change.
4. Add or update tests and documentation as needed.
5. Run the relevant validation.
6. Inspect the complete diff for correctness and unintended changes.
7. Summarize the implementation, validation results, and any
   remaining concerns.
8. Submit the work through a pull request for review.

Do not report work as complete while relevant failures or
unverified assumptions remain undisclosed.

## Important References

- `CONTRIBUTING.md`
- `docs/maintenance/development.md`
- `docs/README.md`
- `pom.xml`
- `.mvn/wrapper/maven-wrapper.properties`
- `.github/workflows/`

Consult the current files rather than relying on remembered
commands, versions, or workflow behavior.
