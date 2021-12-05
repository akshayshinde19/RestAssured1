package packageForStepDefiniation;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		StepDefination sdObj= new StepDefination();
		
		if(StepDefination.placeIdFromPostResp==null) {
		sdObj.add_Place_Payload_with("abcd", "japnease", "jane soc");
		sdObj.user_calls_with_http_request("AddPlaceAPI", "POST");		
		sdObj.verify_place_id_created_maps_to_using("abcd", "GetPlaceAPI");
		}
	}

}
