package de.n04r.demo.jacksonizedlombokshowcases.showcase04;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MyMessage1.class, name = "message1"),
        @JsonSubTypes.Type(value = MyMessage2.class, name = "message2"),
})
public interface MyMessage {
}
