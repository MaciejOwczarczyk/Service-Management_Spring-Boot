package pl.maciejowczarczyk.servicemanagement.user;

import java.util.List;

public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);

    void activateUser(User user);

    List<User> findAll();
}
