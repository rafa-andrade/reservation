package com.rafaandrade.reservation;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ReservationApiTest extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .acceptHeader("application/json")
                    .contentTypeHeader("application/json");

    ScenarioBuilder myScenario = scenario("My Scenario")
            .exec(
                    http("Request 1").post("/api/v1/reservations")
                            .body(StringBody(
                                    """
                                    {
                                        "name": "Rafa Andrade",
                                        "email": "rafadev88@gmail.com",
                                        "date": "2022-12-31"
                                    }
                                    """))
            );

    {
        setUp(
                myScenario.injectOpen(atOnceUsers(100))
        ).protocols(httpProtocol);
    }
}
