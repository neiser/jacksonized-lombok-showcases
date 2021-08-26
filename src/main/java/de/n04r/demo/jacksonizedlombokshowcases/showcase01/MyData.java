package de.n04r.demo.jacksonizedlombokshowcases.showcase01;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@SuppressWarnings("ClassCanBeRecord")
@Jacksonized
@Builder
@Getter
@EqualsAndHashCode
@ToString
public final class MyData {
    private final String property1;
}
