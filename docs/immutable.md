# TFW Immutable Data

## Introduction

The `tfw.immutable` packages provide abstractions for accessing immutable data through simple, consistent interfaces. They cover typed arrays, multidimensional matrices, and input streams, while allowing implementations to use different underlying storage and retrieval strategies.

The central design idea is to **separate the interface used to access data from the mechanism used to provide it**. An immutable array might be backed by a Java array, a file, an input stream, a calculation, or a composition of other immutable arrays. Code that consumes the data can use the same abstraction without needing to know how the data is stored or produced.

This separation makes it possible to work with large logical data structures, avoid unnecessary copying, and introduce specialized implementations when performance or resource usage warrants it.

## Simple to use, sophisticated to implement

One of the most important characteristics of `tfw.immutable` is that **implementing an immutable data source can be complicated, while using one is intended to be straightforward**.

A correct implementation may need to handle indexing and bounds, resource management, data retrieval, composition, lazy evaluation, and caching. These concerns can require considerable care and specialized knowledge. Implementations must also respect the contracts of their interfaces, even when their underlying storage or retrieval mechanisms differ substantially.

The consumer-facing API is deliberately much simpler. Depending on the abstraction, the essential operations are typically to determine the logical length or dimensions, retrieve data, and close the resource when required. Constructors and factory methods provide a straightforward way to obtain an implementation.

This contrasts with lower-level APIs such as `InputStream` and `SeekableByteChannel`, whose operations expose more of the mechanics of reading and positioning. The immutable abstractions aim to provide a more uniform way to access data without requiring every consumer to understand those mechanics.

Most developers using `tfw.immutable` should not need to implement these abstractions themselves. They can use existing implementations and combine them to build the data structures their applications require. The complexity is concentrated in the implementations rather than imposed on every consumer.

The simplicity of the API does not eliminate resource-management responsibilities. Where an implementation owns resources and exposes a `close()` operation, callers must still respect its lifetime and close it appropriately.

## Thread safety and ownership

The immutable interfaces are not necessarily thread-safe. Although their data is immutable through the interface, an implementation may maintain internal state, use resources that are not safe for concurrent access, or otherwise rely on being accessed by a single thread. Unless an implementation explicitly documents otherwise, an ILA, IBA, ILM, or other immutable interface should generally be treated as belonging to one thread at a time.

Factories provide a way to make these implementations usable in multithreaded applications. A factory can be shared among multiple threads, with each thread calling the factory's `create()` method to obtain its own instance of the immutable interface.

This separates the thread-safety requirement from the implementation that provides the data. Rather than requiring every immutable implementation to support concurrent access to the same instance, the factory allows each thread to work with its own instance.

For example, an application can share one factory among several worker threads. Each worker creates its own immutable array and accesses that instance independently. The factory must provide the thread-safety needed for concurrent creation, while the resulting instances need not themselves support concurrent access.

**The key distinction is that sharing a factory is not the same as sharing an instance.** The factory is the shared entry point; the instances it creates are intended to be used independently by their respective threads.

## Design principles

The immutable abstractions follow several related principles.

### Type-specific interfaces

The library provides type-specific interfaces for primitive values and objects. This allows consumers to work with the appropriate Java types directly instead of routing every value through a generic object representation.

For primitive types, specialized interfaces can avoid the boxing and unboxing that a generic object-based design might require. They also make the types of values being read and written explicit in the API. The actual performance benefit depends on the implementation and usage pattern.

### Consistent support for ten data types

The immutable packages follow a consistent design across ten data types:

- **Eight Java primitive types:** `boolean`, `byte`, `char`, `short`, `int`, `long`, `float`, and `double`.
- **Generic objects:** values of a reference type.
- **Bits:** bit data represented within primitive `long` values.

This pattern is used across the relevant immutable abstractions and their factory packages. For example, ILA and its factory counterpart, ILAF, provide the long-indexed array abstraction and factories for these data types; IBA provides the corresponding abstraction for arrays indexed using `BigInteger`.

