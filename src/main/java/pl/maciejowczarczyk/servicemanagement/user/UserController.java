package pl.maciejowczarczyk.servicemanagement.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.authority.Authority;
import pl.maciejowczarczyk.servicemanagement.authority.AuthorityRepository;
import pl.maciejowczarczyk.servicemanagement.role.Role;
import pl.maciejowczarczyk.servicemanagement.role.RoleRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserServiceImpl userService;

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/showAllUsers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findAllById(id));
        return "user/editUser";
    }

    @PostMapping("/edit/{id}")
    public String add(@PathVariable Long id, @ModelAttribute @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/editUser";
        }

        userRepository.save(user);
        List<Authority> authorities = authorityRepository.findAll();
        for (Authority authority : authorities) {
            if (authority.getUser().getId().equals(user.getId())) {
                authorityRepository.delete(authority);
            }
        }
        for (Role role : user.getRoles()) {
            Authority authority = new Authority();
            authority.setUser(user);
            authority.setRole(role);
            authorityRepository.save(authority);
        }


        return "redirect:../showAll";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("addUser", true);
        return "user/addUser";
    }

    @PostMapping("/add")
    public String add(Model model, @ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/addUser";
        }
        List<User> users = userRepository.findAll();
        boolean check = users.stream().map(User::getUsername).anyMatch(o -> o.equals(user.getUsername()));
        if (check) {
            model.addAttribute("registerFail", true);
            return "user/addUser";
        }
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.saveUser(user);

        for (Role role : user.getRoles()) {
            Authority authority = new Authority();
            authority.setUser(user);
            authority.setRole(role);
            authorityRepository.save(authority);
        }
        return "redirect:showAll";
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return roleRepository.findAll();
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

}
