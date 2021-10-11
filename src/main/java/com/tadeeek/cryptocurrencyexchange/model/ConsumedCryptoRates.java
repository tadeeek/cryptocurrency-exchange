package com.tadeeek.cryptocurrencyexchange.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ConsumedCryptoRates {

    private String time;
    private String asset_id_quote;
    private BigDecimal rate;
}
