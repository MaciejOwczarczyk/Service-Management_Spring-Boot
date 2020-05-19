package pl.maciejowczarczyk.servicemanagement.machine;

import pl.maciejowczarczyk.servicemanagement.company.Company;

import java.util.List;

public interface MachineService {

    Machine findMachineById(Long id);
    List<Machine> findAllMachines();
    void deleteMachine(Machine machine);
    void saveMachine(Machine machine);
    List<Machine> findAllMachinesByCompany(Company company);
    List<Machine> findAllByCompanyId(Long id);
}
