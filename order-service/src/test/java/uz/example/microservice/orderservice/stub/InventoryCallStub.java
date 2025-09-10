package uz.example.microservice.orderservice.stub;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryCallStub {
    public static void stubInventoryCall(String skuCode, Integer quantity) {

            stubFor(get(urlPathEqualTo("/api/inventory"))
                    .withQueryParam("skuCode", equalTo(skuCode))
                    .withQueryParam("quantity", equalTo(String.valueOf(quantity)))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("true")
                            .withStatus(200)));
        }
}
