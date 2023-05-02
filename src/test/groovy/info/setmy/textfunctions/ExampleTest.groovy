package info.setmy.textfunctions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExampleTest {

    Example example

    @BeforeEach
    void before() {
        example = new Example()
        example.firstName = "Imre"
    }

    @Test
    void test() {
        assert example.firstName == "Imre"
    }
}
