package pl.maciejowczarczyk.servicemanagement.province;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Province findAllById(Long id);
}
