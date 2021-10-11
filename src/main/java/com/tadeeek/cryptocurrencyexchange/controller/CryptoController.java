package com.tadeeek.cryptocurrencyexchange.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tadeeek.cryptocurrencyexchange.model.Crypto;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeCurrency;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeCurrencyB;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeRequest;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeResponse;
import com.tadeeek.cryptocurrencyexchange.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/currencies")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping(value = "/{currency}")
    public Mono<Crypto> getCurrencies(@PathVariable String currency) {
        return cryptoService.getCurrencies(currency);
    }

    @GetMapping(value = "/{currency}", params = { "filter" })
    public Mono<Crypto> getFilteredCrypto(@PathVariable String currency, @RequestParam List<String> filter) {
        return cryptoService.getFilteredCurrencies(currency, filter);
    }

    @PostMapping("/exchange")
    public Mono<ExchangeResponse> exchange(@RequestBody ExchangeRequest exchangeRequest) {
        Mono<Map<String, BigDecimal>> rates = cryptoService
                .getFilteredCurrencies(exchangeRequest.getFrom(), exchangeRequest.getTo()).map(it -> it.getRates());

        Mono<List<ExchangeCurrency>> exchangeCurrencies = rates.map(el -> {
            List<ExchangeCurrency> exchangeCurrencies1 = new ArrayList<>();
            for (Map.Entry<String, BigDecimal> entry : el.entrySet()) {
                // Getting data
                String from = entry.getKey();
                BigDecimal rate = entry.getValue();
                BigDecimal amount = exchangeRequest.getAmount();
                BigDecimal commission = new BigDecimal(0.01);

                // Exchange
                BigDecimal fee = amount.divide(rate,4, RoundingMode.HALF_UP).multiply(commission);
                BigDecimal result = amount.divide(rate,2, RoundingMode.HALF_UP).add(fee);

                ExchangeCurrency exchangeCurrency = new ExchangeCurrency(from, rate, amount,  fee,  result);
                exchangeCurrencies1.add(exchangeCurrency);
            }
            return exchangeCurrencies1;
        });

        Mono<ExchangeResponse> test = exchangeCurrencies.map(el -> new ExchangeResponse(exchangeRequest.getFrom(),el));

        return test;
    }

}
