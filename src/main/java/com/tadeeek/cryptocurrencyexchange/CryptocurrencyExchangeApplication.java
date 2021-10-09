package com.tadeeek.cryptocurrencyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CryptocurrencyExchangeApplication {

	@Bean
	public WebClient.Builder getWebClientBuilder() {
		// for development purposes only, later change to app prop
		String apikey = "02F6E779-26AD-4038-910D-F11A343B5912";
		String url = "http://rest-sandbox.coinapi.io/v1/exchangerate/";
		return WebClient.builder().baseUrl(url).defaultHeader("X-CoinAPI-Key", apikey);
	}

	public static void main(String[] args) {
		SpringApplication.run(CryptocurrencyExchangeApplication.class, args);
	}

}
