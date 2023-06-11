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

  Scenario: Reserve Success and Purchase Success and try Purchase again
    Given User enter departure and destination country
    When User choose flight
    And User enter Room type and Stars
    And User choose offer
    And User Reserve the Offer
    Then User successfully Purchase the Offer
    Then User tries to Purchase the Offer again

  Scenario: Purchase Failed because of lack of available seats in a plane
    Given User enter departure and destination country
    When User choose specific flight
    And User enter Adults number
    And User choose offer
    Then User failed to Purchase the Offer because of lack of available seats in a plane



