package net.airlinemanagement.airlinemanagement.repository;

import net.airlinemanagement.airlinemanagement.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByOrderByDepartureTimeAsc();
}
