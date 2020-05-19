package pl.maciejowczarczyk.servicemanagement.planner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {

    private final PlannerRepository plannerRepository;

    @Override
    public List<Planner> findPlannersByServiceTicket_TicketTypeName(String name) {
        return plannerRepository.findAllByServiceTicket_TicketTypeName(name);
    }

    @Override
    public Planner findPlannerByServiceTicket(ServiceTicket serviceTicket) {
        return plannerRepository.findByServiceTicket(serviceTicket);
    }

    @Override
    public Set<Planner> findPlannersByServiceTicket(ServiceTicket serviceTicket) {
        return plannerRepository.findAllByServiceTicket(serviceTicket);
    }

    @Override
    public Set<Planner> findPlannersByUserUsername(String username) {
        return plannerRepository.findAllByUserUsername(username);
    }

    @Override
    public Planner findPlannerById(Long id) {
        return plannerRepository.findAllById(id);
    }

    @Override
    public Planner savePlanner(Planner planner) {
        return plannerRepository.save(planner);
    }

    @Override
    public void deletePlanner(Planner planner) {
        plannerRepository.delete(planner);
    }

    @Override
    public List<Planner> findAllPlanners() {
        return plannerRepository.findAll();
    }
}
