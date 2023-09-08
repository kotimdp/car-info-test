Feature: This is to verify the Car Valuation information

  Scenario Outline: Verify Car Valuation Information
    Given User reading input file "<input_file>" to extract vehicle registration numbers
    When User Navigate to CarValuation Page "value-my-car"
    And perform free car valuation check to get the details
    Then compare the vehicle details returned from website with output file "<output_file>"
    Examples:
      |input_file    | output_file    |
      |car_input.txt | car_output.txt |