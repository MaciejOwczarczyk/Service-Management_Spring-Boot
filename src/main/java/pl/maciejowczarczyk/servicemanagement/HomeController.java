package pl.maciejowczarczyk.servicemanagement;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.planner.PlannerServiceImpl;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicketRepository;
import pl.maciejowczarczyk.servicemanagement.user.CurrentUser;
import pl.maciejowczarczyk.servicemanagement.user.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ServiceTicketRepository serviceTicketRepository;
    private final UserRepository userRepository;
    private final PlannerServiceImpl plannerService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails customUser, Model model) {
        boolean checkOpenTickets = customUser.getAuthorities().stream().anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN") || o.getAuthority().equals("ROLE_USER"));
        if (checkOpenTickets) {
            model.addAttribute("openTickets", serviceTicketRepository.findAllByTicketStatusName("Open").size());
        } else {
            Set<Planner> plannerList = getPlannersByUserUsername(customUser);
            Set<ServiceTicket> serviceTickets = plannerList.stream().map(Planner::getServiceTicket).collect(Collectors.toSet());
            List<ServiceTicket> temp = serviceTickets.stream().filter(o -> o.getTicketStatus().getName().equals("Open")).collect(Collectors.toList());
            model.addAttribute("openTickets", temp.size());
        }

        boolean checkAllTickets = customUser.getAuthorities().stream().anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN") || o.getAuthority().equals("ROLE_USER"));
        if (checkAllTickets) {
            model.addAttribute("allTickets", serviceTicketRepository.findAll().size());
        } else {
            Set<Planner> planners = getPlannersByUserUsername(customUser);
            Set<ServiceTicket> serviceTicketSet = planners.stream().map(Planner::getServiceTicket).collect(Collectors.toSet());
//            List<ServiceTicket> temp2 = serviceTicketSet.stream().filter(o -> o.getTicketStatus().getName().equals("Closed")).collect(Collectors.toList());
            model.addAttribute("allTickets", serviceTicketSet.size());
        }
        return "home";
    }

    @GetMapping("/calendar")
    public String calendar() {
        return "calendar/calendar";
    }

    @ModelAttribute("userDetails")
    public CurrentUser custom(@AuthenticationPrincipal CurrentUser customUser) {
        return customUser;
    }

    @ModelAttribute("percentageWarranty")
    public BigDecimal percentageWarranty(@AuthenticationPrincipal UserDetails customUser, Model model) {
        boolean check = customUser.getAuthorities()
                .stream().anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN") || o.getAuthority().equals("ROLE_USER"));

        if (check) {
            int serviceTicketsSize = serviceTicketRepository.findAll().size();
            if (serviceTicketsSize == 0) {
                return null;
            }
            return getBigDecimal("Warranty", serviceTicketsSize);
        } else {
            Set<Planner> allWarrantyInterventionsForFieldEngineer = getPlannersByUserUsername(customUser)
                    .stream()
                    .filter(o -> o.getServiceTicket().getTicketType().getName().equals("Warranty"))
                    .collect(Collectors.toSet());
            if (allWarrantyInterventionsForFieldEngineer.size() == 0) {
                return null;
            }
            return getBigDecimalForEngineer(customUser, allWarrantyInterventionsForFieldEngineer);
        }
    }

    @ModelAttribute("percentageAfterWarranty")
    public BigDecimal percentageAfterWarranty(@AuthenticationPrincipal UserDetails customUser, Model model) {
        boolean check = customUser.getAuthorities()
                .stream().anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN") || o.getAuthority().equals("ROLE_USER"));

        if (check) {
            int serviceTicketsSize = serviceTicketRepository.findAll().size();
            if (serviceTicketsSize == 0) {
                return null;
            }
            return getBigDecimal("After Warranty", serviceTicketsSize);
        } else {
            Set<Planner> allAfterWarrantyInterventionsForFieldEngineer = getPlannersByUserUsername(customUser)
                    .stream()
                    .filter(o -> o.getServiceTicket().getTicketType().getName().equals("After Warranty"))
                    .collect(Collectors.toSet());
            if (allAfterWarrantyInterventionsForFieldEngineer.size() == 0) {
                return null;
            }
            return getBigDecimalForEngineer(customUser, allAfterWarrantyInterventionsForFieldEngineer);
        }
    }

    @ModelAttribute("percentageAssemble")
    public BigDecimal percentageAssemble(@AuthenticationPrincipal UserDetails customUser, Model model) {
        boolean check = customUser.getAuthorities()
                .stream().anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN") || o.getAuthority().equals("ROLE_USER"));

        if (check) {
            int serviceTicketsSize = serviceTicketRepository.findAll().size();
            if (serviceTicketsSize == 0) {
                return null;
            }
            return getBigDecimal("Assemble", serviceTicketsSize);
        } else {
            Set<Planner> allAssembleInterventionsForFieldEngineer = getPlannersByUserUsername(customUser)
                    .stream()
                    .filter(o -> o.getServiceTicket().getTicketType().getName().equals("Assemble"))
                    .collect(Collectors.toSet());
            if (allAssembleInterventionsForFieldEngineer.size() == 0) {
                return null;
            }
            return getBigDecimalForEngineer(customUser, allAssembleInterventionsForFieldEngineer);
        }
    }

    private BigDecimal getBigDecimal(String typeOfTicket, int serviceTicketsSize) {
        int typeOfServiceTicketsSize;
        float out;
        BigDecimal bigDecimal;
        typeOfServiceTicketsSize = serviceTicketRepository.findAllByTicketTypeName(typeOfTicket).size();
        out = (float) typeOfServiceTicketsSize / serviceTicketsSize * 100;
        bigDecimal = new BigDecimal(Float.toString(out));
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getBigDecimalForEngineer(@AuthenticationPrincipal UserDetails customUser, Set<Planner> allKindOfInterventionsForFieldEngineer) {
        int serviceTicketsSize;
        int warrantyServiceTicketsSize;
        float out;
        BigDecimal bigDecimal;
        Set<Planner> allInterventionsForFieldEngineer = getPlannersByUserUsername(customUser);
        serviceTicketsSize = allInterventionsForFieldEngineer.size();
        warrantyServiceTicketsSize = allKindOfInterventionsForFieldEngineer.size();
        out = (float) warrantyServiceTicketsSize / serviceTicketsSize * 100;
        bigDecimal = new BigDecimal(Float.toString(out));
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    private Set<Planner> getPlannersByUserUsername(@AuthenticationPrincipal UserDetails customUser) {
        return plannerService.findPlannersByUserUsername(customUser.getUsername());
    }


}
