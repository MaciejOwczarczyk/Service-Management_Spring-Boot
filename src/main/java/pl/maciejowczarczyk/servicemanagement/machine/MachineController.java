package pl.maciejowczarczyk.servicemanagement.machine;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.company.Company;
import pl.maciejowczarczyk.servicemanagement.company.CompanyServiceImpl;
import pl.maciejowczarczyk.servicemanagement.machineType.MachineType;
import pl.maciejowczarczyk.servicemanagement.machineType.MachineTypeServiceImpl;
import pl.maciejowczarczyk.servicemanagement.producer.Producer;
import pl.maciejowczarczyk.servicemanagement.producer.ProducerServiceImpl;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicketRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/machine")
@RequiredArgsConstructor
public class MachineController {

    private final MachineServiceImpl machineService;
    private final CompanyServiceImpl companyService;
    private final ProducerServiceImpl producerService;
    private final MachineTypeServiceImpl machineTypeService;
    private final ServiceTicketRepository serviceTicketRepository;


    @GetMapping("/showAll")
    public String showAll(Model model) {
        List<Machine> machines = machineService.findAllMachines();
        model.addAttribute("machines", machines);
        return "machine/showAllMachines";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("machine", new Machine());
        return "machine/addMachine";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid Machine machine, BindingResult result) {
        if (result.hasErrors()) {
            return "machine/addMachine";
        }
        machineService.saveMachine(machine);
        return "redirect:showAll";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Machine machine = machineService.findMachineById(id);
        model.addAttribute("machine", machine);
        return "machine/addMachine";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid Machine machine, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("machine", machineService.findMachineById(id));
            return "machine/addMachine";
        }
        machineService.saveMachine(machine);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Machine machine = machineService.findMachineById(id);
        List<ServiceTicket> serviceTickets = serviceTicketRepository.findAll();
        boolean check = serviceTickets.stream().
                map(o -> o.getMachine().getId()).anyMatch(o -> o.equals(machine.getId()));

        if (check) {
            model.addAttribute("failedMachine", true);
            model.addAttribute("machines", machineService.findAllMachines());
            return "machine/showAllMachines";
        }
        machineService.deleteMachine(machine);
        return "redirect:../showAll";
    }

    @ModelAttribute("companies")
    public List<Company> fetchAllCompanies() {
        return companyService.findAllCompanies();
    }

    @ModelAttribute("producers")
    public List<Producer> fetchAllProducers() {
        return producerService.findAllProducers();
    }

    @ModelAttribute("machineTypes")
    public List<MachineType> fetchAllMachineType() {
        return machineTypeService.findAllMachineTypes();
    }


    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }


}
