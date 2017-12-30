Feature: Login

  Scenario: valid login
    Given I login with user "aaa" and password "bbb"
    Then the text on elemenet "dsdasd" should be "aaa"