package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@QuarkusTestResource(DatabaseLifecycle.class)
public class ProductTest {
    @Test
    void testSample() {
        given()
                .when().get("/products")
                .then()
                .statusCode(200)
                .body("", is(Product.listAll()));
    }

    @Test
    void testRegister() {
        ProductRegisterDTO productDTO = new ProductRegisterDTO("Test", new BigDecimal(12.35));
        given()
                .contentType("application/json")
                .body(productDTO)
                .when()
                    .post("/products")
                .then()
                    .statusCode(204);
    }
}
