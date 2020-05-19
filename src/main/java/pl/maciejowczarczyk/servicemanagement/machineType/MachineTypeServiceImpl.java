package pl.maciejowczarczyk.servicemanagement.machineType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineTypeServiceImpl implements MachineTypeService {

    private final MachineTypeRepository machineTypeRepository;

    @Override
    public List<MachineType> findAllMachineTypes() {
        return machineTypeRepository.findAll();
    }

    @Override
    public MachineType findMachineTypeById(Long id) {
        return machineTypeRepository.findAllById(id);
    }

    @Override
    public void deleteMachineType(MachineType machineType) {
        machineTypeRepository.delete(machineType);
    }

    @Override
    public void saveMachineType(MachineType machineType) {
        machineTypeRepository.save(machineType);
    }
}
