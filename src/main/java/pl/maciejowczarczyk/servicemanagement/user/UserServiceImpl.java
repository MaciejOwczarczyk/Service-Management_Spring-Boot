package pl.maciejowczarczyk.servicemanagement.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.maciejowczarczyk.servicemanagement.role.Role;
import pl.maciejowczarczyk.servicemanagement.role.RoleServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User findUserByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        if (user.getRoles().size() == 0) {
            Role userRole = roleService.findRoleByName("ROLE_ADMIN");
            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        }
        userRepository.save(user);
    }

    @Override
    public void activateUser(User user) {
        if (!user.isEnabled()) {
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean containsUser(User user) {
        return findAllUsers().contains(user);
    }

    @Override
    public List<User> findAllUsersByRole(Role role) {
        return userRepository.findAllByRoles(role);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findAllById(id);
    }
}
