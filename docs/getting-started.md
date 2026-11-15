# Getting Started with TFW

TFW is a Java library. It is not a standalone application or service that must be installed and started independently. Applications use TFW by adding it as a dependency and incorporating its APIs into their own code.

## Requirements

- Java 8 or later.
- Maven, if you use Maven to manage your project dependencies.

## Installing TFW

TFW is published to Maven Central. To use it in a Maven project, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.tfw-org</groupId>
    <artifactId>tfw</artifactId>
    <version>YOUR_TFW_VERSION</version>
</dependency>
```

Replace `YOUR_TFW_VERSION` with a released version listed on [Maven Central](https://central.sonatype.com/artifact/io.github.tfw-org/tfw).

Other Java build systems can use the same Maven Central artifact.

## Running a TFW application

The [HelloWorld example](../src/main/java/tfw/tsm/demo/HelloWorld.java) included in the TFW repository demonstrates a minimal TSM application.

To run the example:

1. Clone the TFW repository.
2. Import the project into a Java IDE as a Maven project.
3. Locate `tfw.tsm.demo.HelloWorld`.
4. Run its `main` method as a Java application.

The example demonstrates the basic steps involved in creating and running a TSM application:

1. Create a transaction queue.
2. Define the event channels used by the application.
3. Create the components that initiate and process work.
4. Construct a `Root` to establish the application hierarchy and transaction manager.
5. Add the components to the root.
6. Trigger the initial work and wait for transaction processing to complete.

The example uses SLF4J for logging. TFW provides the logging API but does not select a logging implementation, so an application should include an appropriate SLF4J provider to see the example's log output.

## Learning more

TFW provides two principal packages:

- [Immutable Data](immutable.md) explains immutable arrays, matrices, streams, composition, lazy evaluation, and resource management.
- [Transaction and State Management](tsm.md) explains components, event channels, transactions, processing cycles, rollback, commits, and application composition.

The [Javadocs](https://tfw-org.github.io/tfw/javadoc/latest/) provide detailed API reference documentation.

## Security considerations

TFW provides data structures and a component-based state-management framework. It does not attempt to provide application-level security mechanisms such as authentication, authorization, access control, or input sanitization. Applications must implement the security controls appropriate to their requirements.

When developing components or other extensions that process TFW event-channel values, developers should observe the following practices:

- **Validate input:** Treat values originating outside the application's trusted boundary as untrusted. Validate their content and enforce application-specific constraints before using them.
- **Check assumptions:** Converters, synchronizers, validators, event-channel predicates, and other components should not assume that values are valid merely because they are carried by a typed event channel.
- **Protect sensitive information:** Avoid exposing credentials, private data, or other sensitive values through log messages, exception messages, or diagnostic output.
- **Control resource consumption:** Consider the size and complexity of values being processed, particularly when they originate from untrusted sources. Avoid allowing input values to cause unbounded memory allocation or excessive processing.
- **Control external side effects:** Use commit processing for operations that should occur after transaction processing completes. Ensure that external operations do not inadvertently expose or modify protected resources.

The immutable interfaces do not automatically guarantee thread safety. Applications should follow the thread-safety and ownership requirements documented for the particular implementations they use. In particular, when ownership of an underlying array is transferred to an immutable implementation, the caller must not subsequently modify or access that array.

For information about reporting security vulnerabilities in TFW, see the [Security Policy](../SECURITY.md).
