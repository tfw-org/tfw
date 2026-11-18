---
name: tfw-documentation
description: Create, review, and maintain accurate documentation for the TFW Java framework. Use when modifying user guides, technical documentation, Javadocs, examples, README files, maintenance guides, or AI agent skills.
---

# TFW Documentation

## Purpose

Guide AI agents in creating, reviewing, and maintaining accurate,
useful, and consistent documentation for the TFW Java framework.

Documentation must describe the actual behavior and contracts of
TFW. It should help users understand and use the framework, help
contributors maintain it, and provide accurate reference information
for its public APIs.

The implementation, public interfaces, tests, build configuration,
and existing documentation are all important sources of evidence.
Do not invent behavior, API contracts, procedures, or guarantees.

## Before Writing or Changing Documentation

1. Understand the purpose and intended audience of the documentation.
2. Identify the relevant source code, public interfaces, tests, and
   existing documentation.
3. Determine whether the change affects user documentation,
   maintenance documentation, Javadocs, examples, or AI skills.
4. Read the existing material before introducing a new structure
   or convention.
5. Verify technical statements against the current implementation.
6. Identify links, examples, and other documents that may also need
   updating.
7. Keep the documentation change focused on the requested subject.

Do not assume that existing documentation is necessarily correct.
It may be incomplete or outdated. Investigate discrepancies between
documentation, implementation, tests, and configuration before
deciding what should change.

## Documentation Organization

TFW documentation is organized by audience and purpose.

### Project README

`README.md` is the main introduction to TFW.

It should provide a concise overview of the project, its principal
capabilities, how to get started, and where to find additional
information.

Keep it approachable and relatively concise. Link to detailed
guides rather than duplicating their contents.

When changing the project's capabilities, public resources,
documentation structure, or important project links, consider
whether the README should also be updated.

### User documentation

The `docs/` directory contains documentation for people using TFW.

Current documents include:

- `docs/README.md`  documentation index and navigation.
- `docs/getting-started.md`  installation, running an example,
  finding additional information, and security considerations.
- `docs/immutable.md`  the `tfw.immutable` package.
- `docs/tsm.md`  Transaction and State Management.

User documentation should explain what TFW provides, how its
features work, and how users can apply them correctly.

Keep conceptual explanations separate from detailed API reference
material. Link to the Javadocs when users need the complete
description of a public API.

### Maintenance documentation

The `docs/maintenance/` directory contains procedures for
contributors and maintainers.

Current documents include:

- `docs/maintenance/README.md`  maintenance guide index.
- `docs/maintenance/development.md`  development environment,
  build, testing, and validation.
- `docs/maintenance/deployment.md`  release and publishing
  procedure.
- `docs/maintenance/fuzzing.md`  building and running fuzzers.
- `docs/maintenance/metrics-token.md`  metrics token maintenance.

Maintenance guides should describe the actual procedures used
by the project.

When a procedure becomes automated, document the automation
and how to verify its results. Do not leave obsolete manual
instructions presented as the current standard procedure.

### API reference documentation

Javadocs are the detailed reference for TFW's public Java APIs.

The source documentation is maintained alongside the Java code
under `src/main/java/`.

The published API documentation includes:

- Current development Javadocs:
  https://tfw-org.github.io/tfw/javadoc/
- Latest released Javadocs:
  https://tfw-org.github.io/tfw/javadoc/latest/

Use Javadocs to describe the contracts of classes, interfaces,
constructors, methods, parameters, return values, and exceptions.

### AI agent skills

AI-facing instructions are maintained under:

`.agents/skills/`

Each skill has its own directory containing a `SKILL.md` file
and, when needed, supporting reference material.

Skills are not replacements for the user manual, maintenance
guides, or Javadocs.

They provide concise, actionable instructions to AI agents:
which project conventions to follow, which sources to consult,
which constraints matter, and how to perform particular tasks.

