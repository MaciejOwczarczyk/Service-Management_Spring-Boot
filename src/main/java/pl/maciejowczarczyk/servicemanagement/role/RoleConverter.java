package pl.maciejowczarczyk.servicemanagement.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role convert(String s) {
        Long id = Long.parseLong(s);
        return roleRepository.findAllById(id);
    }
}
