package org.openindustries.itai.model;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AiModelResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Support Classifier v3\",\"framework\":\"PYTORCH\",\"ownerTeam\":\"ml-platform\"}")
                .when().post("/api/v1/models")
                .then().statusCode(201)
                .body("status", is("REGISTERED"))
                .body("framework", is("PYTORCH"))
                .extract().path("id");

        given().when().get("/api/v1/models/" + id)
                .then().statusCode(200).body("status", is("REGISTERED"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"name\":\"Support Classifier v3\",\"framework\":\"PYTORCH\",\"ownerTeam\":\"ml-platform\"}")
                .when().post("/api/v1/models")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/models/" + id + "/approve")
                .then().statusCode(200).body("status", is("APPROVED"));
    }
}
