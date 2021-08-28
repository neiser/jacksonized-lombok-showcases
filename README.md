# Showcases for Jackson & Lombok

This project highlights showcases how to use Jackson and Lombok to create nice Plain Old Java Objects (POJOs) or records
in Java 16, which are immutable and easily serializable and deserializable. We also cover some more advanced topics, such as the backwards-compatible
extension of JSON formats. All showcases have some completely spelled out example code and tests, which are linked in the beginning of each showcase.

## TL;DR

Just use `@Jacksonized` together with `@Builder` or `@SuperBuilder`
and forget about all other alternatives how to define immutable POJOs for Jackson.

## Showcase 1: A simple example

[Code](src/main/java/de/n04r/demo/jacksonizedlombokshowcases/showcase01) and [Test](src/test/java/de/n04r/demo/jacksonizedlombokshowcases/showcase01)

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

[Code](src/main/java/de/n04r/demo/jacksonizedlombokshowcases/showcase02) and [Test](src/test/java/de/n04r/demo/jacksonizedlombokshowcases/showcase02)

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

[Code](src/main/java/de/n04r/demo/jacksonizedlombokshowcases/showcase03) and [Test](src/test/java/de/n04r/demo/jacksonizedlombokshowcases/showcase03)

Depending on properties of the to-be-deserialized JSON, Jackson is able to determine the target POJO class. Let's
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

## Showcase 4: Using abstract base classes for shared properties

[Code](src/main/java/de/n04r/demo/jacksonizedlombokshowcases/showcase04) and [Test](src/test/java/de/n04r/demo/jacksonizedlombokshowcases/showcase04) 

Suppose some or all of your messages share a common property, such as a message `id` exchanged between the sender and the receiver of such messages.

The easiest way would be to introduce an abstract class `MyAbstractIdMessage`
which is extended by any message having the `id` property. However, this
usually forces you to implement a constructor in all base classes. This can
be elegantly avoided using Lombok's `@SuperBuilder`. Also, `@ToString` and
`@EqualsAndHashCode` support this inheritance via the `callSuper = true` flag.

For example, the message structure could now look like this:

```java
@SuperBuilder
@Getter
@EqualsAndHashCode
@ToString
public abstract class MyAbstractIdMessage implements MyMessage {
    private final String id;
}
```

```java
@Jacksonized
@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MyMessage1 extends MyAbstractIdMessage {
    String property1;
}
```

```java
@Jacksonized
@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MyMessage2 extends MyAbstractIdMessage {
    String property2;
}
```

where the corresponding JSONs look like

```json
 {
  "type": "message1",
  "id": "id",
  "property1": "value1"
}
```

```json
{
  "type": "message2",
  "id": "id",
  "property2": "value2"
}
```

Within the [Test](src/test/java/de/n04r/demo/jacksonizedlombokshowcases/showcase04/MyMessageTest.java), you'll find a pretty handy method to fill common properties into any builder:
```java
private static <B extends MyAbstractIdMessageBuilder<?, ? extends B>> B fillId(B builder) {
    return (B) builder.id("id");
}
```
Of course, you wouldn't always use the same value `"id"` in real use cases, but it's quite nice to see how Lombok's `@SuperBuilder` can be flexibly used and save quite some boilerplate code. The only open question, and there my knowledge about Java generics ends, is why there's an explicit cast to `(B)` necessary for the return value and if there's a more elegant formulation for the above method. If you happen to know this, please [reach out to me](mailto:andreas.grub@qaware.de). 

