package org.openindustries.itai.gates;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class DeploymentGateResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"high-standard\",\"riskTier\":\"HIGH\",\"evalScorePoints\":900,\"maxBiasIncidents\":2,\"priority\":1}")
                .when().post("/api/v1/deployment-gates")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/deployment-gates/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"riskTier\":\"HIGH\",\"requiredMaxBiasIncidents\":3,\"requiredEvalScorePoints\":800}")
                .when().post("/api/v1/deployment-gates/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"riskTier\":\"CRITICAL\",\"requiredMaxBiasIncidents\":3,\"requiredEvalScorePoints\":800}")
                .when().post("/api/v1/deployment-gates/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
