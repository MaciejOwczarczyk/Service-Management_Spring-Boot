package pl.maciejowczarczyk.servicemanagement.role;

import java.util.List;

public interface RoleService {

    Role findRoleByName(String name);
    Role findRoleById(Long id);
    List<Role> findAllRoles();
    void deleteRole(Role role);
    void saveRole(Role role);
}
