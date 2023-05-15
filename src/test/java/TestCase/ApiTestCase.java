package TestCase;
import java.util.*;
import org.testng.annotations.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.*;
public class ApiTestCase 
{
	

	
		ResponseSpecification res;    
		/* It is used to validate a common response or for multiple tests from  the body
		1:ResponseSpecification is an interface that allows you to specify how the expected 
		response must look like in order for a test to pass. 
		2:This interface has readymade methods to define assertions like status code, 
		content type, etc. 
		3:We need to use expect() method of RestAssured class to get a reference 
		for ResponseSpecification. 
		4:Remember ResponseSpecification is an interface and we can not create an object of it.
		*/
		
		RequestSpecification req_spec;  
		/* 
		 1:It is used to extract repetitive actions like setting up base URL, headers,
		  HTTP verbs etc which may be common for multiple Rest calls
		
		2:RequestSpecification is an interface that allows you to specify how the request
		 will look like. 
		3:This interface has readymade methods to define base URL, base path, headers, etc. 
		4:We need to use given() method of RestAssured class to get a reference for 
		RequestSpecification. 
		5:Remember RequestSpecification is an interface and we can not create an object of it.
		 RequestSpecificationImpl is its implemented class.
		*/
		
		String str=" ";
		
		@BeforeClass
		public void setSpecification()
		{
			res = RestAssured.expect();
			//Expect :will expect that the response body for the GET request to "/lotto" shouldcontain JSON or XML 
			
			res.statusLine("HTTP/1.1 200 OK");//to check wheather the expected 200 number is same as we r  getting in postman in response body.
			res.contentType(ContentType.JSON);//to check wheather data is coming from json or not .
			
		}
		
		 //Post Demo 1
		/*1: Post is used for sending the added with all the variable required 
		  2:Post is used to check how the database is interacting with the file without font-end developed 
		  
		*/
		@Test(priority=1)
		public void loginuser()
		{
			HashMap<String,String>params = new HashMap<String, String>();// Hashmap is use to send values in the form of key and value 
			params.put("username", "avez.kazi@pisystindia.com");//This user is already Created
			params.put("password", "Avez@123456");            
			RestAssured.baseURI ="https://arcadia.pisystindia.com/"; //In postname the value send in URL varible is the stored in the baseurl variable 
		   //given() It is mainly used to validate a Response obtained from a request.
		   //content type() used to specify a request or response content-type more easily than specifying the full string each time. i.e. http.request( GET, JSON ) 
		   //post() use to  post url where the parameter are stored
		   //then() mainly used to validate a Response obtained from a request after passing a particular value 
			//Extract: To extract the whole JSON as a string in Rest Assured  
			//2:It shall extract the whole response as a string using the asString method.
			//response:It will show the complete response object including the header cookies,body .
			Response resp=given().when().contentType("application/json").body(params).post("api/login").then().contentType(ContentType.JSON).extract().response();
			System.out.println("op is"+resp.asPrettyString());// prettystring =to print the output clearly in a nice manner 
			LinkedHashMap<String,Object> payload=resp.body().jsonPath().get("payload");
			/*LinkedHashMap
			 1:insertion order is not affected if a key is re-inserted into the map. 
			 2:A key k is reinserted into a map m if m.put(k, v) is invoked when m.containsKey(k) would return true immediately prior to the invocation
			 
			 JsonPath:To see how the output is in the form of json and from that location based on what variable u have find the value 
			 like u will get a json path as success payload firstname,lastname . 
			 2:We need to mention from that location based what details u want to see
			 3:We mentioned payload everythning under payload will get return and strored in the linkedhashmap 
			 */
			 
			 // We will get a access token as a long number and combination of alphabet that will be converted to string and will be than use to print 
			
			str=payload.get("access_token").toString();
			System.out.println("access_token is:"+str);
		}    

		//Post Demo 2
		
		
		@Test(description="Registering a new user",priority=2)
		public void UserRegister()
		{
			HashMap<String,String>params = new HashMap<String,String>();
			params.put("firstname","Praiutiksha");
			params.put("lastname","Thoruuat");
			params.put("email","pratiksha182.thorat@pisystindia.com");
			params.put("username","pratiksha141");
			params.put("mobile_code","91");
			params.put("mobile" ,"9503057872");
			params.put("password","Pratiksha@7447883652");
			params.put("country_code","IN");
			params.put( "country","India");
			params.put("agree","1");
			params.put( "address","pune");
			params.put("city","pune");
			params.put("zip","440440");
			params.put("state","Maharashtra");
			RestAssured.baseURI="https://arcadia.pisystindia.com/";
			
			Response resp=given().when().contentType("application/json").body(params).post("api/register").then().contentType(ContentType.JSON).extract().response();
			System.out.println("op is"+resp.asPrettyString());
			LinkedHashMap<String,Object> payload=resp.body().jsonPath().get("payload");
			str=payload.get("access_token").toString();
			System.out.println("access_token is:"+str);
			
		}

		
		
		//Get Demo 1
		@Test(description="getting profile details",priority=3)
			public void ProfileDetails()
			{
			RestAssured.baseURI ="https://arcadia.pisystindia.com/";
			Response resp=given().header("Authorization",str).when().get("api/user/profileDetails").then().contentType(ContentType.JSON).extract().response();
			System.out.println("op is"+resp.asPrettyString());
			
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Get Demo 1
		@Test(priority=2)
		
		public void GeneralSetting()
		{
			RestAssured.baseURI ="https://arcadia.pisystindia.com/";
			Response resp=given().header("Authorization",str).when().get("api/generalSetting").then().contentType(ContentType.JSON).extract().response();
			/*
			A REST request can have a special header called Authorization Header, 
			this header can contain the credentials (username and password) in some form. 
			Once a request with Authorization Header is received, 
			the server can validate the credentials and can let you access the
			 private resources.
			2:Check the status code in case of unauthorization it will be 404 .
			*/
			System.out.println("Get output is =  "+resp.asPrettyString());
		} 
}
