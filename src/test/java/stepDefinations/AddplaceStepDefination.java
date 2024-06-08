package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlacePojo;
import pojo.LocationPojo;
import resources.APIResources;
import resources.Testdata;
import resources.Utils;

public class AddplaceStepDefination extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response res1;
	Testdata data = new Testdata();
	static String placeid;
	
	
	@Given("Add Place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String lang, String address) throws IOException {
		resspec = new ResponseSpecBuilder().expectStatusCode(200).build();
		res = given().spec(requestSpecification()).body(data.addplaceData(name,lang,address));

	}
	
	
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		APIResources endPoint = APIResources.valueOf(resource);
		System.out.println(endPoint.getResource());
		if(method.equalsIgnoreCase("POST")) {
			res1 = res.when().post(endPoint.getResource());
		}
		
		else if (method.equalsIgnoreCase("GET")) {
			res1 = res.when().get(endPoint.getResource());
		}
		/*
		 * res1 =
		 * res.when().post(endPoint.getResource()).then().spec(resspec).extract().
		 * response(); System.out.println(res1.asString());
		 */
	}

	@Then("API call got success with {int} as status code")
	public void api_call_got_success_with_as_status_code(Integer int1) {
		Assert.assertEquals(res1.getStatusCode(), 200);

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
		String str = res1.asString();
		JsonPath jp = new JsonPath(str);
		Assert.assertEquals(jp.getString(key), value);
		/*
		 * System.out.println(jp.getString(key)); System.out.println(str);
		 */
	}
	
	@Then("verify placeId created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		placeid = getJsonPath(res1,"place_id");
		res = given().spec(requestSpecification()).queryParam("place_id" , placeid);
		user_calls_with_http_request( resource, "GET");
		String actualName = getJsonPath(res1, "name");
		System.out.println(actualName);
		Assert.assertEquals(expectedName, actualName);
	}
	@Given("Delete place payload")
	public void delete_place_payload() throws IOException {
		
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(placeid));
	}

}
