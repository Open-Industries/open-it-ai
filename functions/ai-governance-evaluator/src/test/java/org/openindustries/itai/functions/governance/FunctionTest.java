package org.openindustries.itai.functions.governance;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"modelId\":\"m-1\",\"measuredOpenRiskItems\":0,\"targetOpenRiskItems\":0,\"measuredDocumentationCoveragePct\":100.0,\"targetDocumentationCoveragePct\":100.0}")
                .when().post("/aiGovernanceEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"modelId\":\"m-2\",\"measuredOpenRiskItems\":4,\"targetOpenRiskItems\":0,\"measuredDocumentationCoveragePct\":100.0,\"targetDocumentationCoveragePct\":100.0}")
                .when().post("/aiGovernanceEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
