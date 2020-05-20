package pl.maciejowczarczyk.servicemanagement.ticketStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicketServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/ticketStatus")
@RequiredArgsConstructor
public class TicketStatusController {

    private final TicketStatusServiceImpl ticketStatusService;
    private final ServiceTicketServiceImpl serviceTicketService;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("ticketStatus", new TicketStatus());
        return "ticketStatus/addTicketStatus";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid TicketStatus ticketStatus, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ticketStatus/addTicketStatus";
        }

        List<TicketStatus> ticketStatuses = ticketStatusService.findAllTicketStatuses();
        boolean check = ticketStatuses.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(ticketStatus.getName().toLowerCase()));
        if (check) {
            model.addAttribute("ticketStatusFail", true);
            return "ticketStatus/addTicketStatus";
        }
        ticketStatusService.saveTicketStatus(ticketStatus);
        return "redirect:showAll";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        List<TicketStatus> ticketStatuses = ticketStatusService.findAllTicketStatuses();
        model.addAttribute("ticketStatuses", ticketStatuses);
        return "ticketStatus/showAllTicketStatuses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("ticketStatus", ticketStatusService.findTicketStatusById(id));
        return "ticketStatus/addTicketStatus";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid TicketStatus ticketStatus, BindingResult result, Model model,  @RequestParam String oldName) {
        if (ticketStatus.getName().equals(oldName)) {
            ticketStatusService.saveTicketStatus(ticketStatus);
            return "redirect:../showAll";
        } else if (result.hasErrors()) {
            model.addAttribute("ticketStatus", ticketStatusService.findTicketStatusById(ticketStatus.getId()));
            return "ticketStatus/addTicketStatus";
        }
        List<TicketStatus> ticketStatuses = ticketStatusService.findAllTicketStatuses();
        boolean check = ticketStatuses.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(ticketStatus.getName().toLowerCase()));
        if (check) {
            model.addAttribute("ticketStatusFail", true);
            model.addAttribute("ticketStatus", ticketStatusService.findTicketStatusById(ticketStatus.getId()));
            return "ticketStatus/addTicketStatus";
        }

        ticketStatusService.saveTicketStatus(ticketStatus);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        TicketStatus ticketStatus = ticketStatusService.findTicketStatusById(id);
        List<ServiceTicket> serviceTickets = serviceTicketService.findAllServiceTicketsByTicketStatus(ticketStatus);

        if (serviceTickets.size() > 0) {
            model.addAttribute("ticketStatuses", ticketStatusService.findAllTicketStatuses());
            model.addAttribute("ticketStatusFailed", true);
            return "ticketStatus/showAllTicketStatuses";
        }
        ticketStatusService.deleteTicketStatus(ticketStatus);
        return "redirect:../showAll";
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

}
