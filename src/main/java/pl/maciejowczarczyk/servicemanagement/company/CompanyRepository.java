package pl.maciejowczarczyk.servicemanagement.company;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    void deleteById(Long id);

    Company findAllById(Long id);

    Company findByServiceTickets(ServiceTicket serviceTicket);
}
