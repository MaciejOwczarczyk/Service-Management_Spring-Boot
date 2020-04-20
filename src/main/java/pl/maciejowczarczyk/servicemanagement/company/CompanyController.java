package pl.maciejowczarczyk.servicemanagement.company;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.maciejowczarczyk.servicemanagement.machine.Machine;
import pl.maciejowczarczyk.servicemanagement.machine.MachineRepository;
import pl.maciejowczarczyk.servicemanagement.machineType.MachineType;
import pl.maciejowczarczyk.servicemanagement.machineType.MachineTypeRepository;
import pl.maciejowczarczyk.servicemanagement.producer.Producer;
import pl.maciejowczarczyk.servicemanagement.producer.ProducerRepository;
import pl.maciejowczarczyk.servicemanagement.province.Province;
import pl.maciejowczarczyk.servicemanagement.province.ProvinceRepository;
import pl.maciejowczarczyk.servicemanagement.salesman.Salesman;
import pl.maciejowczarczyk.servicemanagement.salesman.SalesmanRepository;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicketRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final SalesmanRepository salesmanRepository;
    private final ProvinceRepository provinceRepository;
    private final MachineRepository machineRepository;
    private final ServiceTicketRepository serviceTicketRepository;
    private final ProducerRepository producerRepository;
    private final MachineTypeRepository machineTypeRepository;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("company", new Company());
        return "company/addCompany";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Validated({CompanyValidationGroup.class}) Company company, BindingResult result, Model model) {
        List<Company> companies = companyRepository.findAll();
        boolean checkName = companies.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(company.getName().toLowerCase()));
        boolean checkEmail = companies.stream().map(o -> o.getEmail().toLowerCase()).anyMatch(o -> o.equals(company.getEmail().toLowerCase()));
        boolean checkNIP = companies.stream().map(Company::getNip).anyMatch(o -> o.equals(company.getNip()));
        if (checkName) {
            model.addAttribute("addNameFail", true);
            return "company/addCompany";
        } else if (checkEmail) {
            model.addAttribute("addEmailFail", true);
            return "company/addCompany";
        } else if (checkNIP) {
            model.addAttribute("addNipFail", true);
            return "company/addCompany";
        } else if (company.isProspect()) {
            return "forward:/prospect/add";
        } else if (result.hasErrors()) {
            return "company/addCompany";
        }
        companyRepository.save(company);
        return "redirect:showAll";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        return "company/showAllCompanies";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Company company = companyRepository.findAllById(id);
        model.addAttribute("company", company);
        return "company/addCompany";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Validated({CompanyValidationGroup.class}) Company company, BindingResult result) {
        if (company.isProspect()) {
            return "forward:/prospect/edit/" + id;
        }
        if (result.hasErrors()) {
            return "company/addCompany";
        }
        companyRepository.save(company);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Company company = companyRepository.findAllById(id);
        List<Machine> machines = machineRepository.findAll();
        boolean check = machines.stream().
                map(o -> o.getCompany().getId()).anyMatch(o -> o.equals(company.getId()));

        if (check) {
            model.addAttribute("failedCompany", true);
            model.addAttribute("companies", companyRepository.findAll());
            return "company/showAllCompanies";
        }
        companyRepository.delete(company);
        return "redirect:../showAll";
    }

    @GetMapping("/showMachines/{id}")
    public String showMachines(@PathVariable Long id, Model model) {
        List<Machine> machines = machineRepository.findAllByCompany(companyRepository.findAllById(id));
        model.addAttribute("companyId", id);
        model.addAttribute("machines", machines);
        return "company/showMachines";
    }

    @GetMapping("/delete/machine/{id}/{companyId}")
    public String deleteMachine(@PathVariable Long id, @PathVariable Long companyId, Model model, RedirectAttributes redirectAttributes) {
        Machine machine = machineRepository.findAllById(id);
        List<ServiceTicket> serviceTickets = serviceTicketRepository.findAll();
        boolean check = serviceTickets.stream().
                map(o -> o.getMachine().getId()).anyMatch(o -> o.equals(machine.getId()));

        if (check) {
            model.addAttribute("machines", machineRepository.findAllByCompanyId(companyId));
            redirectAttributes.addFlashAttribute("failedMachine", true);
            return "redirect:../../../showMachines/" + companyId;
        }
        machineRepository.delete(machine);
        return "redirect:../../../showMachines/" + companyId;
    }

    @GetMapping("/edit/machine/{id}")
    public String editMachine(@PathVariable Long id, Model model) {
        model.addAttribute("machine", machineRepository.findAllById(id));
        return "company/editMachine";
    }

    @PostMapping("/edit/machine/{id}")
    public String editMachine(@PathVariable Long id, @ModelAttribute @Valid Machine machine, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("machine", machineRepository.findAllById(id));
            return "company/editMachine";
        }

        machineRepository.save(machine);
        return "redirect:../../showMachines/" + machine.getCompany().getId();
    }

    @GetMapping("/addNewMachine/{id}")
    public String addMachineToClient(Model model, @PathVariable Long id) {
        model.addAttribute("companyId", id);
        model.addAttribute("machine", new Machine());
        return "company/addNewMachine";
    }

    @PostMapping("/addNewMachine/{id}")
    public String addMachineToClient(@RequestParam Long companyId, @ModelAttribute @Valid Machine machine, BindingResult result, Model model) {
        machine.setCompany(companyRepository.findAllById(companyId));
        if(result.hasErrors()) {
            model.addAttribute("companyId", companyId);
            model.addAttribute("machine", new Machine());
            return "company/addNewMachine";
        }

        machineRepository.save(machine);
        return "redirect:../showMachines/" + companyId;
    }

    @ModelAttribute("salesmen")
    public List<Salesman> fetchAllSalesmen() {
        return salesmanRepository.findAll();
    }

    @ModelAttribute("provinces")
    public List<Province> fetchAllProvinces() {
        return provinceRepository.findAll();
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

    @ModelAttribute("companies")
    public List<Company> fetchAllCompanies() {
        return companyRepository.findAll();
    }

    @ModelAttribute("producers")
    public List<Producer> fetchAllProducers() {
        return producerRepository.findAll();
    }

    @ModelAttribute("machineTypes")
    public List<MachineType> fetchAllMachineType() {
        return machineTypeRepository.findAll();
    }

}
