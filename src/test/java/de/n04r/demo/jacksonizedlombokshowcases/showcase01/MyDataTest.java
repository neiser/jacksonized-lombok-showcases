package de.n04r.demo.jacksonizedlombokshowcases.showcase01;

import de.n04r.demo.jacksonizedlombokshowcases.AbstractJsonTest;

class MyDataTest extends AbstractJsonTest {
    MyDataTest() {
        super(
                new MyData("value1"),
                """
                        {"property1":"value1"}
                        """
        );
    }
}