package pl.maciejowczarczyk.servicemanagement.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public Role convert(String s) {
        Long id = Long.parseLong(s);
        return roleService.findRoleById(id);
    }
}
