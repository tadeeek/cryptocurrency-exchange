package com.tadeeek.cryptocurrencyexchange.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tadeeek.cryptocurrencyexchange.model.Crypto;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeCurrency;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeRequest;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeResponse;
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

    //Testing jsn - delete later
    @GetMapping(value = "/test",produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeResponse test() throws JsonProcessingException {
        ExchangeRequest exchangeRequest = new ExchangeRequest("BTC", Arrays.asList("USD", "ETH"), new BigDecimal(123));

        ExchangeCurrency exchangeCurrency1 = new ExchangeCurrency("ETH", new BigDecimal(0.11), new BigDecimal(111), new BigDecimal(0.111), new BigDecimal(0.00001));
        ExchangeCurrency exchangeCurrency2 = new ExchangeCurrency("RLC", new BigDecimal(0.22), new BigDecimal(222), new BigDecimal(0.222), new BigDecimal(0.00002));
        ExchangeResponse exchangeResponse = new ExchangeResponse("BTC", Arrays.asList(exchangeCurrency1,exchangeCurrency2));

        return exchangeResponse;
    }

    @PostMapping("/exchange")
    public ExchangeRequest exchange(@RequestBody ExchangeRequest exchangeRequest)
    {
        System.out.println("I have succesfully intercepted body:");
        System.out.println(exchangeRequest.toString());

        cryptoService.getFilteredCurrencies(exchangeRequest.getFrom(), exchangeRequest.getTo());

        //webClient.post().uri("/doexchange").body(Mono.just(exchange) , Exchange.class).retrieve().bodyToMono(Exchange.class);
        return exchangeRequest;
    }








}
