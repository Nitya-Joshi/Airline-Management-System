package net.airlinemanagement.airlinemanagement.controller;

import net.airlinemanagement.airlinemanagement.entity.Ticket;
import net.airlinemanagement.airlinemanagement.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> bookTicket(@RequestParam String passengerName,
                                             @RequestParam String email,
                                             @RequestParam Long flightId) {
        Ticket ticket = ticketService.bookTicket(passengerName, email, flightId);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.ok("Ticket cancelled successfully");
    }
}
