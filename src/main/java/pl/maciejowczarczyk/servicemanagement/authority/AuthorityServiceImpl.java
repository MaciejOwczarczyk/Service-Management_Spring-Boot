package pl.maciejowczarczyk.servicemanagement.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;


    @Override
    public List<Authority> findAllAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    public void saveAuthority(Authority authority) {
        if (authority != null) {
            authorityRepository.save(authority);
        }
    }

    @Override
    public void deleteAuthority(Authority authority) {
        authorityRepository.delete(authority);
    }
}
