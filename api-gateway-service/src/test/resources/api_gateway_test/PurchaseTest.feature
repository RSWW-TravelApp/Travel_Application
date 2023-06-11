Feature: Make a purchase of an offer
  Scenario: Reserve Success and Purchase Success
    Given User enter departure and destination country
    When User choose flight
    And User enter Room type and Stars
    And User choose offer
    And User Reserve the Offer
    Then User successfully Purchase the Offer

  Scenario: Reserve Success and Purchase Failed
    Given User enter departure and destination country
    When User choose flight
    And User enter Room type and Stars
    And User choose offer
    And User Reserve the Offer
    Then User Purchase the Offer after 1 min and fail

  Scenario: Purchase Random
    Given User enter departure and destination country
    When User choose flight
    And User enter Room type and Stars
    And User choose offer
    Then User tries to Purchase the Offer

  Scenario: Purchase Success
    Given User enter departure and destination country
    When User choose flight
    And User enter Room type and Stars
    And User choose offer
    Then User successfully Purchase the Offer

  Scenario: Purchase Failed
    Given User enter departure and destination country
    When User choose flight
    And User enter Room type and Stars
    And User choose offer
    Then User failed to Purchase the Offer





