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

    private final WebClient webClient;
    private String url = "http://rest-sandbox.coinapi.io/v1/exchangerate/";
    // for development purposes only
    private String apikey = "02F6E779-26AD-4038-910D-F11A343B5912";

    public CryptoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(url).defaultHeader("X-CoinAPI-Key",apikey).build();
    }
    @Autowired
    private Crypto crypto;

    public Mono<Crypto> getCurrencies(String currency) {
        Mono<Crypto> cryptoMono = webClient.get().uri("/{currency}", currency)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ConsumedCrypto.class).map(crypto::convertConsumedCryptoToCrypto);

        return cryptoMono;
    }

    public Mono<Crypto> getFilteredCurrencies(String currency, List<String> filter) {
        Mono<Crypto> cryptoMono =
                webClient.get().uri("/{currency}", currency).accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(ConsumedCrypto.class)
                        .map(it -> crypto.convertConsumedCryptoToCryptoAndFilter(it, filter));
        return cryptoMono;
    }
}
