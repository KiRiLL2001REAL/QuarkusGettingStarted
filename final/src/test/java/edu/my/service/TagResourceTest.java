package edu.my.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class TagResourceTest {
    @Test
    public void testGetAllTagsEndpoint() {
        given()
                .when().get("/tags")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetTagById1Endpoint() {
        given()
                .pathParam("id", 1)
                .when().get("/tags/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetTagById2Endpoint() {
        given()
                .pathParam("id", 0)
                .when().get("/tags/{id}")
                .then()
                .statusCode(500);
    }
}