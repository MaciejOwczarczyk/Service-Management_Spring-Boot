package pl.maciejowczarczyk.servicemanagement.ticketType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

    TicketType findAllById(Long id);

    TicketType findAllByName(String name);
}
