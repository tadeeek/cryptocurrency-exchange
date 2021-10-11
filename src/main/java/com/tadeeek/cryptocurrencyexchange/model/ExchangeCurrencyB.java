package com.tadeeek.cryptocurrencyexchange.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeCurrencyB {

    private String name;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal result;
    private BigDecimal fee;

    private ExchangeCurrencyB() {

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

        public ExchangeCurrencyB build() {
            ExchangeCurrencyB exchangeCurrencyB = new ExchangeCurrencyB();
            exchangeCurrencyB.name = this.name;
            exchangeCurrencyB.rate = this.rate;
            exchangeCurrencyB.amount = this.amount;
            exchangeCurrencyB.result = this.result;
            exchangeCurrencyB.fee = this.fee;
            return exchangeCurrencyB;
        }

    }

}
