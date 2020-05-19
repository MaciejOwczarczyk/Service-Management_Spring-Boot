package pl.maciejowczarczyk.servicemanagement.province;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.company.Company;
import pl.maciejowczarczyk.servicemanagement.company.CompanyServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/province")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceRepository provinceRepository;
    private final CompanyServiceImpl companyService;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("province", new Province());
        return "province/addProvince";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid Province province, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "province/addProvince";
        }
        List<Province> provinces = provinceRepository.findAll();
        boolean check = provinces.stream().map(o -> o.getName().toLowerCase())
                .anyMatch(o -> o.equals(province.getName().toLowerCase()));
        if (check) {
            model.addAttribute("provinceFailed", true);
            return "province/addProvince";
        }
        provinceRepository.save(province);
        return "redirect:showAll";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute("provinces", provinceRepository.findAll());
        return "province/showAllProvinces";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("province", provinceRepository.findAllById(id));
        return "province/addProvince";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid Province province, BindingResult result, Model model, @RequestParam String oldName) {
        if (province.getName().equals(oldName)) {
            provinceRepository.save(province);
            return "redirect:../showAll";
        } else if (result.hasErrors()) {
            model.addAttribute("province", provinceRepository.findAllById(province.getId()));
            return "province/addProvince";
        }

        List<Province> provinces = provinceRepository.findAll();
        boolean check = provinces.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(province.getName().toLowerCase()));
        if (check) {
            model.addAttribute("provinceFailed", true);
            model.addAttribute("province", provinceRepository.findAllById(province.getId()));
            return "province/addProvince";
        }
        provinceRepository.save(province);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Province province = provinceRepository.findAllById(id);
        List<Company> companies = companyService.findAllCompanies();
        boolean check = companies.stream().map(o -> o.getProvince().getId()).anyMatch(o -> o.equals(province.getId()));
        if (check) {
            model.addAttribute("provinceFailed", true);
            model.addAttribute("provinces", provinceRepository.findAll());
            return "province/showAllProvinces";
        }
        provinceRepository.delete(province);
        return "redirect:../showAll";
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

}
