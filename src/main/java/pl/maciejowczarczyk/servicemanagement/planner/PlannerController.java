package pl.maciejowczarczyk.servicemanagement.planner;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.role.RoleRepository;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicketRepository;
import pl.maciejowczarczyk.servicemanagement.user.User;
import pl.maciejowczarczyk.servicemanagement.user.UserRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/planner")
@RequiredArgsConstructor
public class PlannerController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final PlannerRepository plannerRepository;
    private final ServiceTicketRepository serviceTicketRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/add/{id}")
    public String add(@PathVariable Long id, Model model) {
        PlannerModel plannerModel = new PlannerModel();
//        List<Technician> technicians = technicianRepository.findAll();
        List<Planner> planners = new ArrayList<>();

        List<User> users = userRepository.findAllByRoles(roleRepository.findByName("ROLE_ENGINEER"));

        for (User user : users) {
            planners.add(new Planner());
        }

//        for (Technician technician : technicians) {
//            planners.add(new Planner());
//        }
        plannerModel.setPlanners(planners);
        model.addAttribute("id", id);
        model.addAttribute("plannerList", plannerModel);
        return "planner/addIntervention";
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable Long id, @ModelAttribute PlannerModel plannerModel, @RequestParam Long ticketId, Model model) {
        List<Planner> planners = plannerModel.getPlanners();
        List<Planner> plannerList = new ArrayList<>();

        for (Planner planner : planners) {
            if (planner.getStart().length() == 10 & planner.getEnd().length() == 10) {
                LocalDate parseEnd = LocalDate.parse(planner.getEnd(), formatter);
                LocalDate parseStart = LocalDate.parse(planner.getStart(), formatter);

                if (parseStart.isAfter(parseEnd)) {
                    PlannerModel plannerModel1 = new PlannerModel();
//                    List<Technician> technicians = technicianRepository.findAll();
                    List<Planner> plannerArrayList = new ArrayList<>();

                    List<User> users = userRepository.findAllByRoles(roleRepository.findByName("ROLE_ENGINEER"));

                    for (User user : users) {
                        plannerArrayList.add(new Planner());
                    }

//                    for (int i = 0; i < technicians.size() ; i++) {
//                        plannerArrayList.add(new Planner());
//                    }

                    plannerModel1.setPlanners(plannerArrayList);
                    model.addAttribute("dateFailed", true);
                    model.addAttribute("id", ticketId);
                    model.addAttribute("plannerList", plannerModel1);
                    return "planner/addIntervention";
                }
                String newTestStart =  LocalDate.parse(planner.getStart(), formatter).atStartOfDay().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME);
                String newTestEnd = LocalDate.parse(planner.getEnd(), formatter).atStartOfDay().plusHours(10).plusMinutes(59).plusSeconds(59).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME);
                planner.setStart(newTestStart);
                planner.setEnd(newTestEnd);

                plannerList.add(planner);
            }
        }

        for (Planner planner : plannerList) {
            planner.setServiceTicket(serviceTicketRepository.findAllById(ticketId));
            plannerRepository.save(planner);
        }

        return "redirect:/serviceTicket/showAllOpen";
    }

    @ModelAttribute("technicians")
    public List<User> fetchAllUsers() {
        return userRepository.findAllByRoles(roleRepository.findByName("ROLE_ENGINEER"));
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }


}
