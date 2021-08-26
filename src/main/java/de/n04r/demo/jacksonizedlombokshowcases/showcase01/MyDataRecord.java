package de.n04r.demo.jacksonizedlombokshowcases.showcase01;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
public record MyDataRecord(String property1) {
}
