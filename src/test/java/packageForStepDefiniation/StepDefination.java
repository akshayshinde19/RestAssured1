package packageForStepDefiniation;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoClasses.AddPlace;
import pojoClasses.Location;
import resources.APIResources;
import resources.Utils;
import resources.testDataBuild;

public class StepDefination extends Utils {

	RequestSpecification reqspec;
	ResponseSpecification resspec;
	Response response;
	testDataBuild reqPayload = new testDataBuild();
	static String placeIdFromPostResp;

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {
		System.out.println("APP/n");
		reqspec = given().spec(requestSpecification()).body(reqPayload.addPlacePayload(name, language, address));

	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String string, String string2) {
		System.out.println("ucwpr");

		APIResources arObj = APIResources.valueOf(string); // enum

		System.out.println(arObj.getResource());

		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (string2.equalsIgnoreCase("POST")) {
			response = reqspec.when().post(arObj.getResource());
		}
		if (string2.equalsIgnoreCase("GET")) {
			response = reqspec.when().get(arObj.getResource());
		}

		// String responseString = response.asString();
		// System.out.println(responseString);
	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {
		System.out.println("tAcgs");

		assertEquals(response.getStatusCode(), 200);
	}

	@And("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
		System.out.println("iRbi = " + keyValue);

		// assertEquals(response.getBody().jsonPath().getString(keyValue),ExpectedValue);

		// System.out.println(response.getBody().jsonPath().getString(keyValue)); this
		// is also one way to extarct value instaed of converting response as string

		// String respAsStr=response.asString();
		// JsonPath js=new JsonPath(respAsStr);
		assertEquals(getJSONpath(response, keyValue), ExpectedValue);
	}

	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String Expectedname, String string2) throws IOException {
		System.out.println("vpcmtu" + Expectedname);

		placeIdFromPostResp = getJSONpath(response, "place_id");
		System.out.println("placeId from POST API " + placeIdFromPostResp);

		reqspec = given().spec(requestSpecification()).queryParam("place_id", placeIdFromPostResp);

		APIResources arObj = APIResources.valueOf(string2); // enum

		response = reqspec.when().get(arObj.getResource());

		//String responseString = response.asString();
		// System.out.println("Get API response == "+responseString);

		in_response_body_is("name", Expectedname);

	}

	@Given("DeletePlace playload")
	public void deleteplace_playload() throws IOException {
		System.out.println("deleteplace_playload\n");

		reqspec = given().spec(requestSpecification()).body(reqPayload.deletePlacePayload(placeIdFromPostResp));

	}
}
