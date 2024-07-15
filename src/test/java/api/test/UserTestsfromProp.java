package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPtsProperties;
import api.payload.User;
import io.restassured.response.Response;

public class UserTestsfromProp {
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority =1)
	public void testPostUser() {
		
		logger.info("*********Creating User**********");
		Response response = UserEndPtsProperties.createUser(userPayload);
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*********User Created************");
	}
	
	@Test(priority =2)
	public void testGetUserByName() {
		logger.info("*********Reading User**********");
		Response response = UserEndPtsProperties.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		logger.info("*********User Info Displayed**********");
	}
	
	@Test(priority =3)
	public void testUpdateByUserByName() {
		
		logger.info("*********Updating user**********");
		//update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response = UserEndPtsProperties.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*********User Updated**********");
		
		Response responseAfterUpdate= UserEndPtsProperties.readUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(responseAfterUpdate.getStatusCode(), 200);
	}

	@Test(priority =4)
	public void testDeleteUserByName() {
		
		logger.info("*********Deleting User**********");
		Response response = UserEndPtsProperties.deleteUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*********User Deleted**********");
	}
}
