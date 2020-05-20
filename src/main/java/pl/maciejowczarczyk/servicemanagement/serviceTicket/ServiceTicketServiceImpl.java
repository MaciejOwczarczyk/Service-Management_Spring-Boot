package pl.maciejowczarczyk.servicemanagement.serviceTicket;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.ticketStatus.TicketStatus;
import pl.maciejowczarczyk.servicemanagement.ticketType.TicketType;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceTicketServiceImpl implements ServiceTicketService {

    private final ServiceTicketRepository serviceTicketRepository;

    @Override
    public ServiceTicket findServiceTicketById(Long id) {
        return serviceTicketRepository.findAllById(id);
    }

    @Override
    public List<ServiceTicket> findAllServiceTickets() {
        return serviceTicketRepository.findAll();
    }

    @Override
    public void saveServiceTicket(ServiceTicket serviceTicket) {
        serviceTicketRepository.save(serviceTicket);
    }

    @Override
    public Set<ServiceTicket> findAllServiceTicketsByTicketStatusName(String name) {
        return serviceTicketRepository.findAllByTicketStatusName(name);
    }

    @Override
    public List<ServiceTicket> findAllServiceTicketsByTicketTypeName(String name) {
        return serviceTicketRepository.findAllByTicketTypeName(name);
    }

    @Override
    public ServiceTicket findServiceTicketByPlanner(Planner planner) {
        return serviceTicketRepository.findAllByPlanners(planner);
    }

    @Override
    public List<ServiceTicket> findAllServiceTicketsByTicketType(TicketType ticketType) {
        return serviceTicketRepository.findAllByTicketType(ticketType);
    }

    @Override
    public List<ServiceTicket> findAllServiceTicketsByTicketStatus(TicketStatus ticketStatus) {
        return serviceTicketRepository.findAllByTicketStatus(ticketStatus);
    }
}
