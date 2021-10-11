package com.tadeeek.cryptocurrencyexchange.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeCurrency {

    private String name;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal result;
    private BigDecimal fee;

    private ExchangeCurrency() {

    }

    public static class Builder {

        private String name;
        private BigDecimal rate;
        private BigDecimal amount;
        private BigDecimal result;
        private BigDecimal fee;

        public Builder() {
        }

        public Builder from(String name) {
            this.name = name;
            return this;
        }

        public Builder rate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder result(BigDecimal result) {
            this.result = result;
            return this;
        }

        public Builder fee(BigDecimal fee) {
            this.fee = fee;
            return this;
        }

        public ExchangeCurrency build() {
            ExchangeCurrency exchangeCurrency = new ExchangeCurrency();
            exchangeCurrency.name = this.name;
            exchangeCurrency.rate = this.rate;
            exchangeCurrency.amount = this.amount;
            exchangeCurrency.result = this.result;
            exchangeCurrency.fee = this.fee;
            return exchangeCurrency;
        }

    }

}
