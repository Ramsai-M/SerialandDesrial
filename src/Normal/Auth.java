package Normal;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import Pojo.Api;
import Pojo.GetCourse;
import Pojo.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class Auth {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*System.setProperty("webdriver.chrome.driver", "C:\\Users\\this pc\\Downloads\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&flowName=GeneralOAuthFlow");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("Ramtmp27@gmail.com");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(password);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);*/
		String[] expected= {"Selenium Webdriver Java","Cypress","Protractor"};
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qjWFDG46OfC1WcYRDCBLZ5HxpoQ2MxqxWyd1V0sOi8iaj_ghnVc3AgYA8wZstC5AA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none";
		String partialcod=url.split("code=")[1];
		String code=partialcod.split("&scope")[0];
		System.out.println(code);
		
	String accessTokenResponse=	given().urlEncodingEnabled(false).queryParams("code", code)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js=new JsonPath(accessTokenResponse);
		String accessToken= js.getString("access_token");
		
		
		String response= given().queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);
		
		//if your expecting Json response then we need to add the method called defaultParser(parser.JSON)
		//we can avoid method defaultParser(parser.JSON) if our content type is application/json.
		GetCourse gc=given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		System.out.println(gc.getInstructor());//it will provide the value of instructor
		System.out.println(gc.getLinkedIn());// it will provide the value of Linkedin
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());//it will provide the value of Course title under courses->Api->first course title.
		//print the Api courses and prices
		List<Api> ApiCourses=gc.getCourses().getApi();
		for(Api ap:ApiCourses) {
			System.out.println("Course Title"+ap.getCourseTitle());
			System.out.println("Course Price"+ap.getPrice());
		}
		
		ArrayList<String> actual=new ArrayList<String>();
		List<WebAutomation> webApplicationCourses=gc.getCourses().getWebAutomation();
		for(WebAutomation wa:webApplicationCourses) {
			System.out.println(wa.getCourseTitle());
			actual.add(wa.getCourseTitle());
		}
		/*for(int k=0;k<webApplicationCourses.size();k++) {
			System.out.println(webApplicationCourses.get(k).getCourseTitle());
		}*/
		//convert the array to arrayList
		List<String> expectedList=Arrays.asList(expected);
		Assert.assertTrue(actual.equals(expectedList));
	}
}
