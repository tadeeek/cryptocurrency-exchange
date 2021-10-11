package com.tadeeek.cryptocurrencyexchange.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadeeek.cryptocurrencyexchange.model.Crypto;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        rates.put("CUBE", new BigDecimal(26272.689177070701650449535119));
        crypto.setRates(rates);

        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(crypto)) // 2DO - maybe use Json file?
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        Mono<Crypto> response = controller.getCurrencies("BTC");
        Assertions.assertEquals("BTC", response.block().getSource());
        Assertions.assertEquals(3, response.block().getRates().size());
    }

    @Test
    void getFilteredCryptoTest() throws JsonProcessingException {
        Crypto crypto = new Crypto();
        crypto.setSource("BTC");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("ETH", new BigDecimal(16.40614768473217722240352312));
        rates.put("USDT", new BigDecimal(57135.392766652044543967077002));
        crypto.setRates(rates);

        List<String> filter = new ArrayList<>();
        filter.add("ETH");
        filter.add("USDT");

        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(crypto)) // 2DO - maybe use Json file?
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        Mono<Crypto> response = controller.getFilteredCrypto("BTC", filter);
        Assertions.assertEquals("BTC", response.block().getSource());
        Assertions.assertEquals(2, response.block().getRates().size());
        Assertions.assertEquals(true, response.block().getRates().containsKey("ETH"));
        Assertions.assertEquals(true, response.block().getRates().containsKey("USDT"));
    }

    @Test
    void exchange() {
    }

}