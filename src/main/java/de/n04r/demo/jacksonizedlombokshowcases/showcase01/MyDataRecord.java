package de.n04r.demo.jacksonizedlombokshowcases.showcase01;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

public record MyDataRecord(String property1) {
    @SuppressWarnings("java:S6207") // suppress "redundant constructor"
    @Jacksonized
    @Builder
    public MyDataRecord {
        // empty for lombok
    }
}
