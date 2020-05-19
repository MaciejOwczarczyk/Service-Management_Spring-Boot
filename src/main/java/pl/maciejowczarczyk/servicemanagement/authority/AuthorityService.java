package pl.maciejowczarczyk.servicemanagement.authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> findAllAuthorities();
    void saveAuthority(Authority authority);
    void deleteAuthority(Authority authority);

}
