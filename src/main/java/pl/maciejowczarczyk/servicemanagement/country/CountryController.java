package pl.maciejowczarczyk.servicemanagement.country;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository countryRepository;
    private final ProducerRepository producerRepository;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("country", new Country());
        return "country/addCountry";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid Country country, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "country/addCountry";
        }
        List<Country> countries = countryRepository.findAll();
        boolean check = countries.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(country.getName().toLowerCase()));
        if (check) {
            model.addAttribute("countryFail", true);
            return "country/addCountry";
        }
        countryRepository.save(country);
        return "redirect:showAll";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute("countries", countryRepository.findAll());
        return "country/showAllCountries";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("country", countryRepository.findAllById(id));
        return "country/addCountry";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid Country country, BindingResult result, Model model, @RequestParam String oldName) {
        if (country.getName().equals(oldName)) {
            countryRepository.save(country);
            return "redirect:../showAll";
        } else if (result.hasErrors()) {
            model.addAttribute("country", countryRepository.findAllById(country.getId()));
            return "country/addCountry";
        }

        List<Country> countries = countryRepository.findAll();
        boolean check = countries.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(country.getName().toLowerCase()));
        if (check) {
            model.addAttribute("countryFail", true);
            model.addAttribute("country", countryRepository.findAllById(country.getId()));
            return "country/addCountry";
        }

        countryRepository.save(country);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Country country = countryRepository.findAllById(id);
        List<Producer> producers = producerRepository.findAll();
        boolean check = producers.stream().map(o -> o.getCountry().getId()).anyMatch(o -> o.equals(country.getId()));
        if (check) {
            model.addAttribute("countryFailed", true);
            model.addAttribute("countries", countryRepository.findAll());
            return "country/showAllCountries";
        }
        countryRepository.delete(country);
        return "redirect:../showAll";
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

}
