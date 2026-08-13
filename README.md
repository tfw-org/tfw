# TFW

**TFW is a lightweight Java framework for building large-scale applications.** It is composed of two main packages, `tfw.immutable` and `tfw.tsm`, which provide complementary foundations for managing application data, state, and component composition.

## Overview

Large-scale applications benefit from strong boundaries around data and state. **TFW** is designed around this principle, providing immutable data structures and a transactional state-management framework that work together to make complex applications easier to compose, reason about, and maintain.

### `tfw.immutable`

The `tfw.immutable` package provides efficient **immutable data structures** for working with arrays, matrices, and streams.

It includes specialized implementations for both primitive and object types, allowing data to be safely shared between components without unintended mutation.

Key characteristics include:

- Immutable arrays and matrices
- Support for primitive and object types
- Stream-oriented data access
- Efficient data sharing without defensive copying
- Data structures designed for safe composition

### `tfw.tsm`

The `tfw.tsm` package provides a **transaction-based state-management and component framework** for building large, modular applications.

Application state and communication channels are organized into a hierarchical component model supporting:

- Transactional state changes
- Publish/subscribe messaging
- Hierarchical component composition
- Structural changes
- Dependency management
- Rollback support
- Modular, independently manageable components

The design of `tfw.tsm` is inspired by the component-programming principles described by **Clemens Szyperski** in *Component Software: Beyond Object-Oriented Programming*. The goal is to provide a practical foundation for composing large applications from well-defined components with controlled interactions and predictable state transitions.

## Designed for Large-Scale Applications

`tfw.immutable` and `tfw.tsm` are designed to complement one another.

Immutable data structures provide a safe and predictable way for components to share information, while the transactional state-management system provides the mechanisms needed to coordinate changes and communication across a large component hierarchy.

Together, they provide a foundation for applications whose **data and state can be composed, observed, validated, and updated in a controlled and predictable manner**.

## Lightweight by Design

TFW has **only one external dependency: [SLF4J](https://www.slf4j.org/)**.

This keeps the framework lightweight while allowing applications to integrate it into existing Java environments without bringing along a large dependency tree.

## Design Philosophy

TFW is built around a few core ideas:

**Immutability**  
Data that is shared between components should be safe from unintended modification.

**Composition**  
Large applications should be assembled from smaller, well-defined components rather than built as a single monolithic system.

**Transactions**  
Changes to application state should be coordinated and treated as atomic operations where appropriate.

**Controlled Communication**  
Components should communicate through explicit channels rather than relying on hidden dependencies and shared mutable state.

**Predictability**  
The structure and behavior of an application should remain understandable as the application grows.

---

**TFW — Immutable data. Transactional state. Composable applications.**
