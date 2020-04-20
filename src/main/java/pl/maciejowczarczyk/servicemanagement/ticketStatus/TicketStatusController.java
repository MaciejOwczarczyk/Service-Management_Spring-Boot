package pl.maciejowczarczyk.servicemanagement.ticketStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicketRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/ticketStatus")
@RequiredArgsConstructor
public class TicketStatusController {

    private final TicketStatusRepository ticketStatusRepository;
    private final ServiceTicketRepository serviceTicketRepository;

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

        List<TicketStatus> ticketStatuses = ticketStatusRepository.findAll();
        boolean check = ticketStatuses.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(ticketStatus.getName().toLowerCase()));
        if (check) {
            model.addAttribute("ticketStatusFail", true);
            return "ticketStatus/addTicketStatus";
        }
        ticketStatusRepository.save(ticketStatus);
        return "redirect:showAll";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        List<TicketStatus> ticketStatuses = ticketStatusRepository.findAll();
        model.addAttribute("ticketStatuses", ticketStatuses);
        return "ticketStatus/showAllTicketStatuses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("ticketStatus", ticketStatusRepository.findAllById(id));
        return "ticketStatus/addTicketStatus";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid TicketStatus ticketStatus, BindingResult result, Model model,  @RequestParam String oldName) {
        if (ticketStatus.getName().equals(oldName)) {
            ticketStatusRepository.save(ticketStatus);
            return "redirect:../showAll";
        } else if (result.hasErrors()) {
            model.addAttribute("ticketStatus", ticketStatusRepository.findAllById(ticketStatus.getId()));
            return "ticketStatus/addTicketStatus";
        }
        List<TicketStatus> ticketStatuses = ticketStatusRepository.findAll();
        boolean check = ticketStatuses.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(ticketStatus.getName().toLowerCase()));
        if (check) {
            model.addAttribute("ticketStatusFail", true);
            model.addAttribute("ticketStatus", ticketStatusRepository.findAllById(ticketStatus.getId()));
            return "ticketStatus/addTicketStatus";
        }

        ticketStatusRepository.save(ticketStatus);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        TicketStatus ticketStatus = ticketStatusRepository.findAllById(id);
        List<ServiceTicket> serviceTickets = serviceTicketRepository.findAllByTicketStatus(ticketStatus);

        if (serviceTickets.size() > 0) {
            model.addAttribute("ticketStatuses", ticketStatusRepository.findAll());
            model.addAttribute("ticketStatusFailed", true);
            return "ticketStatus/showAllTicketStatuses";
        }
        ticketStatusRepository.delete(ticketStatus);
        return "redirect:../showAll";
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

}
