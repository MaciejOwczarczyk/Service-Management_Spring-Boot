package pl.maciejowczarczyk.servicemanagement.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;

    @Override
    public Producer findProducerById(Long id) {
        return producerRepository.findAllById(id);
    }

    @Override
    public List<Producer> findAllProducers() {
        return producerRepository.findAll();
    }

    @Override
    public void saveProducer(Producer producer) {
        producerRepository.save(producer);
    }

    @Override
    public void deleteProducer(Producer producer) {
        producerRepository.delete(producer);
    }
}
