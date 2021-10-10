package com.tadeeek.cryptocurrencyexchange.service;

import com.tadeeek.cryptocurrencyexchange.model.ConsumedCrypto;

import com.tadeeek.cryptocurrencyexchange.model.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class CryptoService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private Crypto crypto;


    public Mono<Crypto> getCurrencies(String currency) {
        Mono<Crypto> cryptoMono = webClientBuilder.build().get().uri("/{currency}", currency)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ConsumedCrypto.class).map(crypto::convertConsumedCryptoToCrypto);

        return cryptoMono;
    }

    public Mono<Crypto> getFilteredCurrencies(String currency, List<String> filter) {
        Mono<Crypto> cryptoMono =
                webClientBuilder.build().get().uri("/{currency}", currency).accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(ConsumedCrypto.class)
                        .map(it -> crypto.convertConsumedCryptoToCryptoAndFilter(it, filter));
        return cryptoMono;
    }

}
