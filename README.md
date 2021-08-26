# Showcases for Jackson & Lombok

This project highlights showcases how to use Jackson and Lombok to create nice Java DTOs (records in Java 16) which are
immutable and easily serializable and deserializable. We also cover backwards-compatible extensions of JSON formats.

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

It even works with Java 16 records, unfortunately a somewhat redundant empty compact constructor is needed:

```java

public record MyDataRecord(String property1) {
    @Jacksonized
    @Builder
    public MyDataRecord {
        // empty for lombok
    }
}
```

## Showcase 2: Support deprecated property on deserialization

If you want to rename an existing property but still need to support "old" JSONs on deserialization, you can use
Lombok's feature that Builders can be adjusted as follows:

```java

@Jacksonized
@Builder
@Value
public class MyData {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String deprecatedProperty1;
    String property1;

    public static class MyDataBuilder {
        MyDataBuilder deprecatedProperty1(String value) {
            // one could do more conversions 
            return property1(value);
        }
    }
}
```