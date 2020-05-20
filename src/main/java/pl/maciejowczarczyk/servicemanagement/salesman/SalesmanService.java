package pl.maciejowczarczyk.servicemanagement.salesman;

import java.util.List;

public interface SalesmanService {

    Salesman findSalesmanById(Long id);
    List<Salesman> findAllSalesmen();
    void saveSalesman(Salesman salesman);
    void deleteSalesman(Salesman salesman);

}
