package pl.maciejowczarczyk.servicemanagement.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class UserConverter implements Converter<String, User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User convert(String s) {
        Long id = Long.parseLong(s);
        return userRepository.findAllById(id);
    }
}
