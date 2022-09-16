package com.osttra.capstone.tradeaggregation.endtoendtest.cases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@SpringBootTest
class PartyTest {

	@Test
	void getAllParties() {
		
		Response response = RestAssured.get("http://localhost:8084/api/parties");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		
		JsonPath j = new JsonPath(response.asString());
		int  PartySize =j.getInt("data.size()");
		 assertEquals(7,PartySize);
		
	}
	
	

	@Test @DisplayName("Find Party By Partyname")
	void findPartyByPartyname() {
		
		Response response = RestAssured.get("http://localhost:8084/api/party/name/HDFCCN");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());
		JsonPath j = new JsonPath(response.asString());
		 String partyName = j.getString("data[0].partyName");
		 assertEquals(partyName, "HDFCCN");
		}
	
	@Test @DisplayName("Get Party by ID")
	void getPartybyID() {
		
		Response response = RestAssured.get("http://localhost:8084/api/party/15");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());
		
		 int partyId = j.getInt("data[0].partyId");
		 assertEquals(partyId,15);

	}
	

	
	@Test @DisplayName("Add new Party")
	void addnewParty() {
		
	String requestBody ="{\"partyName\":\"JPMD\",\"partyFullName\":\"JPMORAN_DELHI\",\"institution\":1}";

		Response response = RestAssured.given()
         .contentType(ContentType.JSON)
         .body(requestBody)
         .post("http://localhost:8084/api/party");

        
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());

		int  NewPartySize =j.getInt("data.size()");
		 assertEquals(1,NewPartySize);
		
	}
	
	
	@Test @DisplayName("Update Party By Id")
	void updatePartyById() {
		
		String requestBody ="{\"partyName\":\"SBICH\",\"partyFullName\":\"SBI_CHANDIGARH\",\"institution\":1}";
		Response response = RestAssured.given().contentType(ContentType.JSON)
		         .body(requestBody).put("http://localhost:8084/api/party/16");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());
		
		 String partyName = j.getString("data[0].partyName");
		 assertEquals(partyName,"SBICH");

	}
	


	@Test @DisplayName("Remove Party from PartyId ")
	void removeParty() {
		Response response = RestAssured.delete("http://localhost:8084/api/party/26");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());	
		 int deletedPartyId=j.getInt("data[0].partyId");
		 assertEquals(deletedPartyId,26);


	}
	
	
	
	
}