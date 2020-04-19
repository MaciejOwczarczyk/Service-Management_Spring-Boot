package pl.maciejowczarczyk.servicemanagement.salesman;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.company.Company;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/salesman")
@RequiredArgsConstructor
public class SalesmanController {

    private final SalesmanRepository salesmanRepository;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("salesman", new Salesman());
        return "salesman/addSalesman";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid Salesman salesman, BindingResult result) {
        if (result.hasErrors()) {
            return "salesman/addSalesman";
        }
        salesmanRepository.save(salesman);
        return "redirect:showAll";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute("salesmen", salesmanRepository.findAll());
        return "salesman/showAllSalesmen";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("salesman", salesmanRepository.findById(id));
        return "salesman/addSalesman";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid Salesman salesman, BindingResult result) {
        if (result.hasErrors()) {
            return "salesman/addSalesman";
        }
        salesmanRepository.save(salesman);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Salesman salesman = salesmanRepository.findAllById(id);
        List<Company> companies = salesman.getCompanies();
        boolean check = companies.stream().
                map(o -> o.getSalesman()).anyMatch(o -> o.equals(salesman));
        if (check) {
            model.addAttribute("salesmanFailed", true);
            model.addAttribute("salesmen", salesmanRepository.findAll());
            return "salesman/showAllSalesmen";
        }

        salesmanRepository.deleteById(id);
        return "redirect:../showAll";
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

}
