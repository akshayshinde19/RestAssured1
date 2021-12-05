Feature: Validating Place API's

@AddPlace @Regression
  Scenario Outline: Verify if place is being successfuly added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_id created maps to "<name>" using "GetPlaceAPI"
    
    Examples: 
    	|name | language | address |
    	|aksh | Marathi1 |address12|
#    	|aks2 | Marathi2 |address13|


@DeletePlace @Regression
Scenario: Verify if Delete place functionality is working
		Given  DeletePlace playload
		When user calls "DeletePlaceAPI" with "Post" http request
		Then the API call got success with status code 200
    And "status" in response body is "OK"
    