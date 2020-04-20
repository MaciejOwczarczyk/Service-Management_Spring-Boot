package pl.maciejowczarczyk.servicemanagement.producer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Producer findAllById(Long id);
}
