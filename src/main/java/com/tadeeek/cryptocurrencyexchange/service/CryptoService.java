package com.tadeeek.cryptocurrencyexchange.service;
import com.tadeeek.cryptocurrencyexchange.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private final WebClient webClient;

    @Autowired
    public CryptoService(WebClient.Builder webClientBuilder, @Value("${crypto-apikey}") String apikey) {
        String url = "http://rest-sandbox.coinapi.io/v1/exchangerate/";
        this.webClient = webClientBuilder.baseUrl(url).defaultHeader("X-CoinAPI-Key",apikey).build();
    }
    @Autowired
    private Crypto crypto;

    public Mono<Crypto> getCurrencies(String currency) {

        return webClient.get().uri("/{currency}", currency)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ConsumedCrypto.class).map(crypto::convertConsumedCryptoToCrypto);
    }

    public Mono<Crypto> getFilteredCurrencies(String currency, List<String> filter) {
        return webClient.get().uri("/{currency}", currency).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ConsumedCrypto.class)
                .map(it -> crypto.convertConsumedCryptoToCryptoAndFilter(it, filter));
    }

    public Mono<ExchangeResponse> exchange (ExchangeRequest exchangeRequest){
        //Get rates from API
        Mono<Map<String, BigDecimal>> rates = getFilteredCurrencies(exchangeRequest.getFrom(), exchangeRequest.getTo()).map(Crypto::getRates);
        //Create arrays of exchanged currencies and perform calculations
        Mono<List<ExchangeCurrency>> exchangeCurrencies = rates.map(el -> el.entrySet().parallelStream().map(it->{
            String from = it.getKey();
            BigDecimal rate = it.getValue().setScale(6, RoundingMode.HALF_UP);
            BigDecimal amount = exchangeRequest.getAmount();
            BigDecimal commission = new BigDecimal("0.01");
            BigDecimal fee = amount.multiply(rate).multiply(commission).setScale(6, RoundingMode.HALF_UP);
            BigDecimal result = amount.multiply(rate).add(fee).setScale(6, RoundingMode.HALF_UP);

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
        //Prepare response

        return exchangeCurrencies.map(el -> new ExchangeResponse(exchangeRequest.getFrom(),el));
    }

}
