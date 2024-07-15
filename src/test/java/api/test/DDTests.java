package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utitlites.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	
	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostuser(String userID, String userName, String fName, String lName, String userEmail, String pwd, String ph)
	{
		User userPayload =new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(lName);
		userPayload.setLastName(lName);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		Response response = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteuser(String userName) {
		Response response = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
