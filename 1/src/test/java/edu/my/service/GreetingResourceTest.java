package edu.my.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHello1Endpoint() {
        given()
                .when().get("/hello/v1")
                .then()
                    .statusCode(200)
                    .body(is("Hello quarkus!"));
    }

    @Test
    public void testHello2Endpoint() {
        given()
                .when().get("/hello/v2")
                .then()
                    .statusCode(200)
                    .body(is("Hello from app, quarkus :)"));
    }

    @Test
    public void testGreetingEndpoint() {
        String uuid = UUID.randomUUID().toString();
        given()
                .pathParam("name", uuid)
                .when().get("/hello/greeting/{name}")
                .then()
                    .statusCode(200)
                    .body(is("Hello, dear " + uuid + "."));
    }

}