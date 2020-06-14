package getRequest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mainClasses.JsonParsing;
import mainClasses.RestAssuredMethods;

public class myApis {
	RestAssuredMethods restAssuredMethods;
	JsonParsing jsonParsing;
	public Map<String, String> testData;
	Boolean newRecord = false;

	@BeforeClass
	public void setUp() {
		jsonParsing = new JsonParsing();
		restAssuredMethods = new RestAssuredMethods();
	}
	
	@Test(priority=0)
	public void getEmpDetails() throws FileNotFoundException
	{
	newRecord = false;
		ArrayList<Object> actualData = new ArrayList<Object>();
		ArrayList<Object> expectedData = new ArrayList<Object>();
		testData = jsonParsing.readJsonFile("Infobeans", "getEmpDetails");

		Response rs = restAssuredMethods.getResponseFromUrl("http://localhost:3000/employees/");
		JsonPath jsnew = jsonParsing.getJsonPathFromResponse(rs);
		
		actualData.add(jsnew.get("id[2]").toString());
		expectedData.add(testData.get("id"));
		
		actualData.add(jsnew.get("first_name[2]"));
		expectedData.add(testData.get("FirstName"));
		
		actualData.add(rs.getStatusCode());
		expectedData.add(200);
		System.out.println("testCase-1\n"+actualData+"\n"+expectedData);
		Assert.assertEquals(actualData, expectedData);
	}
	
	@Test(priority=1)
	public void addEmployee() throws FileNotFoundException
	{
	newRecord = false;
		ArrayList<Object> actualData = new ArrayList<Object>();
		ArrayList<Object> expectedData = new ArrayList<Object>();
		testData = jsonParsing.readJsonFile("Infobeans", "addEmployee");
		
		Response rs = restAssuredMethods.postAPICall(testData);

		int statusCode = rs.getStatusCode();
		JsonPath jsnew = jsonParsing.getJsonPathFromResponse(rs);
		expectedData.add(201);
		actualData.add(statusCode);
		if(statusCode==201)newRecord = true;
		actualData.add(jsnew.get("first_name"));
		expectedData.add(testData.get("Value2"));
		System.out.println(actualData+"\n"+expectedData+"\n"+newRecord);
		Assert.assertEquals(actualData, expectedData);
	}
	
	@AfterMethod
	public void cleanUp(){
		if(newRecord.equals(true)){
			System.out.println("id to delete->"+testData.get("Value1"));
		Response rs = RestAssured.delete("http://localhost:3000/employees/"+testData.get("Value1"));	
		System.out.println(rs.statusCode());
		}
	}
	
}
