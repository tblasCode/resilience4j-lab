package com.spring.resilience4j.nonreactive.services;

import com.spring.resilience4j.nonreactive.model.Flight;
import com.spring.resilience4j.nonreactive.model.SearchRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FlightSearchService {

  Logger LOG = LoggerFactory.getLogger(FlightSearchService.class);

  @CircuitBreaker(name = "backend", fallbackMethod = "fallBack")
  public List<Flight> searchFlights(SearchRequest request) {

    System.out.println("Flight search init");

    UriComponents builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/greeting").queryParam("time",request.getFlightDate()).build();

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response
        = restTemplate.getForEntity(builder.toUriString(), String.class);


    List<Flight> flights = Arrays.asList(
        new Flight("XY 765", request.getFlightDate(), request.getFrom(), request.getTo()),
        new Flight("XY 781", request.getFlightDate(), request.getFrom(), request.getTo()),
        new Flight("XY 732", request.getFlightDate(), request.getFrom(), request.getTo()),
        new Flight("XY 746", request.getFlightDate(), request.getFrom(), request.getTo())
    );

    return flights;
  }

  public List<Flight>  fallBack(SearchRequest request, Exception e) {
    LOG.error("fallBack::{}", e);

    return Arrays.asList(
        new Flight("XY 765", "request.getFlightDate()", "request.getFrom()", "request.getTo()")
    );
  }
}
