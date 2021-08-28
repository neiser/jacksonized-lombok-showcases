package de.n04r.demo.jacksonizedlombokshowcases.showcase04;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.n04r.demo.jacksonizedlombokshowcases.showcase04.MyAbstractIdMessage.MyAbstractIdMessageBuilder;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.assertj.core.api.Assertions.assertThat;

class MyMessageTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final MyMessage1 MESSAGE_1 = fillId(MyMessage1.builder())
            .property1("value1")
            .build();

    private static final MyMessage2 MESSAGE_2 = fillId(MyMessage2.builder())
            .property2("value2")
            .build();

    private static <B extends MyAbstractIdMessageBuilder<?, ? extends B>> B fillId(B builder) {
        // why is there an explicit cast necessary to (B)?
        return (B) builder.id("id");
    }

    private static final String MESSAGE1_JSON = """
            {
                "type": "message1",
                "id": "id",
                "property1": "value1"
            }
            """;

    private static final String MESSAGE2_JSON = """
            {
                "type": "message2",
                "id": "id",
                "property2": "value2"
            }
            """;

    @Test
    void polymorphicDeserialization() throws Exception {
        var message1 = OBJECT_MAPPER.readValue(MESSAGE1_JSON, MyMessage.class);
        assertThat(message1).isEqualTo(MESSAGE_1);

        var message2 = OBJECT_MAPPER.readValue(MESSAGE2_JSON, MyMessage.class);
        assertThat(message2).isEqualTo(MESSAGE_2);
    }

    @Test
    void polymorphicSerialization() throws Exception {
        JSONAssert.assertEquals(MESSAGE1_JSON, OBJECT_MAPPER.writeValueAsString(MESSAGE_1), true);
        JSONAssert.assertEquals(MESSAGE2_JSON, OBJECT_MAPPER.writeValueAsString(MESSAGE_2), true);
    }
}