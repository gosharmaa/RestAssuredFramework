package mainClasses;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Map;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonParsing {
	
	public Map<String,String> readJsonFile(String fileName, String methodName) throws FileNotFoundException {
		JsonPath jsPath = new JsonPath(new FileReader("D:\\workspace\\RestAssuredFramework\\src\\test\\resources\\"+fileName+".json"));
		return jsPath.get(methodName);
		}
	
	public JsonPath getJsonPathFromResponse(Response rs){
		return new JsonPath(rs.asString());
	}
	
}
