package com.baumannibiuna.livrotarefa.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        // Não definir basePath aqui - vamos usar paths completos nos testes
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void healthCheck_ShouldReturn200() {
        given()
        .when()
            .get("/actuator/health")
        .then()
            .statusCode(200);
    }

    @Test
    public void createTaskAndRetrieveIt_ShouldWorkCorrectly() {
        String taskJson = """
            {
                "name": "Tarefa de Teste",
                "description": "Descrição da tarefa"
            }
            """;

        // POST - usando o caminho completo
        given()
            .contentType(ContentType.JSON)
            .body(taskJson)
        .when()
            .post("/api/tasks")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", equalTo("Tarefa de Teste"));

        // GET
        given()
        .when()
            .get("/api/tasks")
        .then()
            .statusCode(200)
            .body("content", hasSize(greaterThan(0)));
    }

    @Test
    public void createTask_WithoutName_ShouldReturnBadRequest() {
        String invalidTaskJson = """
            {
                "description": "Descrição sem nome"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(invalidTaskJson)
        .when()
            .post("/api/tasks")
        .then()
            .statusCode(400)
            .body("message", containsString("Nome obrigatório"));
    }
}