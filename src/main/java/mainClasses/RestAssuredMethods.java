package mainClasses;

import java.util.Map;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredMethods {
	
	public Response getResponseFromUrl(String url)
	{
		return RestAssured.get(url);
	}
	
	public Response postAPICall(Map<String, String> testData)
	{
		JSONObject payload = new JSONObject();
		for(int i = 1;i<=Integer.parseInt(testData.get("paraSize"));i++)
		{
			payload.put(testData.get("Key"+i),testData.get("Value"+i));
		}
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(payload.toString());
		return request.post(testData.get("URL"));
	}

}
