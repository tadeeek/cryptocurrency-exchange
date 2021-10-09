package com.tadeeek.cryptocurrencyexchange.controller;

import com.tadeeek.cryptocurrencyexchange.model.ConsumedCrypto;
import com.tadeeek.cryptocurrencyexchange.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private CryptoService cryptoService;

    @GetMapping(value = "/currencies/{currency}")
    public Mono<ConsumedCrypto> getCurrencies(@PathVariable String currency) {
        return  cryptoService.getCurrencies(currency);
    }


}
