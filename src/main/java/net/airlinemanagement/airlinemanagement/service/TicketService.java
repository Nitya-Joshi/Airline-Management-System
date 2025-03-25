package net.airlinemanagement.airlinemanagement.service;

import net.airlinemanagement.airlinemanagement.entity.Flight;
import net.airlinemanagement.airlinemanagement.entity.Ticket;
import net.airlinemanagement.airlinemanagement.repository.FlightRepository;
import net.airlinemanagement.airlinemanagement.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    public TicketService(TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
    }

    public Ticket bookTicket(String passengerName, String email, Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        Ticket ticket = new Ticket();
        ticket.setPassengerName(passengerName);
        ticket.setEmail(email);
        ticket.setFlight(flight);

        flight.setAvailableSeats(flight.getAvailableSeats() - 1); // Reduce seat count
        flightRepository.save(flight);
        return ticketRepository.save(ticket);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public void cancelTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Flight flight = ticket.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + 1); // Increase seat count
        flightRepository.save(flight);

        ticketRepository.deleteById(id);
    }
}
