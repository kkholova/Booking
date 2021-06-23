Feature: Search for Hotel on Booking.com
  Scenario: Find Hotel by name
    Given Hotel name "Sunrise Crystal Bay Resort -Grand Select"
    When User does search
    Then Result page will contain "Sunrise Crystal Bay Resort -Grand Select" with rate "9.1"

