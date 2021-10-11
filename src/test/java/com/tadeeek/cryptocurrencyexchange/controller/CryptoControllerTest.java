package com.tadeeek.cryptocurrencyexchange.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadeeek.cryptocurrencyexchange.controller.CryptoController;
import com.tadeeek.cryptocurrencyexchange.model.Crypto;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
class CryptoControllerTest {

    private static MockWebServer mockWebServer;

    private  WebClient webClient;

    @Autowired
    public CryptoControllerTest(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Autowired
    private CryptoController controller;

    @BeforeAll
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(9090);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getCurrenciesTest() throws JsonProcessingException {
        Crypto crypto = new Crypto();
        crypto.setSource("BTC");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("ETH", new BigDecimal(16.40614768473217722240352312));
        rates.put("USDT", new BigDecimal(57135.392766652044543967077002));

        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(crypto))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        Mono<Crypto> response = controller.getCurrencies("BTC");

        Assertions.assertEquals("BTC", response.block().getSource());
    }


}