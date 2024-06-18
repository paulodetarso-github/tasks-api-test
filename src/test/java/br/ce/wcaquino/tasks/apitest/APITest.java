package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void testDemo() {
		RestAssured.given()
				.log().all()
			.when()
				.get("/todo")
			.then()
				.log().all();
	}
	
	@Test
	public void deveRetornarTarefas() {
		
		RestAssured.given()
				.when()
					.get("/todo")
				.then()
					.statusCode(200);
		
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		
		RestAssured.given()
				.body("{ \"task\": \"Criar um teste para a API\", \"dueDate\": \"2024-06-30\" }")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(201);
	}

	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		
		RestAssured.given()
				.body("{ \"task\": \"Criar um teste para a API\", \"dueDate\": \"2023-06-30\" }")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"));
	}

}