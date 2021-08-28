package de.n04r.demo.jacksonizedlombokshowcases.showcase03;


import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@SuppressWarnings("ClassCanBeRecord")
@Jacksonized
@Builder
@Value
public class MyMessage2 implements MyMessage {
    String property2;
}
