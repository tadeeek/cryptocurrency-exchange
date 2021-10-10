package com.tadeeek.cryptocurrencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeCurrency {
    private String name;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal result;
}