The consistent type coverage makes it easier to apply the same design concepts across different data types and immutable abstractions.

### Minimal interfaces

The core interfaces are intentionally small. They describe the essential operations needed to access the data rather than attempting to expose every capability that an implementation might support.

Additional behavior can be supplied through specialized implementations and composition. This keeps the common interfaces easier to understand and allows different implementations to make different trade-offs.

### Separation of logical data from physical storage

The size of a logical immutable array is not necessarily the size of a Java array allocated in memory. An implementation may retrieve data from a file, calculate values when requested, or combine data from multiple sources.

This distinction is particularly important for arrays with very large logical lengths. Access can use a long index even though each individual transfer uses a normal Java array and an `int` transfer length.

### Composition

Immutable data structures can be built from other immutable data structures. For example, concatenation can present two arrays as one logical array, and a segment can present a selected portion of another array.

Composition allows new views to be created without necessarily copying all the underlying data into a new array.

### Lazy evaluation by design

Because immutable structures may represent extremely large logical data sets, implementations generally aim to compute or retrieve only the data requested by the consumer. Small internal buffers may improve algorithmic efficiency or be essential to a particular implementation.

Additional caching should not be assumed to improve performance. The recommended starting point is an uncached stack of immutable implementations, with caches introduced only when measurements justify them and their placement and behavior match the calling method's access pattern.

## Immutable Long Arrays (ILA)

The `tfw.immutable.ila` package provides the Immutable Long Array abstraction, or **ILA**.

The word *Long* refers to the type used for the array's logical length and indexing, not the type of values it contains. An ILA uses `long` for its logical length and positions, while specialized interfaces determine the element type. For example, `IntIla` represents an ILA containing `int` values, and `LongIla` represents one containing `long` values.

For logical lengths and indices that exceed the range of `long`, TFW provides the Immutable Big Integer Array abstraction (IBA), which uses `BigInteger` for its logical length and indexing.

An ILA represents a logical sequence of values indexed using `long`. The library includes type-specific interfaces for primitive values and objects, including implementations for values such as `int`, `long`, `double`, and other Java primitive types.

The core `ImmutableLongArray` interface extends `Closeable` and provides a `length()` method returning a `long`. Type-specific interfaces add methods for retrieving values into caller-provided arrays.

For example, `IntIla` provides an operation of the following form:

```java
void get(int[] array, int arrayOffset, long ilaStart, int length)
        throws IOException;
```

The parameters distinguish two different kinds of indexing:

- `arrayOffset` identifies the destination position in the ordinary Java array.
- `ilaStart` identifies the starting position in the logical ILA.
- `length` specifies the number of values to transfer.

The logical source position is a `long`, while the number of values transferred in one call is an `int`. This allows an ILA to represent a sequence whose logical length exceeds the range of a Java array's length, without requiring the entire sequence to reside in one Java array.

For example, a consumer can retrieve a section of a large ILA into a suitably sized destination array, process it, and then retrieve another section. The consumer need not allocate a destination array as large as the complete logical ILA.

The underlying implementation determines how requested values are obtained. They might be read from memory, retrieved from another source, calculated, or delegated to another immutable array.

## Immutable Big Integer Arrays (IBA)

The `tfw.immutable.iba` package provides the Immutable Big Integer Array abstraction, or **IBA**.

An IBA is similar in purpose to an ILA, but uses `BigInteger` for its logical length and indexing. This supports logical arrays whose lengths and positions can exceed the range of `long`.

The core `ImmutableBigIntegerArray` interface provides a `length()` method returning `BigInteger`. Type-specific interfaces use `BigInteger` for the starting position of a retrieval operation while continuing to use ordinary Java arrays as destinations.

For example, `IntIba` provides an operation of the following form:

```java
void get(int[] array, int arrayOffset, BigInteger ibaStart, int length)
        throws IOException;
```

