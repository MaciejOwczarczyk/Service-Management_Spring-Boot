package pl.maciejowczarczyk.servicemanagement.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Country findCountryById(Long id) {
        return countryRepository.findAllById(id);
    }

    @Override
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }
}
