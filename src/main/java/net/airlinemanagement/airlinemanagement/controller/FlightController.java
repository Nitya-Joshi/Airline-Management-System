package net.airlinemanagement.airlinemanagement.controller;

import net.airlinemanagement.airlinemanagement.entity.Flight;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final Map<Long, Flight> flightMap = new HashMap<>();

    @PostMapping
    public String createSchedule(@RequestBody Flight flight) {
        flightMap.put(flight.getId(), flight);  // Store flight in the map using its ID
        return "Created flight schedule with ID: " + flight.getId();
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return new ArrayList<>(flightMap.values()); // Return all stored flights
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightMap.getOrDefault(id, null); // Return flight if found, otherwise null
    }

    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable Long id) {
        if (flightMap.containsKey(id)) {
            flightMap.remove(id);
            return "Deleted flight with ID: " + id;
        } else {
            return "Flight not found";
        }
    }
}

