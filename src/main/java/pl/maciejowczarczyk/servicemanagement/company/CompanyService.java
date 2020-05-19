package pl.maciejowczarczyk.servicemanagement.company;


import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;

import java.util.List;

public interface CompanyService {

    Company findCompanyById(Long id);
    void deleteCompanyById(Long id);
    void saveCompany(Company company);
    void deleteCompany(Company company);
    List<Company> findAllCompanies();
    Company findCompanyByServiceTicket(ServiceTicket serviceTicket);
}