As with ILA, the destination array and the transfer length remain bounded by ordinary Java array and method parameter limits. The `BigInteger` index describes the position in the logical data source; it does not imply that a single Java array of that size is allocated.

IBA is useful when the logical size of a data set requires indexing beyond the `long` range. It retains the same general approach as ILA: provide a simple typed interface for retrieving portions of a potentially much larger logical sequence.

## Immutable Long Matrices (ILM)

The `tfw.immutable.ilm` package provides the Immutable Long Matrix abstraction, or **ILM**.

An ILM represents a two-dimensional logical structure. The `ImmutableLongMatrix` interface provides `width()` and `height()` methods, both returning `long`.

Type-specific matrix interfaces provide operations for retrieving rectangular regions into ordinary Java arrays. For example, `IntIlm` accepts starting row and column positions, along with row and column counts.

This supports access to a portion of a matrix without requiring the consumer to retrieve the entire matrix at once. As with ILA, logical dimensions and positions are distinct from the size of the Java array used for an individual transfer.

The matrix abstraction also separates the logical two-dimensional data from its backing representation. Different implementations can provide the same matrix interface while using different storage or retrieval strategies.

## Immutable Input Streams (IIS)

The `tfw.immutable.iis` package provides typed immutable input stream abstractions, or **IIS**.

These interfaces provide a simpler, type-specific alternative to using `java.io.InputStream` when consuming streams of values other than bytes. The library supplies corresponding stream interfaces for different data types.

For example, `IntIis` provides operations of the following form:

```java
int read(int[] array, int offset, int length) throws IOException;

long skip(long n) throws IOException;
```

The `read` operation fills a portion of the supplied array and returns the number of values read, or `-1` when the end of the stream is reached. The `skip` operation advances through the stream without requiring the skipped values to be returned to the caller.

The interface follows familiar stream-reading conventions while allowing values to be transferred in their natural Java types. A consumer of an integer stream, for example, can read directly into an `int[]` rather than converting a sequence of bytes into integers itself.

As with the other immutable abstractions, the interface separates the consumer from the underlying mechanism that provides the data. Resource management remains important for implementations that own resources.

## Composition: building larger structures from smaller ones

Composition is a central benefit of the immutable interfaces. Rather than copying data whenever a new logical structure is needed, an implementation can present existing immutable structures through another interface.

Two examples are concatenation and segmentation.

### Concatenation

A concatenated array presents two source arrays as one logical sequence. The first source occupies the beginning of the resulting sequence, followed by the second source.

For example, given arrays `left` and `right`, a concatenated array represents:

```text
[left values][right values]
```

The consumer accesses the result as one array. The implementation determines which source provides a requested range and delegates retrieval accordingly.

This makes it possible to combine arrays without necessarily copying all their values into a newly allocated array. It is especially useful when the source arrays are themselves backed by files, calculations, or other implementations.

### Segments

A segment represents a selected range of an existing array. It provides a logical view of that range, with positions relative to the segment rather than to the original array.

For example, a segment beginning at position `start` with a specified length can expose that portion of a source array as a separate logical array. Requests to the segment are translated into requests to the corresponding positions in the source.

This allows consumers to work with a smaller logical range without requiring the selected values to be copied into a new array.

### Other implementations

Concatenation and segments illustrate the general approach: implementations can compose other immutable values while preserving the common access interface. Other specialized implementations can support different storage layouts or access patterns when needed.

These implementation choices are not necessarily visible to the consumer. Code that only needs the common interface can often use a composed result just as it would use another immutable array.

## Lazy evaluation, buffering, and caching

The immutable interfaces are designed with the assumption that extremely large amounts of data may be manipulated. Consequently, **lazy evaluation is used extensively so that implementations compute or retrieve only the data that is actually requested**.

An immutable array may represent a logical data set far larger than the amount of data that needs to be accessed at any one time. Computing the entire data set eagerly would often be impractical and could waste substantial time and resources. Lazy evaluation allows an implementation to represent a large logical structure while doing work incrementally, as consumers request particular values or ranges.

