package com.tadeeek.cryptocurrencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
public class ConsumedCryptoRates {

    private String time;
    private String asset_id_quote;
    private BigDecimal rate;
}
