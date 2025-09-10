package uz.example.microservice.inventoryservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldReadInventory() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/inventory?skuCode=iPhone_15&quantity=10")
                .then()
                .statusCode(200)
                .body(equalTo("true"));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/inventory?skuCode=iPhone_15&quantity=1000")
                .then()
                .statusCode(200)
                .body(equalTo("false"));
    }
}
