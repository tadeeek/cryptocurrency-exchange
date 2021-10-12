## Cryptocurrency exchange

A service application which downloads cryptocurrency rates from CoinAPI and allows to calculate exchange amount from base cryptocurrency. The main purpose of writing this program was to get to know WebFlux reactive programming.

## Table of contents

- [General info](#general-info)
- [Technologies](#technologies)
- [Status](#status)

## General info

This application supports following REST API requests:

- `GET: /currencies/{currency}`

This method will return a list of rates for the specified cryptocurrency {currency} e.g. "BTC"

- `GET: /currencies/{currency1}?filter={currency2}?filter={currency3}`

This method will return a list of rates for the specified cryptocurrency {currency1} but limited to the filter parametes {currency2}, {currency3} etc.

- `POST: /currencies/exchange`

Allows to calculate exchange result from one cryptocurrency to others, in the given amount at the current exchange rate. A commision of 1% of the base(input) currency is added to the result.

Where example request body is:

`{
    "from": "BTC",
    "to": [
        "USD",
        "ETH",
        "SLP",
        "BCH",
        "BAND"
    ],
    "amount": 1000
}`


## Technologies

- Java
- Spring
- Spring WebFlux

## Status

Project is: _in development_
