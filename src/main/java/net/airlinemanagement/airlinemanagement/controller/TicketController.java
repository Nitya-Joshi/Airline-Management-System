package net.airlinemanagement.airlinemanagement.controller;

import net.airlinemanagement.airlinemanagement.entity.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private Map<Long, Ticket> ticketMap = new HashMap<>();
    private Long ticketCounter = 1L; // Ensures unique ticket IDs

    @PostMapping
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket) {
        ticket.setId(ticketCounter); // Assign a unique ID before storing
        ticketMap.put(ticketCounter, ticket);
        ticketCounter++; // Increment counter for next booking
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        if (!ticketMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ticketMap.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        return ResponseEntity.ok(new ArrayList<>(ticketMap.values()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long id) {
        if (!ticketMap.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found");
        }
        ticketMap.remove(id);
        return ResponseEntity.ok("Ticket cancelled successfully");
    }
}
