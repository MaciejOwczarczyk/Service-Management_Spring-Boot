package pl.maciejowczarczyk.servicemanagement.province;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maciejowczarczyk.servicemanagement.producer.ProducerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Override
    public Province findProvinceById(Long id) {
        return provinceRepository.findAllById(id);
    }

    @Override
    public List<Province> findAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public void saveProvince(Province province) {
        provinceRepository.save(province);
    }

    @Override
    public void deleteProvince(Province province) {
        provinceRepository.delete(province);
    }
}
