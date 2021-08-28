package de.n04r.demo.jacksonizedlombokshowcases.showcase04;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode
@ToString
public abstract class MyAbstractIdMessage implements MyMessage {
    private final String id;
}
