package com.tadeeek.cryptocurrencyexchange.controller;

import com.tadeeek.cryptocurrencyexchange.model.Crypto;
import com.tadeeek.cryptocurrencyexchange.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping(value = "/currencies/{currency}")
    public Mono<Crypto> getCurrencies(@PathVariable String currency) {
        return  cryptoService.getCurrencies(currency);
    }


}
