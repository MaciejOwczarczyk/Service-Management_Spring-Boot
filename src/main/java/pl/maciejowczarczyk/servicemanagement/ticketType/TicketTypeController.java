package pl.maciejowczarczyk.servicemanagement.ticketType;

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
@RequestMapping("/admin/ticketType")
@RequiredArgsConstructor
public class TicketTypeController {

    private final TicketTypeRepository ticketTypeRepository;
    private final ServiceTicketRepository serviceTicketRepository;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("ticketType", new TicketType());
        return "ticketType/addTicketType";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid TicketType ticketType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ticketType/addTicketType";
        }
        List<TicketType> ticketTypes = ticketTypeRepository.findAll();
        boolean check = ticketTypes.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(ticketType.getName().toLowerCase()));
        if (check) {
            model.addAttribute("ticketTypeFail", true);
            return "ticketType/addTicketType";
        }
        ticketTypeRepository.save(ticketType);
        return "redirect:showAll";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute("ticketTypes", ticketTypeRepository.findAll());
        return "ticketType/showAllTicketTypes";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("ticketType", ticketTypeRepository.findAllById(id));
        return "ticketType/addTicketType";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid TicketType ticketType, BindingResult result, Model model, @RequestParam String oldName) {
        if (ticketType.getName().equals(oldName)) {
            ticketTypeRepository.save(ticketType);
            return "redirect:../showAll";
        } else if (result.hasErrors()) {
            model.addAttribute("ticketType", ticketTypeRepository.findAllById(ticketType.getId()));
            return "ticketType/addTicketType";
        }

        List<TicketType> ticketTypes = ticketTypeRepository.findAll();
        boolean check = ticketTypes.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(ticketType.getName().toLowerCase()));
        if (check) {
            model.addAttribute("ticketTypeFail", true);
            model.addAttribute("ticketType", ticketTypeRepository.findAllById(ticketType.getId()));
            return "ticketType/addTicketType";
        }


        ticketTypeRepository.save(ticketType);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        TicketType ticketType = ticketTypeRepository.findAllById(id);
        List<ServiceTicket> serviceTickets = serviceTicketRepository.findAllByTicketType(ticketType);

        if (serviceTickets.size() > 0) {
            model.addAttribute("failedTicketType", true);
            model.addAttribute("ticketTypes", ticketTypeRepository.findAll());
            return "ticketType/showAllTicketTypes";
        }
        ticketTypeRepository.delete(ticketType);
        return "redirect:../showAll";
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }
}
