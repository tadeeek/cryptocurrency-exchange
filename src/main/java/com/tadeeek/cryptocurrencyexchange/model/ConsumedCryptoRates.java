package com.tadeeek.cryptocurrencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ConsumedCryptoRates {

    private String time;
    private String assetIdQuote;
    private BigDecimal rate;
}
