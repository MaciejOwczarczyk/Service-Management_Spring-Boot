package pl.maciejowczarczyk.servicemanagement.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Override
    public Company findCompanyById(Long id) {
        return companyRepository.findAllById(id);
    }

    @Override
    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company findCompanyByServiceTicket(ServiceTicket serviceTicket) {
        return companyRepository.findByServiceTickets(serviceTicket);
    }
}
