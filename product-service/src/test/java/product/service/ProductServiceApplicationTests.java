package product.service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateProductSuccessfully() {
        String productJson = """
                	{
                		"name": "Test Product",
                		"description": "iPhone is very expensive model",
                		"price": 1100
                	};
                """;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productJson)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("name", Matchers.equalTo("Test Product"))
                .body("description", Matchers.equalTo("iPhone is very expensive model"))
                .body("price", Matchers.equalTo(1100));
    }


}
