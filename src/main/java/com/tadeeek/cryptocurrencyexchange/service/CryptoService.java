package com.tadeeek.cryptocurrencyexchange.service;

import com.tadeeek.cryptocurrencyexchange.model.ConsumedCrypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CryptoService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<ConsumedCrypto> getCurrencies(String currency) {
        System.out.println("Getting currenciess... ");

        Mono<ConsumedCrypto> consumedCryptoMono = webClientBuilder.build().get().uri("/{currency}", currency)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ConsumedCrypto.class);

        //log
        consumedCryptoMono.subscribe(System.out::println);

        return  consumedCryptoMono;
    }

}
