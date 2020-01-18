Feature: Creation of users

  Background:
    Given there is a User server

  Scenario: creating a new User is allowed
    Given I have a new random User payload
    When I POST it to the /user endpoint
    Then I receive a 201 status code

  Scenario: creating a new User fails if User already exists
    Given I have a new random User payload
    When I POST it to the /user endpoint
    Then I receive a 201 status code
    When I POST it to the /user endpoint
    Then I receive a 409 status code

  Scenario: test login works
    Given I have a new random User payload
    When I POST it to the /user endpoint
    Then I receive a 201 status code
    When I POST it to the /login endpoint
    Then I receive a jwt token
    When I do GET on endpoint /user/email (email being the User email)
    Then I receive a 200 status code
    And I receive the User

  Scenario: test login fails with impersonators
    Given I have a new random User payload
    When I POST it to the /login endpoint
    Then I receive a 404 status code
    And I do not receive a jwt token

  Scenario: test PATCH on user/ endpoint works
    Given I have a new random User payload
    When I POST it to the /user endpoint
    And I POST it to the /login endpoint
    And I do PATCH on user/ endpoint with password1234 as password
    And I do GET on endpoint /user/email (email being the User email)
    Then I receive a 404 status code
    When I set User password to password1234
    And I do GET on endpoint /user/email (email being the User email)
    Then I receive a 200 status code