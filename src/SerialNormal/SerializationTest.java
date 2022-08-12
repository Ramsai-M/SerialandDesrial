package SerialNormal;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import PojoSerial.AddPlace;
import PojoSerial.Location;

public class SerializationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
		AddPlace ap=new AddPlace();
		ap.setAccuracy(45);
		ap.setAddress("99, Front layout, Lojen 09");
		ap.setLanguage("French-IN");
		ap.setPhone_number("(+91) 143 893 3937");
		ap.setWebsite("http://facebook.com");
		ap.setName("Backsideline house");
		List<String> myList=new ArrayList<String>();
		myList.add("Gold_park");
		myList.add("shop");
		ap.setTypes(myList);
		Location loc=new Location();
		loc.setLat(-98.383494);
		loc.setLng(64.427362);
		ap.setLocation(loc);
		
		Response res= given().queryParam("key", "qaclick123").body(ap)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		String ResponseString=res.asString();
		System.out.println(ResponseString);
		
		//Get Response
		JsonPath js=new JsonPath(ResponseString);
		String placeId=js.getString("place_id");
		String GetPlaceResponse=   given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();
		

	}

}
