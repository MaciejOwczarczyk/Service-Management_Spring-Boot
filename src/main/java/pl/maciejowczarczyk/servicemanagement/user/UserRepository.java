package pl.maciejowczarczyk.servicemanagement.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maciejowczarczyk.servicemanagement.role.Role;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    List<User> findAllByRoles(Role role);
    User findAllByUsername(String username);

    User findAllById(Long id);

}
