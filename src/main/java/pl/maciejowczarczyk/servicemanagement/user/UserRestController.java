package pl.maciejowczarczyk.servicemanagement.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.maciejowczarczyk.servicemanagement.role.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/techniciansList/get")
    public List<DTOUser> users() {
        List<User> users = userRepository.findAllByRoles(roleRepository.findByName("ROLE_ENGINEER"));
        List<DTOUser> DtoUsers = new ArrayList<>();

        for (User user : users) {
            DTOUser dtoUser = new DTOUser();
            dtoUser.setTitle(user.getFullName());
            dtoUser.setId(user.getId());
            DtoUsers.add(dtoUser);
        }

        DtoUsers.sort((s1, s2) -> s1.getTitle().compareToIgnoreCase(s2.getTitle()));
        return DtoUsers;
    }
}
