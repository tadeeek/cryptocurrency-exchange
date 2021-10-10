package com.tadeeek.cryptocurrencyexchange.model;

import lombok.Data;
import java.util.List;

@Data
public class ConsumedCrypto {
    private String asset_id_base;
    private List<ConsumedCryptoRates> rates;
}
