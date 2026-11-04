package org.openindustries.itai.dataset;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class DatasetResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Support Tickets Corpus\",\"dataType\":\"TEXT\",\"recordsCount\":250000}")
                .when().post("/api/v1/datasets")
                .then().statusCode(201)
                .body("status", is("DRAFT"))
                .body("dataType", is("TEXT"))
                .extract().path("id");

        given().when().post("/api/v1/datasets/" + id + "/approve")
                .then().statusCode(200).body("status", is("APPROVED"));
    }
}
