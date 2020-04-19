package pl.maciejowczarczyk.servicemanagement.planner;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;

import java.util.List;
import java.util.Set;

public interface PlannerRepository extends JpaRepository<Planner, Long> {

    List<Planner> findAllByServiceTicket_TicketTypeName(String name);
    Planner findByServiceTicket(ServiceTicket serviceTicket);
    Set<Planner> findAllByServiceTicket(ServiceTicket serviceTicket);
    Set<Planner> findAllByUserUsername(String username);
    Planner findAllById(Long id);
}
