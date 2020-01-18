package ch.heigvd.amt.project_two.auth.api.spec.steps;

import ch.heigvd.amt.project_two.auth.ApiException;
import ch.heigvd.amt.project_two.auth.ApiResponse;
import ch.heigvd.amt.project_two.auth.api.DefaultApi;
import ch.heigvd.amt.project_two.auth.api.dto.Fruit;
import ch.heigvd.amt.project_two.auth.api.dto.User;
import ch.heigvd.amt.project_two.auth.api.dto.UserWithoutPassword;
import ch.heigvd.amt.project_two.auth.api.spec.helpers.Environment;
import ch.heigvd.amt.project_two.auth.api.spec.helpers.UserHelper;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class UserTest {

    private Environment environment;
    private DefaultApi api;

    User user;
    String jwt;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public UserTest(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a User server$")
    public void there_is_a_User_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a new random User payload$")
    public void i_have_a_User_payload() throws Throwable {
        user = UserHelper.getRandomUser();
    }

    @When("^I POST it to the /user endpoint")
    public void i_post_user() throws Throwable {
        try {
            lastApiResponse = api.createUserWithHttpInfo(user);
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

    @When("^I do GET on endpoint /user/email email being the user email$")
    public void i_do_GET_on_user_email_with_email_as(String email) throws Throwable {
        try {
            lastApiResponse = api.getUserWithHttpInfo(user.getEmail());
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

    @Then("^I receive the User$")
    public void i_receive_the_User() throws Throwable {
        UserWithoutPassword uwp;
        try {
            uwp = (UserWithoutPassword) lastApiResponse.getData();
        } catch (ClassCastException e) {
            throw new ApiException("The return object is not a UserWithoutPassword");
        }
        assertEquals(uwp.getEmail(), user.getEmail());
        assertEquals(uwp.getUsername(), user.getUsername());
    }

    @Then("^I receive a (\\d{3}) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, lastStatusCode);
    }

    @When("^I POST it to the /login endpoint$")
    public void i_POST_it_to_the_login_endpoint() throws Throwable {
        try {
            lastApiResponse = api.getJwtWithHttpInfo(user);
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

    @Then("^I receive a jwt token$")
    public void i_receive_a_jwt_token() throws Throwable {
        jwt = (String) lastApiResponse.getData();
    }

    @When("^I do PATCH on user/ endpoint with password(.+) as password$")
    public void i_do_PATCH_on_user_endpoint_with_password_as_password(String password) throws Throwable {
        User patchedUser = new User();
        patchedUser.setEmail(user.getEmail());
        patchedUser.setUsername(user.getUsername());
        patchedUser.setPassword(password);
        try {
            lastApiResponse = api.changePasswordWithHttpInfo(patchedUser);
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

    @When("^I set User password to password(.+)$")
    public void i_set_User_password_to_password(String password) throws Throwable {
        user.setPassword(password);
    }



}
