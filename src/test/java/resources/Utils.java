package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	public static RequestSpecification reqbuilder;
	public RequestSpecification requestSpecification() throws IOException {
		
		if(reqbuilder == null)
		{
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		reqbuilder = new RequestSpecBuilder().setBaseUri(getGlobalvalue("baseURL"))
				.addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(log)).
				addFilter(ResponseLoggingFilter.logResponseTo(log)).
				setContentType(ContentType.JSON) .build();
		return reqbuilder;
		}
		return reqbuilder;
	}
	
	public static String getGlobalvalue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\varun prasanth\\eclipse-workspace\\CucumberFrameworkRestAPI\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
		 
	}
	
	public String getJsonPath(Response response,String key ) {
		JsonPath jp = new JsonPath(response.asString());
		return jp.get(key).toString();
	}
	
	public void testGit() {
		System.out.println("test");
	}
}
