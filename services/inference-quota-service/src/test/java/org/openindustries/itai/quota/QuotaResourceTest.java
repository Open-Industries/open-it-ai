package org.openindustries.itai.quota;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class QuotaResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"teamCode\":\"ml-platform\",\"budgetUnits\":1000000}")
                .when().post("/api/v1/quotas")
                .then().statusCode(201).body("budgetUnits", is(1000000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"usageType\":\"COMPLETION\",\"units\":250000}")
                .when().post("/api/v1/quotas/" + id + "/usage")
                .then().statusCode(200)
                .body("remainingAfter", is(750000));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"teamCode\":\"ml-platform\",\"budgetUnits\":1000000}")
                .when().post("/api/v1/quotas")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"usageType\":\"COMPLETION\",\"units\":5000000}")
                .when().post("/api/v1/quotas/" + id + "/usage")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/quotas/" + id)
                .then().statusCode(200).body("budgetUnits", is(1000000));
    }
}
