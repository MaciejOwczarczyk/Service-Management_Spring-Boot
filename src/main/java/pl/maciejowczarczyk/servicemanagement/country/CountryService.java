package pl.maciejowczarczyk.servicemanagement.country;

import java.util.List;


public interface CountryService {

    Country findCountryById(Long id);
    List<Country> findAllCountries();
    void saveCountry(Country country);
    void deleteCountry(Country country);

}
