package org.openindustries.itai.functions.drift;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"modelId\":\"m-1\",\"driftIndex\":6,\"qualityDropIndex\":4,\"latencyIndex\":9}")
                .when().post("/driftScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"modelId\":\"m-2\",\"driftIndex\":81,\"qualityDropIndex\":73,\"latencyIndex\":66}")
                .when().post("/driftScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
