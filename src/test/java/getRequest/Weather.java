package getRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mainClasses.JsonParsing;
import mainClasses.RestAssuredMethods;

public class Weather {
	public Map<String, String> testData;
	public Map<String, String> fetchData;
	RestAssuredMethods restAssuredMethods;
	JsonParsing jsonParsing;

	@BeforeClass
	public void setUp() {
		jsonParsing = new JsonParsing();
		restAssuredMethods = new RestAssuredMethods();
	}

//	@Test
	public void testResponseCode() throws FileNotFoundException, IOException {
		ArrayList<Object> actualData = new ArrayList<Object>();
		ArrayList<Object> expectedData = new ArrayList<Object>();
		testData = jsonParsing.readJsonFile("Data", "testResponseCode");
		actualData.addAll(Arrays.asList(testData.get("employee_name").split(",")));
		System.out.println(testData.get("URL"));
		Response rs = restAssuredMethods.getResponseFromUrl(testData.get("URL"));
		JsonPath jsnew = jsonParsing.getJsonPathFromResponse(rs);
		for (int i = 0; i < jsnew.getInt("data.size()"); i++) {
			expectedData.add(jsnew.get("data.employee_name[" + i + "]"));
		}
		System.out.println(expectedData);
		Assert.assertEquals(actualData, expectedData);
	}

	@SuppressWarnings("unchecked")
//	@Test
	public void createEmployeeRecord() throws FileNotFoundException {
		ArrayList<Object> actualData = new ArrayList<Object>();
		ArrayList<Object> expectedData = new ArrayList<Object>();
		testData = jsonParsing.readJsonFile("Data", "createEmployeeRecord");
		System.out.println(testData);
		RequestSpecification request = RestAssured.given();

		// Preparing POST call
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", testData.get("employee_name"));
		requestParams.put("salary", testData.get("employee_salary"));
		requestParams.put("age", testData.get("employee_age"));

		// Header
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		Response rs = request.post(testData.get("URL"));
		System.out.println(rs.asString());
		System.out.println(jsonParsing.getJsonPathFromResponse(rs));
		actualData.add(rs.getStatusCode());
		expectedData.add(200);
		Assert.assertEquals(actualData, expectedData);
	}

//	@Test
	public void create(){
		
		RequestSpecification request = RestAssured.given();
		JSONObject reqPara = new JSONObject();
		reqPara.put("name","Govinda");
		
		request.header("Content-Type", "application/json");
		request.body(reqPara.toJSONString());
		Response rs = request.post("https://my-json-server.typicode.com/gosharmaa/restAPIs");
		System.out.println(rs);
	}
	
	@Test
	public void getEmpDetails()
	{
		ArrayList<Object> actualData = new ArrayList<Object>();
		ArrayList<Object> expectedData = new ArrayList<Object>();
		
		Response rs = restAssuredMethods.getResponseFromUrl("http://localhost:3000/employees/");
		System.out.println(rs.asString());
		JsonPath jsnew = jsonParsing.getJsonPathFromResponse(rs);
		System.out.println(jsnew.get("id[0]"));
		System.out.println(jsnew.toString());
		
	}
	
}
