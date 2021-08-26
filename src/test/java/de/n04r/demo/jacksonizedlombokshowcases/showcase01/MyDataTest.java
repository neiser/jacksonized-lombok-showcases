package de.n04r.demo.jacksonizedlombokshowcases.showcase01;

import de.n04r.demo.jacksonizedlombokshowcases.AbstractJsonTest;

class MyDataTest extends AbstractJsonTest {
    MyDataTest() {
        super(
                MyData.builder().property1("value1").build(),
                """
                        {"property1":"value1"}
                        """
        );
    }
}