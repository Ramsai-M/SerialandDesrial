package SpecBuilderPack;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import PojoSerial.AddPlace;
import PojoSerial.Location;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
		AddPlace ap=new AddPlace();
		ap.setAccuracy(86);
		ap.setAddress("94, middle layout, Lojlo 59");
		ap.setLanguage("French-IN");
		ap.setPhone_number("(+91) 443 893 3937");
		ap.setWebsite("http://Instagram.com");
		ap.setName("Rightsideline house");
		List<String> myList=new ArrayList<String>();
		myList.add("Biscuit_park");
		myList.add("shop");
		ap.setTypes(myList);
		Location loc=new Location();
		loc.setLat(-68.383884);
		loc.setLng(24.427982);
		ap.setLocation(loc);
		
		RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification ResSpec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification res= given().spec(req).body(ap);
		
		Response response= res.when().post("/maps/api/place/add/json")
		.then().spec(ResSpec).extract().response();
		String ResponseString=response.asString();
		System.out.println(ResponseString);
		
		//Get Response
		JsonPath js=new JsonPath(ResponseString);
		String placeId=js.getString("place_id");
		String GetPlaceResponse=   given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();
		

	}

}
