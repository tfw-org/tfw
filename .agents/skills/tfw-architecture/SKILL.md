---
name: tfw-architecture
description: Understand and preserve the architecture and design principles of the TFW Java framework. Use when designing, implementing, reviewing, or modifying TFW components, transactions, event channels, immutable data structures, or public APIs.
---

# TFW Architecture

## Purpose

Understand TFW's architectural principles and ensure that proposed
changes preserve them.

TFW consists primarily of two complementary packages:

- `tfw.tsm`: Transaction and State Management.
- `tfw.immutable`: Immutable data structures and data access abstractions.

The architecture and existing project documentation are authoritative.
Do not invent architectural behavior or assume that a proposed design
is consistent merely because it seems reasonable.

## General Design Principles

- Favor composition over direct dependencies between components.
- Keep components focused on well-defined responsibilities.
- Prefer immutable data for state shared between components.
- Make dependencies explicit through declared inputs and outputs.
- Preserve transaction consistency and predictable state transitions.
- Favor simple, maintainable implementations.
- Preserve existing public API compatibility unless a change is
  intentional and justified.

## Transaction and State Management (TSM)

### Component Model

- Components are intended to be stateless.
- The framework supplies current input state immediately before
  component execution.
- Components declare their inputs and outputs.
- Components communicate through event channels rather than direct
  calls to other components.
- All required state-bearing inputs must be non-null when a component
  executes, regardless of whether an input is reactive or non-reactive.
- Diagnostic methods such as `debugConvert()` are not substitutes
  for normal processing.

### Event Channels

- Event channels provide the communication mechanism between
  components.
- State-bearing channels represent values.
- Stateless channels represent triggers without carrying state.
- Prefer separate channels for logically independent pieces of state.
- Prefer immutable values for state carried by event channels.
- State change rules should reflect the semantics of the value.
- Respect the visibility and hierarchy rules of Root and Branch
  structures.

### Transactions

- A transaction is the fundamental unit of TSM processing.
- A transaction may contain multiple processing cycles.
- Processing continues until all consequential work has completed.
- Validators execute before processors.
- Processors include converters and synchronizers.
- Changes produced during a cycle may require subsequent cycles.
- Commits execute after transaction processing.
- Rollback event channels terminate normal transaction processing
  and trigger commits that listen for rollback.

Do not confuse a processing cycle with a transaction.

### Component Hierarchy

Understand the distinction between:

- Root
- Branch
- MultiplexedBranch

When modifying hierarchy behavior, examine the existing implementation
and tests. Pay particular attention to descendant visibility,
structural changes, and component lifecycle behavior.

## Immutable Data

The `tfw.immutable` package separates logical data access from
physical storage and retrieval.

Important principles:

- Support eight Java primitive types, generic objects, and Bits.
- ILA uses long-based indexing and lengths.
- IBA supports BigInteger-based indexing and lengths.
- Prefer small, consistent interfaces.
- Avoid unnecessary copying when ownership transfer is documented.
- Understand resource ownership and closing requirements.
- Do not assume that immutable interface instances are thread-safe.
- Factories can provide independent instances for separate threads.
- Prefer lazy evaluation and avoid unnecessary caching.
- Introduce caching only when justified by measurements and access
  patterns.

## Architectural Review Procedure

When evaluating an architectural change:

1. Identify the affected package, interfaces, and components.
2. Read the relevant project documentation.
3. Examine the existing implementation.
4. Examine related tests and usages.
5. Identify architectural assumptions and dependencies.
6. Evaluate transaction, state, and lifecycle implications.
7. Consider thread safety and resource ownership where applicable.
8. Evaluate public API and backward compatibility.
9. Recommend the smallest change that correctly addresses the
   requirement.

Do not propose large architectural refactoring when a focused
implementation change is sufficient.

## Important References

Consult these documents in the TFW repository:

- `docs/tsm.md`
- `docs/immutable.md`
- `docs/README.md`
- `CONTRIBUTING.md`

When documentation and implementation appear inconsistent,
investigate the discrepancy rather than assuming either is correct.