### Internal buffering

Small internal buffers can improve the efficiency of an algorithm without changing the logical structure exposed to the consumer. For example, an implementation may buffer a small amount of data to reduce repeated I/O or to make an underlying calculation more efficient.

In some implementations, buffering is also the essential mechanism that makes the immutable data source practical. A circular cache, for example, can retain a limited working set of values while providing access to a much larger logical sequence.

The important distinction is that internal buffering is not necessarily the same as adding a cache to an immutable stack. A buffer may simply be part of an implementation's algorithm or representation, whereas a cache added to a stack is an explicit strategy for retaining results to accelerate subsequent requests.

### Build the stack before adding caches

Experience with `tfw.immutable` has shown that it is best to begin by composing the required immutable implementations into a stack **without adding caching**, and then introduce caching only when performance measurements show that it is needed.

An immutable stack may combine several layers, each of which delegates to or transforms data from another layer. Lazy evaluation allows the requested data to flow through these layers without requiring the entire logical data set to be materialized.

Starting without additional caches keeps the design simpler and makes the actual performance characteristics of the stack easier to evaluate. If performance is inadequate, the next step is to determine which work is being repeated or which access pattern is expensive, and then select an appropriate cache and place it at the appropriate point in the stack.

### Choosing the right cache

Caching is not a universally beneficial optimization. **The right cache depends on the access pattern of the calling method**, including how it requests data and how often particular values or ranges are revisited.

A cache that works well for one access pattern may be ineffective for another. It may retain data that is rarely used, evict data that is needed repeatedly, add unnecessary overhead, or interfere with the behavior of an otherwise efficient implementation. In these cases, adding a cache can make performance worse rather than better.

Cache selection therefore requires understanding how the calling code uses the immutable data, not merely identifying an implementation that could store previously requested values. Cache placement matters as well: a cache should be located at the layer where retaining data is most useful for the actual workload.

The recommended approach is:

1. Compose the required immutable implementations without additional caching.
2. Measure performance using the expected workload.
3. Identify the source of unnecessary computation, retrieval, or repeated work.
4. Select a cache suited to the calling method's access pattern.
5. Place the cache at the appropriate point in the immutable stack.
6. Measure again to verify that the cache improves performance.

This approach preserves the benefits of lazy evaluation and avoids introducing unnecessary complexity or memory consumption. It also recognizes that caching is a design decision that must be justified by the behavior of the complete stack, rather than an optimization that should automatically be applied to every layer.

In short, **use lazy evaluation by default, use internal buffering where an algorithm or implementation needs it, and add explicit caching only when measurements and access patterns justify it**.

## Factories and implementation choices

The library includes factories and specialized implementations for creating and using immutable data structures. Factory methods provide a straightforward way to obtain a suitable implementation without requiring consumers to understand all of its internal details.

**Factories also provide the thread-safe mechanism for sharing the ability to create immutable instances across multiple threads.** A factory can be shared among threads, and each thread can call `create()` to obtain an instance for its own use. This avoids requiring the created immutable interface to support concurrent access by multiple threads.

The factory's thread-safety and the thread-safety of the instances it creates are separate concerns. The factory must support the concurrent creation promised by its contract; the instances it creates need not themselves support concurrent access.

This model is particularly useful when an implementation uses internal state or resources that are best kept local to one instance. Each thread can use the same factory while maintaining independent access through its own instance.

The appropriate implementation depends on the data source and the intended access pattern. An implementation backed by an existing array may be suitable when the values are already in memory. Another implementation may be better when values are obtained from a resource, calculated on demand, or assembled from other immutable structures.

The abstraction allows consumers to depend on the operations they need rather than on a particular storage strategy. This also makes it possible to introduce a different implementation without requiring all consumers to be rewritten, provided that the replacement satisfies the same interface contract.

