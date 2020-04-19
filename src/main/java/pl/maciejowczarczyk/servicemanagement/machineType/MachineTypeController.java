package pl.maciejowczarczyk.servicemanagement.machineType;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.machine.Machine;
import pl.maciejowczarczyk.servicemanagement.machine.MachineRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/machineType")
@RequiredArgsConstructor
public class MachineTypeController {

    private final MachineTypeRepository machineTypeRepository;
    private final MachineRepository machineRepository;

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute("machineTypes", machineTypeRepository.findAll());
        return "machineType/showAllMachineTypes";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("machineType", new MachineType());
        return "machineType/addMachineType";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid MachineType machineType, BindingResult result, Model model) {
        List<MachineType> machineTypes = machineTypeRepository.findAll();
        boolean check = machineTypes.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(machineType.getName().toLowerCase()));
        if (result.hasErrors()) {
            return "machineType/addMachineType";
        }
        if (check) {
            model.addAttribute("machineTypeFail", true);
            model.addAttribute("machineType", machineType);
            return "machineType/addMachineType";
        }
        machineTypeRepository.save(machineType);
        return "redirect:showAll";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("machineType", machineTypeRepository.findAllById(id));
        return "machineType/addMachineType";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid MachineType machineType, BindingResult result, Model model, @RequestParam String oldName) {
        if (machineType.getName().equals(oldName)) {
            machineTypeRepository.save(machineType);
            return "redirect:../showAll";
        } else if (result.hasErrors()) {
            model.addAttribute("machineType", machineTypeRepository.findAllById(machineType.getId()));
            return "machineType/addMachineType";
        }

        List<MachineType> machineTypes = machineTypeRepository.findAll();
        boolean check = machineTypes.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(machineType.getName().toLowerCase()));
        if (check) {
            model.addAttribute("machineTypeFail", true);
            model.addAttribute("machineType", machineTypeRepository.findAllById(machineType.getId()));
            return "machineType/addMachineType";
        }
        machineTypeRepository.save(machineType);
        return "redirect:../showAll";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        MachineType machineType = machineTypeRepository.findAllById(id);
        List<Machine> machines = machineRepository.findAll();
        boolean check = machines.stream().
                map(o -> o.getMachineType().getId()).anyMatch(o -> o.equals(machineType.getId()));
        if (check) {
            model.addAttribute("failMachineType", true);
            model.addAttribute("machineTypes", machineTypeRepository.findAll());
            return "machineType/showAllMachineTypes";
        }
        machineTypeRepository.delete(machineType);
        return "redirect:../showAll";
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

}
