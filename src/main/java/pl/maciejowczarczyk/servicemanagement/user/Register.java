package pl.maciejowczarczyk.servicemanagement.user;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.authority.Authority;
import pl.maciejowczarczyk.servicemanagement.authority.AuthorityServiceImpl;
import pl.maciejowczarczyk.servicemanagement.confirmationToken.ConfirmationToken;
import pl.maciejowczarczyk.servicemanagement.confirmationToken.ConfirmationTokenServiceImpl;
import pl.maciejowczarczyk.servicemanagement.role.Role;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class Register {

    private final AuthorityServiceImpl authorityService;
    private final UserServiceImpl userService;
    private final ConfirmationTokenServiceImpl confirmationTokenService;
    private final JavaMailSender javaMailSender;

    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "login/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(Model model, @RequestParam String username) {
        User user = userService.findUserByUsername(username);

        if (!userService.containsUser(user)) {
            model.addAttribute("noUser", true);
            return "login/resetPassword";
        } else {

            ConfirmationToken confirmationToken = new ConfirmationToken();
            confirmationToken.setUser(user);
            confirmationToken.setConfirmationToken(UUID.randomUUID().toString());
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(username.toLowerCase());
            simpleMailMessage.setSubject("ResetPassword");
            simpleMailMessage.setText("To reset your password, please click here: " +
                    "http://localhost:8080/resetPasswordProcess/" + confirmationToken.getConfirmationToken());
            javaMailSender.send(simpleMailMessage);
            return "login/checkEmailForResetPassword";
        }
    }

    @GetMapping("/resetPasswordProcess/{token}")
    public String resetPasswordProcess(@PathVariable String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByConfirmationToken(token);
        Calendar calendar = Calendar.getInstance();
        if (confirmationToken == null || (confirmationToken.getExpirationDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return "login/invalidResetPassword";
        } else {
            return "login/resetPasswordProcess";
        }
    }

    @PostMapping("/resetPasswordProcess/{token}")
    public String resetPasswordProcess(@PathVariable String token, Model model,
                                       @RequestParam String password,
                                       @RequestParam String rePassword) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByConfirmationToken(token);
        if (!password.equals(rePassword)) {
            model.addAttribute("passwordFail", true);
            return "login/resetPasswordProcess";
        } else {
            User user = userService.findUserByUsername(confirmationToken.getUser().getUsername().toLowerCase());
            user.setPassword(password);
            userService.saveNewUser(user);

            for (Role role : user.getRoles()) {
                Authority authority = new Authority();
                authority.setRole(role);
                authority.setUser(user);
                authorityService.saveAuthority(authority);
            }
            return "login/resetPasswordSuccess";
        }
    }

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

        List<User> userList = userService.findAllUsers();

        boolean checkUserPresence = userList.stream()
                .map(o -> o.getUsername().toLowerCase())
                .anyMatch(o -> o.equals(user.getUsername().toLowerCase()));

        if (checkUserPresence) {
            model.addAttribute("registerFail", true);
            return "login/registration";
        }

        if (!user.getPassword().equals(rePassword)) {
            model.addAttribute("passwordFail", true);
            return "login/registration";
        }
            userService.saveNewUser(user);

            for (Role role : user.getRoles()) {
                Authority authority = new Authority();
                authority.setRole(role);
                authority.setUser(user);
                authorityService.saveAuthority(authority);
            }

            ConfirmationToken confirmationToken = new ConfirmationToken();
            confirmationToken.setUser(user);
            confirmationToken.setConfirmationToken(UUID.randomUUID().toString());
            confirmationTokenService.saveConfirmationToken(confirmationToken);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(user.getUsername().toLowerCase());
            simpleMailMessage.setSubject("CompleteRegistration");
            simpleMailMessage.setText("To confirm your account, please click here: " +
                    "http://localhost:8080/confirm-account/" + confirmationToken.getConfirmationToken());

            javaMailSender.send(simpleMailMessage);
            return "login/checkEmailForConfirmation";
    }

    @GetMapping("/confirm-account/{token}")
    public String confirmUserAccount(@PathVariable String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByConfirmationToken(token);
        Calendar calendar = Calendar.getInstance();

        if (confirmationToken == null || (confirmationToken.getExpirationDate().getTime() - calendar.getTime().getTime()) <= 0) {
            return "login/invalidRegistration";
        } else {
            User user = userService.findUserById(confirmationToken.getUser().getId());
            userService.activateUser(user);
            return "login/successRegistration";
        }
    }

}
