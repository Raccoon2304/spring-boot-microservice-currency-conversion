package com.in28minutes.springboot.microservice.example.currencyconversion;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

  private final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);

  @Autowired
  private CurrencyExchangeServiceProxy proxy;

  @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionBean convertCurrency(@PathVariable String from,
                                                @PathVariable String to,
                                                @PathVariable BigDecimal quantity) {

    ExchangeValue response = proxy.retrieveExchangeValue(from, to);

    LOGGER.info("{}", response);

    return new CurrencyConversionBean(response.getId(),
          from,
          to,
          response.getConversionMultiple(),
          quantity,
          quantity.multiply(response.getConversionMultiple()),
          response.getPort()
    );
  }
}
