package com.spring.resilience4j.nonreactive.web;

import com.spring.resilience4j.nonreactive.model.Flight;
import com.spring.resilience4j.nonreactive.model.SearchRequest;
import com.spring.resilience4j.nonreactive.services.FlightSearchService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightsController {

  private final FlightSearchService flightSearchService;

  public FlightsController(FlightSearchService flightSearchService) {
    this.flightSearchService = flightSearchService;
  }

  @GetMapping
  private List<Flight> getAllEmployees(@RequestParam String from, @RequestParam String to, @RequestParam String flightDate) {
    return flightSearchService.searchFlights(new SearchRequest(from, to, flightDate));
  }

}
