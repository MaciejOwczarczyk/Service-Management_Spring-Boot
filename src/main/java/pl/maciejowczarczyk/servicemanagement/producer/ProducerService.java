package pl.maciejowczarczyk.servicemanagement.producer;

import java.util.List;

public interface ProducerService {

    Producer findProducerById(Long id);
    List<Producer> findAllProducers();
    void saveProducer(Producer producer);
    void deleteProducer(Producer producer);
}
