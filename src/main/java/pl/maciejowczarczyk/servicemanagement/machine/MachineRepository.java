package pl.maciejowczarczyk.servicemanagement.machine;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejowczarczyk.servicemanagement.company.Company;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    Machine findAllById(Long id);

    List<Machine> findAllByCompany(Company company);

    List<Machine> findAllByCompanyId(Long id);
}
