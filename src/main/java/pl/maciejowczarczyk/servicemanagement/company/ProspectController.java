package pl.maciejowczarczyk.servicemanagement.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prospect")
@RequiredArgsConstructor
public class ProspectController {

    private final CompanyServiceImpl companyService;

    @PostMapping("/add")
    public String add(@ModelAttribute @Validated({ProspectValidationGroup.class}) Company company, BindingResult result) {
        if (!company.isProspect()) {
            return "forward:/company/add";
        }
        if (result.hasErrors()) {
            return "company/addCompany";
        }

        company.setSalesman(null);
        company.setProspect(true);
        companyService.saveCompany(company);
        return "redirect:/company/showAll";
    }

//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable Long id, Model model) {
//        model.addAttribute("company", companyService.findCompanyById(id));
//        return "company/addCompany";
//    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Validated({ProspectValidationGroup.class}) Company company, BindingResult result) {
        if (!company.isProspect()) {
            return "forward:/company/add";
        }
        if (result.hasErrors()) {
            return "company/addCompany";
        }
        company.setProvince(null);
        company.setSalesman(null);
        companyService.saveCompany(company);
        return "redirect:/company/showAll";
    }


}
