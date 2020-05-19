package pl.maciejowczarczyk.servicemanagement.planner;

import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;

import java.util.List;
import java.util.Set;

public interface PlannerService {

    List<Planner> findPlannersByServiceTicket_TicketTypeName(String name);
    Planner findPlannerByServiceTicket(ServiceTicket serviceTicket);
    Set<Planner> findPlannersByServiceTicket(ServiceTicket serviceTicket);
    Set<Planner> findPlannersByUserUsername(String username);
    Planner findPlannerById(Long id);
    Planner savePlanner(Planner planner);
    void deletePlanner(Planner planner);
    List<Planner> findAllPlanners();
}
