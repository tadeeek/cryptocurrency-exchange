package com.tadeeek.cryptocurrencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exchange {
    private String from;
    private List<String> to;
    private BigDecimal amount;

}
