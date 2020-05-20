package pl.maciejowczarczyk.servicemanagement.serviceTicket;

import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.ticketStatus.TicketStatus;
import pl.maciejowczarczyk.servicemanagement.ticketType.TicketType;

import java.util.List;
import java.util.Set;

public interface ServiceTicketService {

    ServiceTicket findServiceTicketById(Long id);
    List<ServiceTicket> findAllServiceTickets();
    void saveServiceTicket(ServiceTicket serviceTicket);
    Set<ServiceTicket> findAllServiceTicketsByTicketStatusName(String name);
    List<ServiceTicket> findAllServiceTicketsByTicketTypeName(String name);
    ServiceTicket findServiceTicketByPlanner(Planner planner);
    List<ServiceTicket> findAllServiceTicketsByTicketType(TicketType ticketType);
    List<ServiceTicket> findAllServiceTicketsByTicketStatus(TicketStatus ticketStatus);
}
