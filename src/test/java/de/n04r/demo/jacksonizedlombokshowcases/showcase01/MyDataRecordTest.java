package de.n04r.demo.jacksonizedlombokshowcases.showcase01;

import de.n04r.demo.jacksonizedlombokshowcases.AbstractJsonTest;

class MyDataRecordTest extends AbstractJsonTest {

    MyDataRecordTest() {
        super(
                new MyDataRecord("value1"),
                """
                        {"property1":"value1"}
                        """);
    }
}