Avoid copying large portions of human-facing documentation into
a skill. Refer to the authoritative documentation instead.

## Writing User Documentation

### Accuracy and completeness

Documentation must reflect the actual implementation and its
supported contracts.

- Verify technical statements against source code and tests.
- Distinguish guaranteed behavior from implementation details.
- Identify limitations and important preconditions.
- Explain resource ownership and lifecycle requirements where
  relevant.
- Document thread-safety characteristics accurately.
- Avoid promising behavior that the implementation does not
  guarantee.
- Do not describe planned functionality as though it already exists.

When behavior is ambiguous, investigate the implementation and
tests. Ask for clarification when the intended contract cannot
be established reliably.

### Audience and terminology

Write for the intended reader.

- Introduce concepts before relying on specialized terminology.
- Use consistent names for TFW concepts and APIs.
- Explain TFW-specific terminology when it first becomes relevant.
- Prefer clear, direct language over unnecessarily complex prose.
- Use examples to clarify concepts that are difficult to explain
  abstractly.
- Avoid repeating material that is already explained elsewhere.

Documentation should be understandable to developers who are
not already familiar with the internal implementation.

### Examples

Examples should demonstrate real, supported usage.

- Prefer small examples that illustrate one concept at a time.
- Use actual API names and signatures.
- Verify that imports, constructors, and method calls are valid.
- Include necessary setup and cleanup.
- Explain important steps that are not obvious from the code.
- Avoid examples that depend on undocumented behavior.
- Keep examples consistent with the current Java compatibility
  requirements.

When practical, use existing project examples as a starting point.
The `HelloWorld` example is the preferred first introduction to
TSM:

`src/main/java/tfw/tsm/demo/HelloWorld.java`

Do not assume that an example remains correct simply because
it is already present in the repository. Check it against the
current implementation.

## Writing Javadocs

Javadocs are part of the public API contract, not merely comments
describing implementation.

### Public API documentation

When adding or changing public API elements, provide or update
Javadocs as appropriate.

Document:

- The purpose and behavior of the type or member.
- Important preconditions and constraints.
- Parameter meanings and valid values.
- Return values and their semantics.
- Exceptions that callers may need to handle.
- Resource ownership and cleanup requirements.
- Thread-safety and concurrency characteristics.
- Important side effects or lifecycle behavior.

Use `@param`, `@return`, and `@throws` where applicable.

Do not add tags that do not apply to the documented element.
Do not use vague descriptions such as "gets the value" when
the API has important semantics that callers need to understand.

### Contracts and limitations

Pay particular attention to details that affect how callers
can safely use an API.

Examples include:

- Whether null values are accepted or returned.
- Whether arguments or returned objects are mutable.
- Whether ownership of an array or other resource is transferred.
- Whether a method closes or consumes a resource.
- Whether an implementation is thread-safe.
- Whether a method can block or perform substantial work.
- Whether an operation can have externally visible side effects.

Do not infer these properties from method names or interface
types. Verify them in the implementation and existing contract.

### Consistency

When documenting an interface, consider its implementations
and callers. The documented contract should be appropriate
for the abstraction, not merely describe one implementation's
behavior.

When implementation-specific behavior matters, document it
at the appropriate implementation level.

Avoid copying the same lengthy documentation into multiple
overrides unless the override changes or clarifies the contract.

## TFW-Specific Documentation Considerations

### Immutable data structures

When documenting `tfw.immutable`, preserve the distinction
between an immutable access interface and the implementation
that supplies the data.

Important topics include:

- Primitive and object data types.
- Bits and bit-oriented access.
- Arrays, matrices, and streams.
- Long-based and BigInteger-based indexing and lengths.
- Lazy evaluation and optional caching.
- Composition and strided access.
- Resource ownership and `close()`.
- Array-backed implementations and ownership transfer.
- Factory behavior and thread-safety limitations.

Do not imply that all immutable implementations are automatically
thread-safe.

