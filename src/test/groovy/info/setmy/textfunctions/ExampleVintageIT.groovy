package info.setmy.textfunctions

import org.junit.Before
import org.junit.Test


class ExampleVintageIT {

    Example example

    @Before
    void before() {
        example = new Example()
        example.firstName = "Imre"
    }

    @Test
    void test() {
        assert example.firstName == "Imre"
    }
}
