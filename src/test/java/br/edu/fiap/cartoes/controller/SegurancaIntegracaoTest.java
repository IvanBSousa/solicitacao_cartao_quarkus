package br.edu.fiap.cartoes.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/** Estrutura de teste do grupo; não contém teste resolvido nem @Test de exemplo. */
@QuarkusTest
class SegurancaIntegracaoTest {

    @Test
    void deveRetornar401AoListarCartoesSemToken() {
        given()
                .when()
                .get("/api/cartoes")
                .then()
                .statusCode(401);
    }
}