Implementing a new immutable data source is a separate matter. Correctly handling indexing, bounds, resources, composition, and any lazy or cached behavior can require substantial expertise. The existing implementations and factories allow most users to benefit from the abstraction without taking on that complexity.

## Java Stream integration

The library provides adapters that allow certain ILA implementations to be consumed through the Java Stream API.

The supported stream types correspond to the types defined by Java in `java.util.stream`:

- `Stream<T>` for object values.
- `IntStream` for `int` values.
- `LongStream` for `long` values.
- `DoubleStream` for `double` values.

The adapters reflect the stream types Java provides; they are not an arbitrary selection of ILA element types. They make it possible to use familiar Java Stream operations with supported immutable arrays, while retaining the underlying immutable-array interfaces as a separate way to access the data.

## Choosing an abstraction

The main choice is the shape and scale of the logical data being accessed.

| Abstraction | Use when |
|---|---|
| ILA | The data is a one-dimensional logical array indexed by `long`. |
| IBA | The data is a one-dimensional logical array requiring `BigInteger` lengths or indices. |
| ILM | The data is a two-dimensional logical matrix with `long` dimensions and positions. |
| IIS | The data is consumed sequentially as a typed input stream. |

Within each abstraction, the type-specific interface should match the kind of values being handled.

The choice of implementation is a separate decision. It depends on where the data comes from, whether it is already in memory, how expensive it is to obtain, whether it will be accessed repeatedly, and whether composition can avoid unnecessary copying.

In multithreaded applications, also decide whether an instance will be confined to one thread or explicitly shared under an implementation's documented thread-safety guarantees. When independent instances are appropriate, share a factory and have each thread create its own instance.

## Designing with `tfw.immutable`

When using the library, it is useful to keep four concerns separate:

1. **The logical data structure.** Decide whether the application needs a one-dimensional array, a very large indexed array, a matrix, or a sequential stream.
2. **The access interface.** Use the type-specific interface that expresses the required operations and value type.
3. **The implementation strategy.** Select an existing implementation or compose immutable structures to provide the desired behavior.
4. **Thread ownership.** Decide how instances are owned and accessed. Unless an implementation documents otherwise, use each instance from one thread at a time; share a suitable factory when multiple threads need their own instances.

This separation keeps application code focused on what it needs to do with the data rather than on how the data is stored or retrieved.

It also allows implementations to make different trade-offs without forcing those details into every consumer. A simple in-memory source, a resource-backed source, and a composed source can all provide the same general style of access. Factories can make such implementations convenient to create independently in multithreaded applications.

The intended result is a library in which the difficult work of implementing reliable data sources is handled in specialized implementations, while the ordinary task of consuming immutable data remains simple and consistent.

## Summary

`tfw.immutable` provides a family of typed abstractions for accessing immutable arrays, very large logical arrays, matrices, and input streams.

Its design emphasizes:

- Small, consistent access interfaces.
- Type-specific operations for primitive values and objects.
- Logical lengths and indices that need not correspond to a single physical Java array.
- Composition of existing data structures without requiring full copies.
- The ability for implementations to use different storage and retrieval strategies.
- Extensive lazy evaluation to avoid computing or retrieving data that is never requested.
- Internal buffering where it benefits an algorithm or is required by an implementation.
- Caching introduced deliberately, only when justified by performance measurements and the calling method's access pattern.
- Straightforward consumption of abstractions whose implementations may be sophisticated.
- A clear thread-safety model in which factories can be shared while instances are generally used independently by individual threads.

The central idea is that **the implementation can be complex without making the consumer-facing API complex**. Developers can use and compose immutable data structures through a common set of simple operations, while specialized implementations handle the details of providing the data. Lazy evaluation makes it practical to work with enormous logical data sets, and carefully chosen buffering and caching can improve performance without imposing unnecessary complexity. Factories make it possible to use this model in multithreaded applications without requiring every immutable instance to be thread-safe.
