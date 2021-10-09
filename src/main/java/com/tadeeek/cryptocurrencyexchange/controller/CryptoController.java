package com.tadeeek.cryptocurrencyexchange.controller;

import com.tadeeek.cryptocurrencyexchange.model.ConsumedCrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class CryptoController {

    // for development purposes only - change later
    private String apikey = "02F6E779-26AD-4038-910D-F11A343B5912";

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping(value = "/currencies/{currency}")
    public Mono<ConsumedCrypto> getCrypto(@PathVariable String currency) {

        return  getCurrency(currency);
    }


    private Mono<ConsumedCrypto> getCurrency(String currency) {
        System.out.println("Getting currenciess... ");

        Mono<ConsumedCrypto> consumedCryptoMono = webClientBuilder.build().get().uri("/{currency}?apikey={apikey}", currency, apikey)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ConsumedCrypto.class);

        //to console
        consumedCryptoMono.subscribe(System.out::println);

        return  consumedCryptoMono;
    }
}
