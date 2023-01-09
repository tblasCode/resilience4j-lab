package com.spring.resilience4j.nonreactive.services;

import com.spring.resilience4j.nonreactive.model.Flight;
import com.spring.resilience4j.nonreactive.model.SearchRequest;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FlightSearchService {

  @Retry(name = "flightSearch")
  public List<Flight> searchFlights(SearchRequest request) {

    System.out.println("Flight search init");

    List<Flight> flights = Arrays.asList(
        new Flight("XY 765", request.getFlightDate(), request.getFrom(), request.getTo()),
        new Flight("XY 781", request.getFlightDate(), request.getFrom(), request.getTo()),
        new Flight("XY 732", request.getFlightDate(), request.getFrom(), request.getTo()),
        new Flight("XY 746", request.getFlightDate(), request.getFrom(), request.getTo())
    );

    if("now".equals(request.getFlightDate()))
      throw new RuntimeException();

    return flights;
  }
}
