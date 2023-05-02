package info.setmy.textfunctions

import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class ExampleCukesDefinitions {

    int numberA
    int numberB
    int result

    @Before
    void before() {
        println "Before scenario"
        this.numberA = 0
        this.numberB = 0
        this.result = 0
    }

    @After
    void after() {
        println "After scenario"
    }

    @Given("^two numbers (\\d+) and (\\d+)")
    //String: "^\"([^\"]*)\""
    void "two numbers a and b"(int a, int b) {
        println "givern numbers ${a} ${b}"
        this.numberA = a
        this.numberB = b
    }

    @When("^adding them")
    void "adding them"() {
        result = numberA + numberB
        println "adding ${numberA} and ${numberB} = ${result}"
    }

    @Then("^them summary should be (\\d+)")
    void "them summary should be b"(int c) {
        println "comparing numbers summary ${result} with expected ${c}"
        assert result == c
    }
}
