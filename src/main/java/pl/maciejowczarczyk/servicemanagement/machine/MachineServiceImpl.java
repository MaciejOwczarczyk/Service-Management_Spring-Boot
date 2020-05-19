package pl.maciejowczarczyk.servicemanagement.machine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maciejowczarczyk.servicemanagement.company.Company;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;

    @Override
    public Machine findMachineById(Long id) {
        return machineRepository.findAllById(id);
    }

    @Override
    public List<Machine> findAllMachines() {
        return machineRepository.findAll();
    }

    @Override
    public void deleteMachine(Machine machine) {
        machineRepository.delete(machine);
    }

    @Override
    public void saveMachine(Machine machine) {
        machineRepository.save(machine);
    }

    @Override
    public List<Machine> findAllMachinesByCompany(Company company) {
        return machineRepository.findAllByCompany(company);
    }

    @Override
    public List<Machine> findAllByCompanyId(Long id) {
        return machineRepository.findAllByCompanyId(id);
    }
}
