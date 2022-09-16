package com.osttra.capstone.tradeaggregation.endtoendtest.cases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

class InstitutionTest {

	@Test @DisplayName("Get all Institution")
	void GetInstitutionFullData() {
		
		Response response = RestAssured.get("http://localhost:8084/api/institution");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());
		JsonPath j = new JsonPath(response.asString());
		int  institutuionSize =j.getInt("data.size()");
		assertEquals(4,institutuionSize);
	}
	
	
	@Test @DisplayName("Get Institution by valid ID")
	void getInstitionbyID() {
		
		Response response = RestAssured.get("http://localhost:8084/api/institution/3");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());
		
		 int institutionId = j.getInt("data[0].institutionId");
		 assertEquals(institutionId,3);

	}
	
	
	@Test @DisplayName("Add new Institution")
	void addnewInstitution() {
		
		String requestBody ="{\"id\":0,\"institutionName\":\"JPMORGAN\",\"parties\":[0]}";

		Response response = RestAssured.given()
         .contentType(ContentType.JSON)
         .body(requestBody)
         .post("http://localhost:8084/api/institution");

        
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());
		
    	int  NewInstitutionSize =j.getInt("data.size()");
		 assertEquals(1,NewInstitutionSize);
		
	}
	
	
	@Test @DisplayName("Update Institution By Id")
	void updateInsitutionById() {
		
		String requestBody ="{\"institutionName\":\"SBI\",\"parties\":[]}";
		Response response = RestAssured.given().contentType(ContentType.JSON)
		         .body(requestBody).put("http://localhost:8084/api/institution/2");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());
		
		 String institutionData = j.getString("data[0].institutionName");
		 assertEquals(institutionData,"SBI");

	}
	
	
	@Test
	void removeInstitutionByvalidInstitutionId() {
		Response response = RestAssured.delete("http://localhost:8084/api/institution/15");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());	
		 int deletedInstitution = j.getInt("status");
		 int deletedInstitutionId=j.getInt("data[0].institutionId");
		 assertEquals(deletedInstitutionId,15);
		 assertEquals(deletedInstitution,202);

	}
	

}
