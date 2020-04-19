package pl.maciejowczarczyk.servicemanagement.machineType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineTypeRepository extends JpaRepository<MachineType, Long> {

    MachineType findAllById(Long id);
}
