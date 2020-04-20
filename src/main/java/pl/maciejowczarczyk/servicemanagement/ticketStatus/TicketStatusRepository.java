package pl.maciejowczarczyk.servicemanagement.ticketStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Long> {

    TicketStatus findAllById(Long id);
    TicketStatus findAllByName(String name);
}
