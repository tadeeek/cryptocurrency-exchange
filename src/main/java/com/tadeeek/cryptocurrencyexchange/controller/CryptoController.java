package com.tadeeek.cryptocurrencyexchange.controller;

import com.tadeeek.cryptocurrencyexchange.model.Crypto;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeCurrency;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeRequest;
import com.tadeeek.cryptocurrencyexchange.model.ExchangeResponse;
import com.tadeeek.cryptocurrencyexchange.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .getFilteredCurrencies(exchangeRequest.getFrom(), exchangeRequest.getTo()).map(Crypto::getRates);

        Mono<List<ExchangeCurrency>> exchangeCurrencies = rates.map(el -> el.entrySet().parallelStream().map(it->{
                    String from = it.getKey();
                    BigDecimal rate = it.getValue();
                    BigDecimal amount = exchangeRequest.getAmount();
                    BigDecimal commission = new BigDecimal("0.01");
                    BigDecimal fee = amount.divide(rate,4, RoundingMode.HALF_UP).multiply(commission);
                    BigDecimal result = amount.divide(rate,2, RoundingMode.HALF_UP).add(fee);

                    ExchangeCurrency exchangeCurrency = new ExchangeCurrency.Builder()
                            .from(from)
                            .rate(rate)
                            .amount(amount)
                            .fee(fee)
                            .result(result)
                            .build();

                    //Check for actual thread
                    System.out.println(Thread.currentThread().getId());
                    return exchangeCurrency;

                }).collect(Collectors.toList()));

        Mono<ExchangeResponse> exchangeResponse = exchangeCurrencies.map(el -> new ExchangeResponse(exchangeRequest.getFrom(),el));

        return exchangeResponse;
    }

}
