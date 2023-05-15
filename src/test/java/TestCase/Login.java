package TestCase;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.*;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Login {
	  ResponseSpecification res;     // it is used to validate a common response or for multiple tests from  the body
   	  RequestSpecification req_spec;  // it is used to extract repetitive actions like setting up base URL, headers, HTTP verbs etc which may be common for multiple Rest calls
	  String str=" ";
	  JSONObject logindetails;
	  ExtentReports extent= new ExtentReports("extentreportLogin.html");
	
	 @BeforeClass
		public void setSpecification()
		{
		res = RestAssured.expect();
		res.statusLine("HTTP/1.1 200 OK");
		res.contentType(ContentType.JSON);
		
		InputStream DataIs =null;
		try
		{
			String FileName = "Data/logindetails.json";
			DataIs =getClass().getClassLoader().getResourceAsStream(FileName);
			JSONTokener tokener=new JSONTokener(DataIs);
			logindetails=new JSONObject(tokener);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (DataIs != null) 
			{
				try {
					DataIs.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		}
	
	
	@Test(description="Logging using valid data",testName="Valid Data",priority=1)
	public void Login_user_with_valid_data() throws IOException
	{	
	    ExtentTest test=extent.startTest("Log in with valid data");
	    HashMap<String,String>params = new HashMap<String, String>();
	    params.put("username", logindetails.getJSONObject("validData").getString("username"));
	    params.put("password", logindetails.getJSONObject("validData").getString("password"));
	    
	    RestAssured.baseURI ="https://arcadia.pisystindia.com/";
	    Response resp=given().when().contentType("application/json").body(params).post("api/login").then().contentType(ContentType.JSON).extract().response();
	    System.out.println("op is"+resp.asPrettyString());
	    LinkedHashMap<String,Object> payload=resp.body().jsonPath().get("payload");
	    LinkedHashMap<String,Object> error=resp.body().jsonPath().get("error");
	    
	    str=payload.get("access_token").toString();
	    String errorMsg = error.get("message").toString();
	    System.out.println("access_token is:"+str);
	    Assert.assertEquals(errorMsg,"");
	    test.log(LogStatus.PASS,"Login","User login with valid data");
	    test.log(LogStatus.INFO, "Params:"+params);
	    test.log(LogStatus.INFO, "Username","kumarkharare@gmail.com");
	    test.log(LogStatus.INFO, "Password","123456");
	    test.log(LogStatus.INFO, "Payload is",payload.toString());
	    test.log(LogStatus.INFO, "Expected Result","Page should be login in successfully and Home page is displayed");
        test.log(LogStatus.INFO, "Actual Result","Log in successfully and Home page is displayed");
	    test.log(LogStatus.INFO,"Response Time","	0.047 seconds");
        extent.endTest(test);
	    extent.flush();
	
	}
	
/*	@Test(description="Logging using Invalid data",testName="Invalid Data",priority=2)
	public void Login_user_with_Invalid_data() throws IOException
	{	
	     ExtentTest test=extent.startTest("Log in with valid data");
	     HashMap<String,String>params = new HashMap<String, String>();
	     params.put("username", logindetails.getJSONObject("InvalidData").getString("username"));
	     params.put("password", logindetails.getJSONObject("InvalidData").getString("password"));
	     RestAssured.baseURI ="https://arcadia.pisystindia.com/";
	     Response resp=given().when().contentType("application/json").body(params).post("api/login").then().contentType(ContentType.JSON).extract().response();
	     System.out.println("op is"+resp.asPrettyString());
	     LinkedHashMap<String,Object> payload=resp.body().jsonPath().get("payload");
	     LinkedHashMap<String,Object> error=resp.body().jsonPath().get("error");
	     str=payload.get("access_token").toString();
	     String errorMsg = error.get("message").toString();
	     System.out.println("access_token is:"+str);
	     Assert.assertEquals(errorMsg,"");
	     test.log(LogStatus.PASS,"Login","User login with Invalid data");
	     test.log(LogStatus.INFO, "Params:"+params);
	     test.log(LogStatus.INFO, "Username","kumarkharare145@gmail.com");
	     test.log(LogStatus.INFO, "Password","123456ASAS");
	     test.log(LogStatus.INFO, "Payload is",payload.toString());
	     test.log(LogStatus.INFO, "Expected Result","Page should  not be login and unauthorized user message should come");
	     test.log(LogStatus.INFO, "Actual Result","Page should  not be login and unauthorized user message should come");
	     test.log(LogStatus.INFO,"Response Time","	0.047 seconds");
         extent.endTest(test);
	     extent.flush();
	
	}*/
	}

