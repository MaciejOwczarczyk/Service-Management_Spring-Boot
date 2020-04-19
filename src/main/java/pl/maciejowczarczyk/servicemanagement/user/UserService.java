package pl.maciejowczarczyk.servicemanagement.user;

public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);

    void activateUser(User user);
}
