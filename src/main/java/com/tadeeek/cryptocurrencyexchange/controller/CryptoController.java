package com.tadeeek.cryptocurrencyexchange.controller;

import com.tadeeek.cryptocurrencyexchange.model.Crypto;
import com.tadeeek.cryptocurrencyexchange.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping(value = "/currencies/{currency}")
    public Mono<Crypto> getCurrencies(@PathVariable String currency) {
        return  cryptoService.getCurrencies(currency);
    }

    @GetMapping(value = "/currencies/{currency}", params = {"filter"})
    public Mono<Crypto> getFilteredCrypto(@PathVariable String currency, @RequestParam List<String> filter) {
        return cryptoService.getFilteredCurrencies(currency, filter);
    }



}
