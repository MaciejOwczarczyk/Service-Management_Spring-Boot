package pl.maciejowczarczyk.servicemanagement.user;

import pl.maciejowczarczyk.servicemanagement.role.Role;

import java.util.List;

public interface UserService {

    User findUserByUsername(String name);
    void saveNewUser(User user);
    void saveUser(User user);
    void activateUser(User user);
    List<User> findAllUsers();
    boolean containsUser(User user);
    List<User> findAllUsersByRole(Role role);
    User findUserById(Long id);

}
