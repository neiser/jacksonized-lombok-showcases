package de.n04r.demo.jacksonizedlombokshowcases.showcase02;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.n04r.demo.jacksonizedlombokshowcases.AbstractJsonTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyDataTest extends AbstractJsonTest {

    MyDataTest() {
        super(MyData.builder().property1("value1").build(), """
                {
                    "property1":"value1"
                }
                """);
    }

    @Test
    void deserializeFromDeprecatedJson() throws Exception {
        var actual = OBJECT_MAPPER.readValue("""
                {
                    "deprecatedProperty1":"value1"
                }
                """, sut.getClass());
        assertThat(sut).isEqualTo(actual);
    }
}