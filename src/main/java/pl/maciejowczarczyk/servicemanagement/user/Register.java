package pl.maciejowczarczyk.servicemanagement.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.maciejowczarczyk.servicemanagement.authority.Authority;
import pl.maciejowczarczyk.servicemanagement.authority.AuthorityRepository;
import pl.maciejowczarczyk.servicemanagement.confirmationToken.ConfirmationToken;
import pl.maciejowczarczyk.servicemanagement.confirmationToken.ConfirmationTokenRepository;
import pl.maciejowczarczyk.servicemanagement.role.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class Register {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserServiceImpl userService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender javaMailSender;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "login/registration";
    }


    @PostMapping("/register")
    public String registerProcess(@ModelAttribute @Valid User user, BindingResult result, Model model, @RequestParam String rePassword) {

        if (result.hasErrors()) {
            return "login/registration";
        }

        List<User> userList = userRepository.findAll();
        boolean checkUserPresence = userList.stream().map(o -> o.getUsername().toLowerCase()).anyMatch(o -> o.equals(user.getUsername().toLowerCase()));

        if (checkUserPresence) {
            model.addAttribute("registerFail", true);
            return "login/registration";
        }

        if (!user.getPassword().equals(rePassword)) {
            model.addAttribute("passwordFail", true);
            return "login/registration";
        }
            userService.saveUser(user);

            for (Role role : user.getRoles()) {
                Authority authority = new Authority();
                authority.setRole(role);
                authority.setUser(user);
                authorityRepository.save(authority);
            }

            ConfirmationToken confirmationToken = new ConfirmationToken();
            confirmationToken.setCreatedDate(new Date());
            confirmationToken.setUser(user);
            confirmationToken.setConfirmationToken(UUID.randomUUID().toString());
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(user.getUsername().toLowerCase());
            simpleMailMessage.setSubject("CompleteRegistration");
            simpleMailMessage.setText("To confirm your account, please click here: " +
                    "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

            javaMailSender.send(simpleMailMessage);

            return "login/checkEmailForConfirmation";
    }

    @GetMapping("/confirm-account")
    public String confirmUserAccount(Model model, @RequestParam("token") String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);

        if (confirmationToken != null) {
            User user = userRepository.findAllById(confirmationToken.getUser().getId());
            userService.activateUser(user);
            return "login/successRegistration";
        } else {
            return "login/invalidRegistration";
        }
    }

}
