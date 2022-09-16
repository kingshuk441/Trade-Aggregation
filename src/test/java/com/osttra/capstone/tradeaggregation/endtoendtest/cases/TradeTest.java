package com.osttra.capstone.tradeaggregation.endtoendtest.cases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class TradeTest {
	
	@Test
	void GetTradeFullData() {
		
		Response response = RestAssured.get("http://localhost:8084/api/trades");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		
		JsonPath j = new JsonPath(response.asString());
		int  TradeSize =j.getInt("data.size()");
		 assertEquals(3,TradeSize);
	}
	
	@Test @DisplayName("Add new Trade")
	void addnewTrade() {
		
		String requestBody ="{\"buyer\":\"ICICIM\",\"counterPartyName\":\"SBICH\",\"currency\":\"ERU\","
				+ "\"effectiveDate\":\"2022-09-15\",\"instrumentId\":\"MUTUALFUND\",\"maturityDate\":"
				+ "\"2022-09-15\",\"notionalAmount\":1000,\"partyName\":\"ICICIM\",\"seller\":"
				+ "\"SBICH\",\"tradeDate\":\"2022-09-15\",\"tradeRefNum\":\"130\"}";

		Response response = RestAssured.given()
         .contentType(ContentType.JSON)
         .body(requestBody)
         .post("http://localhost:8084/api/trade");

        
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());
		
    	int  NewTradeSize =j.getInt("data.size()");
		 assertEquals(1,NewTradeSize);
		
	}
	
	
	@Test @DisplayName("Find Trade by Party name")
	void findTradebyPartyname() {
		
		Response response = RestAssured.get("http://localhost:8084/api/party/name/SBICN");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());
		JsonPath j = new JsonPath(response.asString());
		
		 String partyName = j.getString("data[0].partyName");
		 assertEquals(partyName, "SBICN");
		

	}
	

	
	@Test @DisplayName("Find Trade by ID")
	void getTradebyID() {
		
		Response response = RestAssured.get("http://localhost:8084/api/trade/6");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());
		
		 int tradeId = j.getInt("data[0].tradeId");
		 assertEquals(tradeId,6);
	}
	
	
	@Test @DisplayName("Find Trade by Institution name")
	void findTradebyInstitutionname() {
		
		Response response = RestAssured.get("http://localhost:8084/api/trade/institution/ICICI");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());
		JsonPath j = new JsonPath(response.asString());
		
		 String partyName = j.getString("data[0].partyName");
		 assertEquals(partyName, "ICICIM");
	}
	
	@Test @DisplayName("Search Trade by Valid TRN and  Valid Party Name")
	void tradeByValidTRNAndValidPartyName() {
		
		Response response = RestAssured.get("http://localhost:8084/api/trade/search/1/121/HDFCCH");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());
		JsonPath j = new JsonPath(response.asString());
		
		String tradeTRN = j.getString("data[0].tradeRefNum");
		 assertEquals(tradeTRN,"125");	
		
	}
	
	
	@Test @DisplayName("Get all Trade by Status(both cancel and unconfirmed) and Valid party name")
	void TradeByStatusAndPartyName() {
		
		Response response = RestAssured.get("http://localhost:8084/api/trade/search/2/HDFCCH/Cancel");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());
		JsonPath j = new JsonPath(response.asString());
		 String tradeStatus = j.getString("data[0].status");
		 assertEquals(tradeStatus,"AGG" );	
		
	}

	
	@Test @DisplayName("Update Trade with Currency")
	void UpdateTrade() {
		String requestBody ="{\"buyer\":\"ICICIM\",\"confirmationTimeStamp\":\"2022-09-14T10:32:26.336Z\","
				+ "\"counterPartyName\":\"SBICH\",\"currency\":\"ERU\",\"effectiveDate\":\"2022-09-14\","
				+ "\"institutionId\":0,\"instrumentId\":\"string\",\"maturityDate\":\"2022-09-14\","
				+ "\"notionalAmount\":0,\"partyName\":\"ICICIM\",\"seller\":\"SBICH\",\"tradeDate\":\"2022-09-14\","
				+ "\"tradeRefNum\":\"string\",\"version\":0,\"versionTimeStamp\":\"2022-09-14T10:32:26.337Z\"}";
		Response response = RestAssured.given().contentType(ContentType.JSON)
		         .body(requestBody).put("http://localhost:8084/api/trade/6");
		System.out.println(response.getBody());
		System.out.println(response.asString());
		System.out.println(response.getStatusLine());
		System.out.println(response.asPrettyString());	
		JsonPath j = new JsonPath(response.asString());
		 String tradeCurrency = j.getString("data[0].currency");
		 assertEquals(tradeCurrency,"ERU" );	
		
	}
	
}
