package com.tadeeek.cryptocurrencyexchange.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.List;

@Data
public class ConsumedCrypto {
    private String asset_id_base;
    private List<ConsumedCryptoRates> rates;


}
