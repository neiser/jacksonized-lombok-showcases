package de.n04r.demo.jacksonizedlombokshowcases;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractJsonTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Object sut;
    private final String json;

    @Test
    void serialize() throws Exception {
        JSONAssert.assertEquals(OBJECT_MAPPER.writeValueAsString(sut), json, true);
    }

    @Test
    void deserialize() throws Exception {
        var actual = OBJECT_MAPPER.readValue(json, sut.getClass());
        assertEquals(sut, actual);
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(sut.getClass()).verify();
    }
}
