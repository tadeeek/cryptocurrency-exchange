package com.tadeeek.cryptocurrencyexchange.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadeeek.cryptocurrencyexchange.model.Crypto;

import com.tadeeek.cryptocurrencyexchange.model.ExchangeCurrency;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeRequest;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeResponse;
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
import java.util.List;

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

        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(crypto))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );
        //Actually testing API
        Crypto response = controller.getCurrencies("BTC").block();

        Assertions.assertEquals("BTC", response.getSource());
        Assertions.assertEquals(true,response.getRates().size()>1);

    }

    @Test
    void getFilteredCryptoTest() throws JsonProcessingException {
        Crypto crypto = new Crypto();

        List<String> filter = new ArrayList<>();
        filter.add("ETH");
        filter.add("USDT");

        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(crypto))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );
        //Actually testing API
        Crypto response = controller.getFilteredCrypto("BTC", filter).block();

        Assertions.assertEquals("BTC", response.getSource());
        Assertions.assertEquals(2, response.getRates().size());
        Assertions.assertEquals(true, response.getRates().containsKey("ETH"));
        Assertions.assertEquals(true, response.getRates().containsKey("USDT"));
    }

    @Test
    void exchangeTest() throws JsonProcessingException {
        ExchangeResponse exchangeResponse = new ExchangeResponse();
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setFrom("BTC");
        exchangeRequest.setAmount(new BigDecimal(1000));
        List<String> filter = new ArrayList<>();
        filter.add("ETH");
        filter.add("USDT");
        exchangeRequest.setTo(filter);

        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(exchangeResponse))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );
        //Testing app response but with actual response from API also
        ExchangeResponse response = controller.exchange(exchangeRequest).block();

        Assertions.assertEquals("BTC", response.getFrom());
        Assertions.assertEquals(true, response.getExchangedCurrencies().size()>1);
        Assertions.assertEquals(true, response.getExchangedCurrencies().stream().anyMatch(el -> "ETH".equals(el.getName())));


    }

}