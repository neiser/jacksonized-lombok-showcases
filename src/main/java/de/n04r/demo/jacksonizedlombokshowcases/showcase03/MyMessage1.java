package de.n04r.demo.jacksonizedlombokshowcases.showcase03;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@SuppressWarnings("ClassCanBeRecord")
@Jacksonized
@Builder
@Value
public class MyMessage1 implements MyMessage {
    String property1;
}
