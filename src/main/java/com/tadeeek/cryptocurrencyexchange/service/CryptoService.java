package com.tadeeek.cryptocurrencyexchange.service;

import com.tadeeek.cryptocurrencyexchange.model.ConsumedCrypto;

import com.tadeeek.cryptocurrencyexchange.model.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CryptoService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private Crypto crypto;


    public Mono<Crypto> getCurrencies(String currency) {
        System.out.println("Getting currenciess... ");

        Mono<Crypto> cryptoMono = webClientBuilder.build().get().uri("/{currency}", currency)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ConsumedCrypto.class).map(crypto::convertConsumedCryptoToCrypto).log();

        //log
        //cryptoMono.subscribe(System.out::println);

        return cryptoMono;
    }

}
