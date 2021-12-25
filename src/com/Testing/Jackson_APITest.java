package com.Testing;

import com.JSON_API.Jackson_API;
import com.Testing.Test.AuthorPOJO;
import com.Testing.Test.BookPOJO;
import com.Testing.Test.DayPOJO;
import com.Testing.Test.TestCases;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Jackson_APITest {
	
	private String simpleTestCaseJsonSource = "{  \n" +
													  "  \"title\": \"Coder From Scratch\",  \n" +
													  "  \"author\": \"Rui\"\n" +
													  "}";
	
	private String dayScenario1 = "{\n" +
										  "  \"date\": \"2019-12-25\",\n" +
										  "  \"name\": \"Christmas Day\"\n" +
										  "}";
	
	private String authorBookScenario = "{\n" +
												"  \"authorName\": \"Rui\",\n" +
												"  \"books\": [\n" +
												"    {\n" +
												"      \"title\": \"title1\",\n" +
												"      \"inPrint\": true,\n" +
												"      \"publishDate\": \"2019-12-25\"\n" +
												"    },\n" +
												"    {\n" +
												"      \"title\": \"title2\",\n" +
												"      \"inPrint\": false,\n" +
												"      \"publishDate\": \"2019-01-01\"\n" +
												"    }\n" +
												"  ]\n" +
												"}";
	
	@Test
	void parse() throws IOException {
		
		JsonNode node = Jackson_API.parse(simpleTestCaseJsonSource);
		assertEquals(node.get("title").asText(), "Coder From Scratch");
		
	}
	
	@Test
	void fromJson() throws IOException {
		
		JsonNode node = Jackson_API.parse(simpleTestCaseJsonSource);
		TestCases pojo = Jackson_API.fromJson(node, TestCases.class);
		
		assertEquals(pojo.getTitle(), "Coder From Scratch");
		
	}
	
	@Test
	void toJson() {
		TestCases pojo = new TestCases();
		pojo.setTitle("Testing 123");
		
		JsonNode node = Jackson_API.toJson(pojo);
		
		assertEquals(node.get("title").asText(), "Testing 123");
	}
	
	@Test
	void stingify() throws JsonProcessingException {
		TestCases pojo = new TestCases();
		pojo.setTitle("Testing 123");
		
		JsonNode node = Jackson_API.toJson(pojo);
		
		System.out.println(Jackson_API.stingify(node));
		System.out.println(Jackson_API.prettyPrint(node));
		
	}
	
	@Test
	void dayTestScenario1() throws IOException {
		
		JsonNode node = Jackson_API.parse(dayScenario1);
		DayPOJO pojo = Jackson_API.fromJson(node, DayPOJO.class);
		
		assertEquals("2019-12-25", pojo.getDate().toString());
	}
	
	@Test
	void authorBookScenario1() throws IOException {
		
		JsonNode node = Jackson_API.parse(authorBookScenario);
		AuthorPOJO pojo = Jackson_API.fromJson(node, AuthorPOJO.class);
		
		System.out.println("Author : " + pojo.getAuthorName());
		for (BookPOJO bP : pojo.getBooks()) {
			System.out.println("Book : " + bP.getTitle());
			System.out.println("Is In Print? " + bP.isInPrint());
			System.out.println("Date : " + bP.getPublishDate());
		}
	}
}