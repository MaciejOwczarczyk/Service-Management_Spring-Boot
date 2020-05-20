package pl.maciejowczarczyk.servicemanagement.salesman;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesmanServiceImpl implements SalesmanService {

    private final SalesmanRepository salesmanRepository;

    @Override
    public Salesman findSalesmanById(Long id) {
        return salesmanRepository.findAllById(id);
    }

    @Override
    public List<Salesman> findAllSalesmen() {
        return salesmanRepository.findAll();
    }

    @Override
    public void saveSalesman(Salesman salesman) {
        salesmanRepository.save(salesman);
    }

    @Override
    public void deleteSalesman(Salesman salesman) {
        salesmanRepository.delete(salesman);
    }
}
