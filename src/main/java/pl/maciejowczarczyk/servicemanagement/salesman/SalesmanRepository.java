package pl.maciejowczarczyk.servicemanagement.salesman;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesmanRepository extends JpaRepository<Salesman, Long> {

    void deleteById(Long id);

    Salesman findAllById(Long id);

}
