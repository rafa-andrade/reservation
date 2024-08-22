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

    ScenarioBuilder scenario = scenario("Scenario")
            .feed(csv("nomes.csv").random())
            .exec(
                    http("Request").post("/api/v1/reservations")
                            .body(StringBody(
                                    """
                                        {
                                            "name": "#{nome}",
                                            "email": "#{nome}@gmail.com",
                                            "date": "2024-08-27"
                                        }
                                    """
                            ))
            );

    {
        setUp(
                scenario.injectOpen(atOnceUsers(32))
        ).protocols(httpProtocol);
    }
}
