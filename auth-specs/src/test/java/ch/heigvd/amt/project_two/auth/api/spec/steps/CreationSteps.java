package ch.heigvd.amt.project_two.auth.api.spec.steps;

import ch.heigvd.amt.project_two.auth.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ch.heigvd.amt.project_two.auth.ApiException;
import ch.heigvd.amt.project_two.auth.ApiResponse;
import ch.heigvd.amt.project_two.auth.api.DefaultApi;
import ch.heigvd.amt.project_two.auth.api.dto.Fruit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    Fruit fruit;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a Fruits server$")
    public void there_is_a_Fruits_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a fruit payload$")
    public void i_have_a_fruit_payload() throws Throwable {
        fruit = new ch.heigvd.amt.project_two.auth.api.dto.Fruit();
    }

    @When("^I POST it to the /fruits endpoint$")
    public void i_POST_it_to_the_fruits_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createFruitWithHttpInfo(fruit);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(201, lastStatusCode);
    }

}
