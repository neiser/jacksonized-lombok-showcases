# Showcases for Jackson & Lombok

This project highlights showcases how to use Jackson and Lombok to create nice Plain Old Java Objects (POJOs) or records
in Java 16, which are immutable and easily serializable and deserializable. We also cover backwards-compatible
extensions of JSON formats.

## TL;DR

Just use `@Jacksonized` together with `@Builder` or `@SuperBuilder`
and forget about all other alternatives how to define

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

As a side note, making Jackson using a builder for deserialization also avoids passing a `-parameter` flag to `javac` in
order have parameter names available on constructors via reflection.

## Showcase 2: Support deprecated properties

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

If you want to support a deprecated property on serialization, then simply tag it
with `@JsonProperty(access = JsonProperty.Access.READ_ONLY)`.

## Showcase 3: Introduction to Jackson polymorphism

Depending on various properties of the to-be-deserialized JSON, Jackson is able to determine the target class. Let's
suppose you have two types of messages, `message1` and `message2`, and their JSONs look like

```json
{
  "type": "message1",
  "property1": "value1"
}
```

```json
{
  "type": "message2",
  "property2": "value2"
}
```

Note that both JSONs only share the field `type` which will be used by Jackson. The corresponding Java POJOs can now be
defined with the help of a commonly shared base interface `MyMessage` and some Jackson annotations

```java

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MyMessage1.class, name = "message1"),
        @JsonSubTypes.Type(value = MyMessage2.class, name = "message2"),
})
public interface MyMessage {
}
```

where each concrete message implements the above interface:

```java

@Jacksonized
@Builder
@Value
public class MyMessage1 implements MyMessage {
    String property1;
}
```

```java

@Jacksonized
@Builder
@Value
public class MyMessage2 implements MyMessage {
    String property2;
}
```

Then Jackson is able to serialize from the base interface and still return the concrete type, for example

```java
var message1 = new ObjectMapper().readValue("""
        {
            "type": "message1",
            "property1": "value1"
        }
        """, MyMessage.class);
assertThat(message1).isEqualTo(new MyMessage1("value1"));
```
