package pl.maciejowczarczyk.servicemanagement.country;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findAllById(Long id);

}