Do not describe ownership transfer as defensive copying. If an
implementation takes ownership of an underlying array, explain
the caller's responsibilities accurately.

Consult:

- `docs/immutable.md`
- The relevant interfaces and implementations under
  `src/main/java/tfw/immutable/`

### Transaction and State Management

When documenting `tfw.tsm`, ensure that explanations are consistent
with the transaction and component model.

Important concepts include:

- Components and their declared inputs and outputs.
- Event channels and state changes.
- Reactive and non-reactive inputs.
- Transactions and processing cycles.
- Validators and processors.
- Converters and synchronizers.
- Commits and rollback.
- Roots, branches, and component hierarchy.
- State change rules.
- Component and event-channel lifecycle behavior.

Be precise about the distinction between a transaction and
a processing cycle. A transaction may involve multiple cycles
while consequential work remains.

Do not simplify the processing model in a way that changes
the documented execution order or transaction semantics.

Consult:

- `docs/tsm.md`
- `src/main/java/tfw/tsm/`
- Relevant TSM tests under `src/test/java/`

### Security documentation

Security guidance must distinguish responsibilities provided
by TFW from responsibilities belonging to applications that
use or extend it.

TFW does not provide application-level authentication,
authorization, or access control.

When documenting application extensions that process
event-channel values, explain the need to validate untrusted
input, protect sensitive information, and control resource
consumption and external side effects.

Do not imply that using TFW automatically makes an application
secure.

Consult `docs/getting-started.md` and `SECURITY.md`.

## Diagrams and Markdown

### Markdown conventions

Follow the existing Markdown style in the repository.

- Use descriptive headings and a logical heading hierarchy.
- Keep paragraphs focused.
- Use lists for related items and sequential procedures.
- Use fenced code blocks with an appropriate language identifier.
- Use inline code formatting for class names, methods, paths,
  commands, and configuration properties.
- Use tables when they make comparisons or structured information
  easier to understand.
- Use descriptive link text rather than phrases such as "click here."
- Avoid unnecessary formatting and decorative elements.

### Links

Use repository-relative links for documents and source files
within the repository.

Use absolute URLs for external resources and published resources.

When moving or renaming a document:

1. Find references to its previous path.
2. Update affected links and documentation indexes.
3. Check README references and other relevant documents.
4. Verify that the new links resolve correctly.

Do not assume a link is valid merely because its target appears
in a previous version of the documentation.

### Diagrams

Use diagrams when they materially improve understanding,
especially for transaction processing, component relationships,
or other multi-step behavior.

Mermaid diagrams are appropriate for supported flowcharts and
other relationships that benefit from a visual representation.

A diagram must agree with the accompanying explanation and
the actual implementation.

Avoid diagrams that are more complicated than the concept
they are intended to explain. Update diagrams when the behavior
they represent changes.

## Maintaining Documentation Consistency

Documentation changes are often required even when the primary
change is in source code or configuration.

Consider documentation updates whenever changing:

- Public classes, interfaces, methods, or constructors.
- API contracts or observable behavior.
- Transaction or component semantics.
- Immutable data structure behavior.
- Resource ownership or thread-safety guarantees.
- Installation or usage instructions.
- Build requirements and development procedures.
- Maven plugins or build configuration.
- GitHub Actions workflows and automation.
- Release and publishing procedures.
- Security policies or supported security practices.
- Published documentation URLs or project resources.

Update the relevant documentation in the same change when
practical. Do not defer an essential correction simply because
the change is primarily a code change.

Avoid changing unrelated documentation merely to make incidental
wording improvements.

## Documentation Review Procedure

When reviewing documentation changes:

1. Identify the intended audience and purpose.
2. Read the complete changed document and relevant surrounding
   documentation.
3. Verify technical statements against current source code,
   interfaces, tests, and configuration.
4. Check examples for valid API usage and complete setup.
5. Check commands and procedures against current project
   configuration and automation.
