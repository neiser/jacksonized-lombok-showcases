package de.n04r.demo.jacksonizedlombokshowcases.showcase03;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.assertj.core.api.Assertions.assertThat;

class MyMessageTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final MyMessage1 MESSAGE_1 = new MyMessage1("value1");
    private static final String MESSAGE1_JSON = """
            {
                "type": "message1",
                "property1": "value1"
            }
            """;

    private static final String MESSAGE2_JSON = """
            {
                "type": "message2",
                "property2": "value2"
            }
            """;
    private static final MyMessage2 MESSAGE_2 = new MyMessage2("value2");

    @Test
    void polymorphicDeserialization() throws Exception {
        var message1 = new ObjectMapper().readValue("""
                {
                    "type": "message1",
                    "property1": "value1"
                }
                """, MyMessage.class);
        assertThat(message1).isEqualTo(new MyMessage1("value1"));

        var message2 = OBJECT_MAPPER.readValue(MESSAGE2_JSON, MyMessage.class);
        assertThat(message2).isEqualTo(MESSAGE_2);
    }

    @Test
    void polymorphicSerialization() throws Exception {
        JSONAssert.assertEquals(MESSAGE1_JSON, OBJECT_MAPPER.writeValueAsString(MESSAGE_1), true);
        JSONAssert.assertEquals(MESSAGE2_JSON, OBJECT_MAPPER.writeValueAsString(MESSAGE_2), true);
    }
}