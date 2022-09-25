@sample_annotation
Feature: Sample Feature

  Background: Sample General Preconditions Explanation
    Given There is some predefined pet types like "dog"

  Scenario: save pet scenario
    Given There is a pet owner called "Amu Gholam"
    When He performs save pet service to add a pet to his list
    Then The pet is saved successfully
    And The pet is saved for owner successfully

  Scenario: find owner scenario
    Given There is a owner called "Amu Gholi"
    When He performs find owner of 2
    Then The owner is found successfully

  Scenario: new pet scenario
    Given There is a pet owner called "Amu Gholam"
    When He performs new pet
    Then The new pet is added successfully

  Scenario: find pet scenario
    Given There is a pet
    When He performs find pet 1
    Then The pet is found successfully
