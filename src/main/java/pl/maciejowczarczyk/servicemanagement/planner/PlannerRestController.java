package pl.maciejowczarczyk.servicemanagement.planner;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlannerRestController {

    private final PlannerServiceImpl plannerService;
    private final UserRepository userRepository;

    @GetMapping("/serviceTicketWarranty/get")
    public List<PlannerDTO> getWarrantyTickets() {
        List<Planner> plannerList = plannerService.findPlannersByServiceTicket_TicketTypeName("Warranty");
        List<PlannerDTO> plannerDTOList = new ArrayList<>();

        for (Planner planner : plannerList) {
            PlannerDTO plannerDTO = new PlannerDTO();
            plannerDTO.setId(planner.getId());
            plannerDTO.setStart(planner.getStart());
            plannerDTO.setEnd(planner.getEnd());
            plannerDTO.setResourceId(planner.getUser().getId());
            plannerDTO.setTitle(planner.getServiceTicket().getCompany().getName());
            plannerDTOList.add(plannerDTO);
        }

        return plannerDTOList;
    }

    @GetMapping("/serviceTicketAfterWarranty/get")
    public List<PlannerDTO> getAfterWarrantyTickets() {
        List<Planner> plannerList = plannerService.findPlannersByServiceTicket_TicketTypeName("After Warranty");
        List<PlannerDTO> plannerDTOList = new ArrayList<>();

        for (Planner planner : plannerList) {
            PlannerDTO plannerDTO = new PlannerDTO();
            plannerDTO.setId(planner.getId());
            plannerDTO.setStart(planner.getStart());
            plannerDTO.setEnd(planner.getEnd());
            plannerDTO.setResourceId(planner.getUser().getId());
            plannerDTO.setTitle(planner.getServiceTicket().getCompany().getName());
            plannerDTOList.add(plannerDTO);
        }

        return plannerDTOList;
    }

    @GetMapping("/serviceTicketAssemble/get")
    public List<PlannerDTO> getAssembleTickets() {
        List<Planner> plannerList = plannerService.findPlannersByServiceTicket_TicketTypeName("Assemble");
        List<PlannerDTO> plannerDTOList = new ArrayList<>();

        for (Planner planner : plannerList) {
            PlannerDTO plannerDTO = new PlannerDTO();
            plannerDTO.setId(planner.getId());
            plannerDTO.setStart(planner.getStart());
            plannerDTO.setEnd(planner.getEnd());
            plannerDTO.setResourceId(planner.getUser().getId());
            plannerDTO.setTitle(planner.getServiceTicket().getCompany().getName());
            plannerDTOList.add(plannerDTO);
        }

        return plannerDTOList;
    }

    @PostMapping(value = "/serviceTicket/put/{id}", produces = "application/json")
    public Planner update(@PathVariable Long id, @RequestBody PlannerDTO plannerDTO) {
        Planner planner = plannerService.findPlannerById(id);
        planner.setStart(plannerDTO.getStart());
        planner.setEnd(plannerDTO.getEnd());
        return plannerService.savePlanner(planner);
    }

    @DeleteMapping(value = "/serviceTicket/delete/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        Planner planner = plannerService.findPlannerById(id);
        plannerService.deletePlanner(planner);
    }

    @PostMapping(value = "/serviceTicket/drop/{id}", produces = "application/json")
    public Planner drop(@PathVariable Long id, @RequestBody PlannerDTO plannerDTO) {
        Planner planner = plannerService.findPlannerById(id);
        planner.setStart(plannerDTO.getStart());
        planner.setEnd(plannerDTO.getEnd());
//        planner.setTechnician(technicianRepository.findAllById(plannerDTO.getResourceId()));
        planner.setUser(userRepository.findAllById(plannerDTO.getResourceId()));
        return plannerService.savePlanner(planner);
    }


}
