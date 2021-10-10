package com.tadeeek.cryptocurrencyexchange.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
public class Crypto {

    private String source;
    private Map<String, BigDecimal> rates;

    //DTO usage
    public Crypto convertConsumedCryptoToCrypto(ConsumedCrypto consumedCrypto) {
        Crypto crypto = new Crypto();
        List<ConsumedCryptoRates> consumedCryptoRates = consumedCrypto.getRates();

        //Mapping fields
        crypto.setSource(consumedCrypto.getAsset_id_base());

        //Converting list of ConsumedCryptoRates to a Map - can throw IllegalStateException if some API rate name is duplicated... deal it with it later
        rates = consumedCryptoRates.
                stream().
                collect(Collectors.toMap(ConsumedCryptoRates::getAsset_id_quote, ConsumedCryptoRates::getRate));
        crypto.setRates(rates);

        return crypto;
    }

    //Filter and DTO
    public Crypto convertConsumedCryptoToCryptoAndFilter(ConsumedCrypto consumedCrypto, List<String> filter) {

        Crypto crypto = new Crypto();
        List<ConsumedCryptoRates> consumedCryptoRates = consumedCrypto.getRates();

        //Mapping fields
        crypto.setSource(consumedCrypto.getAsset_id_base());

        //Converting list of ConsumedCryptoRates to a Map - can throw IllegalStateException if some API rate name is duplicated... deal it with it later
        //Filter also
        rates = consumedCryptoRates.stream()
                .filter(rate -> filter.contains(rate.getAsset_id_quote()))
                .collect(Collectors.toMap(ConsumedCryptoRates::getAsset_id_quote, ConsumedCryptoRates::getRate));
        crypto.setRates(rates);

        return crypto;
    }
}
