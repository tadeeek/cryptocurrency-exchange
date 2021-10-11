package com.tadeeek.cryptocurrencyexchange.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.web.reactive.function.client.WebClient;


@WebFluxTest(controllers = CryptoController.class)

class CryptoControllerTest {
    @Autowired
    private WebClient webClient;

    public CryptoControllerTest(WebClient webClient) {
        this.webClient = webClient;
    }

    @Test
    void getCurrencies() {
//        Crypto crypto = new Crypto();
//        crypto.setSource("BTC");
//        Map<String, BigDecimal> rates = new HashMap<>();
//        rates.put("BTC", new BigDecimal(16.40614768473217722240352312));
//        rates.put("USDT", new BigDecimal(57135.392766652044543967077002));
//
//        Mono<Crypto> employeMono = Mono.just(crypto);
//
//
    }

    @Test
    void getFilteredCrypto() {
    }

    @Test
    void exchange() {
    }
}