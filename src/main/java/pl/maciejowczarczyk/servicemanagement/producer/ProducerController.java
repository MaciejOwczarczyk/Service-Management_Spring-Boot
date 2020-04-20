package pl.maciejowczarczyk.servicemanagement.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.country.Country;
import pl.maciejowczarczyk.servicemanagement.country.CountryRepository;
import pl.maciejowczarczyk.servicemanagement.machine.Machine;
import pl.maciejowczarczyk.servicemanagement.machine.MachineRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerRepository producerRepository;
    private final CountryRepository countryRepository;
    private final MachineRepository machineRepository;

    @GetMapping("/showAll")
    public String showAll(Model model) {
        List<Producer> producers = producerRepository.findAll();
        model.addAttribute("producers", producers);
        return "producer/showAllProducers";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("producer", new Producer());
        return "producer/addProducer";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid Producer producer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "producer/addProducer";
        }
        List<Producer> producers = producerRepository.findAll();
        boolean check = producers.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(producer.getName().toLowerCase()));
        if (check) {
            model.addAttribute("producerFail", true);
            model.addAttribute("producer", producer);
            return "producer/addProducer";
        }
        producerRepository.save(producer);
        return "redirect:showAll";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Producer> producer = producerRepository.findById(id);
        model.addAttribute("producer", producer);
        return "producer/addProducer";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid Producer producer, BindingResult result) {
        if (result.hasErrors()) {
            return "producer/addProducer";
        }
        producerRepository.save(producer);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Producer producer = producerRepository.findAllById(id);
        List<Machine> machines = producer.getMachines();
        boolean check = machines.stream().map(Machine::getProducer).anyMatch(o -> o.equals(producer));

        if (check) {
            model.addAttribute("failedProducer", true);
            model.addAttribute("producers", producerRepository.findAll());
            return "producer/showAllProducers";
        }
        producerRepository.delete(producer);
        return "redirect:../showAll";
    }

    @ModelAttribute("countries")
    public List<Country> fetchAllCountries() {
        return countryRepository.findAll();
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }
}
