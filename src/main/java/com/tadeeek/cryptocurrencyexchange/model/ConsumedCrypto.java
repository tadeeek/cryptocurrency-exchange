package com.tadeeek.cryptocurrencyexchange.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConsumedCrypto {
    private String base;
    private List<ConsumedCryptoRates> rates;


}
