package net.airlinemanagement.airlinemanagement.repository;

import net.airlinemanagement.airlinemanagement.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
