package de.n04r.demo.jacksonizedlombokshowcases.showcase02;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@SuppressWarnings("ClassCanBeRecord")
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