6. Verify links and referenced paths.
7. Check that diagrams match the described behavior.
8. Look for contradictions with other TFW documentation.
9. Check whether indexes, README files, or related guides
   need updates.
10. Ensure that the change does not duplicate large amounts
    of existing material unnecessarily.
11. Review the final diff for accidental or unrelated changes.

A documentation review should evaluate correctness and usefulness,
not only spelling and formatting.

## Documentation Validation

Use the repository's configured build and documentation tooling.

The primary project verification command is:

```bash
./mvnw -B verify
```

The Maven Wrapper should be used rather than assuming a
particular system Maven installation. On Windows, use
`mvnw.cmd`.

Javadocs are generated through the Maven Javadoc Plugin.
Consult the current `pom.xml` for the authoritative plugin
configuration and options.

The project's published documentation is produced through
the GitHub Pages workflow:

`.github/workflows/publish-pages.yml`

That workflow publishes development and released Javadocs
and other generated project resources.

When changing Javadocs or the publishing workflow, inspect
the actual workflow and its results. A successful Markdown
edit or local compilation does not prove that the published
documentation was generated and deployed successfully.

Do not weaken Javadoc checks, suppress meaningful warnings,
or bypass documentation generation merely to make a build pass.
Investigate the underlying problem and preserve the project's
configured validation expectations.

For development and CI procedures, consult
`docs/maintenance/development.md` and the
`tfw-workflows` skill.

## Keeping Documentation Guides Current

The documentation index files are part of the documentation
structure and should be updated when that structure changes.

When adding, removing, or renaming a guide:

- Update `docs/README.md` as appropriate.
- Update `docs/maintenance/README.md` if a maintenance guide
  is affected.
- Update relevant links in the root `README.md`.
- Check links from other guides and skills.
- Remove references to documents that no longer exist.

Maintenance documentation should reflect current procedures,
not historical assumptions.

The release guide currently documents a specific manual
GitHub-based release process. Preserve its exact steps unless
the release process itself is intentionally changed and verified.

## AI Skill Maintenance

The `.agents/skills/` directory contains instructions for
AI agents working with TFW.

When modifying an existing skill:

- Read the complete skill and its supporting references.
- Check that instructions remain consistent with current
  source code, documentation, and workflows.
- Prefer concise, actionable guidance.
- Avoid duplicating the user manual or maintenance guides.
- Update references when files or procedures move.
- Do not create references to skills or files that do not exist.
- Preserve important safety, security, and quality constraints.

When adding a new skill, follow the established structure
and conventions of the existing TFW skills.

A skill should tell an agent what to do, what to verify,
and which authoritative sources to consult. It should not
become another copy of the project's complete documentation.

## Completion Criteria

Before considering a documentation change complete:

- [ ] The intended audience and purpose are clear.
- [ ] Technical statements match the current implementation.
- [ ] API contracts and limitations are described accurately.
- [ ] Examples and commands have been checked.
- [ ] Links and repository paths have been verified.
- [ ] Diagrams agree with the documented behavior.
- [ ] Related indexes and documents have been updated.
- [ ] No contradictory or obsolete instructions remain.
- [ ] The documentation is consistent with existing TFW terminology.
- [ ] The final diff contains no unrelated changes.
- [ ] Applicable documentation and build checks have passed.
- [ ] Any remaining limitations or unverified items are disclosed.

Do not claim that documentation has been validated or published
unless the corresponding checks or deployment results have
actually been verified.

## Important References

- `README.md`
- `CONTRIBUTING.md`
- `SECURITY.md`
- `docs/README.md`
- `docs/getting-started.md`
- `docs/immutable.md`
- `docs/tsm.md`
- `docs/maintenance/README.md`
- `docs/maintenance/development.md`
- `docs/maintenance/deployment.md`
- `docs/maintenance/fuzzing.md`
- `src/main/java/`
- `src/test/java/`
- `pom.xml`
- `.github/workflows/publish-pages.yml`
- `.agents/skills/`
