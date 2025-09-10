package uz.example.microservice.orderservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import uz.example.microservice.orderservice.stub.InventoryCallStub;

import static io.restassured.RestAssured.given;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldSubmitOrder() {

		InventoryCallStub.stubInventoryCall("iPhone_15", 1);

		String submitOrderJson = """
                	{
                		"skuCode": "iPhone_15",
                		"price": 1100,
                		"quantity": 1
                	}
                """;

		given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(submitOrderJson)
				.when()
				.post("/api/order")
				.then()
				.statusCode(201)
				.body("skuCode", Matchers.equalTo("iPhone_15"))
				.body("price", Matchers.equalTo(1100))
				.body("quantity", Matchers.equalTo(1));
	}

}
