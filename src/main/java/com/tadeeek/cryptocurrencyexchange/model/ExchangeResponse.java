package com.tadeeek.cryptocurrencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeResponse {

    private String from;
    private List<ExchangeCurrency> exchangedCurrencies;



}
