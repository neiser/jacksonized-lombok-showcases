# Showcases for Jackson & Lombok

This project highlights showcases how to use Jackson and Lombok to create nice
Java DTOs (records in Java 16) which are immutable and easily serializable and
deserializable. We also cover backwards-compatible extensions of JSON formats.

## TL;DR

Just use `@Jacksonized` `@Builder` or `@SuperBuilder`.

## Showcase 1: A simple example

Let's start off very simply, if you're used to Lombok, you should write classes to be ser/des'ed by Jackson as follows:

```java

@Jacksonized
@Builder
// Don't use @Value to show components more explicitly
@Getter
@EqualsAndHashCode
@ToString
public final class MyData {
    private final String property1;
}
```

It even works with Java 16 records:

```java

@Jacksonized
@Builder
public record MyDataRecord(String property1) {
}
```