package com.tadeeek.cryptocurrencyexchange.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tadeeek.cryptocurrencyexchange.model.Crypto;
import com.tadeeek.cryptocurrencyexchange.model.Exchange;
import com.tadeeek.cryptocurrencyexchange.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping(value = "/{currency}")
    public Mono<Crypto> getCurrencies(@PathVariable String currency) {
        return  cryptoService.getCurrencies(currency);
    }

    @GetMapping(value = "/{currency}", params = {"filter"})
    public Mono<Crypto> getFilteredCrypto(@PathVariable String currency, @RequestParam List<String> filter) {
        return cryptoService.getFilteredCurrencies(currency, filter);
    }

    //Testing - delete later
    @GetMapping(value = "/test",produces = MediaType.APPLICATION_JSON_VALUE)
    public Exchange test() throws JsonProcessingException {
        Exchange exchange = new Exchange("BTC", Arrays.asList("USD", "ETH"), new BigDecimal(123));

        return exchange;
    }






}
