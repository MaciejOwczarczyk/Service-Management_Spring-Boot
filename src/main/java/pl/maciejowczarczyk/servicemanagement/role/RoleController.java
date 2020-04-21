package pl.maciejowczarczyk.servicemanagement.role;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.user.User;
import pl.maciejowczarczyk.servicemanagement.user.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "role/showAllRoles";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("role", new Role());
        return "role/addRole";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid Role role, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "role/addRole";
        }
        List<Role> roles = roleRepository.findAll();
        boolean check = roles.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(role.getName().toLowerCase()));
        if (check) {
            model.addAttribute("roleFail", true);
            model.addAttribute("role", role);
            return "role/addRole";
        }

        roleRepository.save(role);
        return "redirect:showAll";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Role role = roleRepository.findAllById(id);
        Set<User> users = role.getUsers();
        boolean check = users.stream().
                map(User::getRoles).anyMatch(o -> o.contains(role));
        if (check) {
            model.addAttribute("roleFail", true);
            model.addAttribute("roles", roleRepository.findAll());
            return "role/showAllRoles";
        }
        model.addAttribute("role", roleRepository.findAllById(id));
        return "role/addRole";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid Role role, BindingResult result, Model model, @RequestParam String oldName) {
        if (role.getName().equals(oldName)) {
            roleRepository.save(role);
            return "redirect:../showAll";
        } else if (result.hasErrors()) {
            model.addAttribute("role", roleRepository.findAllById(role.getId()));
            return "role/addRole";
        }
        List<Role> roles = roleRepository.findAll();
        boolean check = roles.stream().map(o -> o.getName().toLowerCase()).anyMatch(o -> o.equals(role.getName().toLowerCase()));
        if (check) {
            model.addAttribute("role", roleRepository.findAllById(role.getId()));
            model.addAttribute("roleFail", true);
            return "role/addRole";
        }

        roleRepository.save(role);
        return "redirect:../showAll";

    }

        @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Role role = roleRepository.findAllById(id);
        Set<User> users = role.getUsers();
        boolean check = users.stream().
                map(User::getRoles).anyMatch(o -> o.contains(role));
        if (check) {
            model.addAttribute("roleFail", true);
            model.addAttribute("roles", roleRepository.findAll());
            return "role/showAllRoles";
        }
        roleRepository.delete(role);
        return "redirect:../showAll";
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }




}
