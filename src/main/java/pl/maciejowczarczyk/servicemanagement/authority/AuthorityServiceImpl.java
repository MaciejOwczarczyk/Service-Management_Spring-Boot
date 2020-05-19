package pl.maciejowczarczyk.servicemanagement.authority;

import lombok.RequiredArgsConstructor;

import java.util.List;

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
