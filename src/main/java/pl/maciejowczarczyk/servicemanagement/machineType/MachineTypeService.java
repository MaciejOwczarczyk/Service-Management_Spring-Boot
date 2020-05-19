package pl.maciejowczarczyk.servicemanagement.machineType;

import java.util.List;

public interface MachineTypeService {

    List<MachineType> findAllMachineTypes();
    MachineType findMachineTypeById(Long id);
    void deleteMachineType(MachineType machineType);
    void saveMachineType(MachineType machineType);
}
