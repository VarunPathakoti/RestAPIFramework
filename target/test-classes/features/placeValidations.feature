Feature: Validating place API

@Addplace
Scenario Outline: Verify if we are able to add place successfully using addPlace API

Given Add Place payload with "<name>" "<language>" "<address>"
When user calls "addPplaceAPI" with "POST" http request
Then API call got success with 200 as status code
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify placeId created maps to "<name>" using "getPlaceAPI"

Examples:
| name        | language     |  address     |

|Mahesh       |  Telugu      |  test address|
#|pavan         | Hindi      |  testing          |

Scenario: Verify if delete functionality is working fine
Given Delete place payload
When user calls "deletePlaceAPI" with "POST" http request
Then API call got success with 200 as status code
And "status" in response body is "OK"