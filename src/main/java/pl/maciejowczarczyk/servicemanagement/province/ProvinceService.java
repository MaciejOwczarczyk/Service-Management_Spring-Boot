package pl.maciejowczarczyk.servicemanagement.province;

import java.util.List;

public interface ProvinceService {

    Province findProvinceById(Long id);
    List<Province> findAllProvinces();
    void saveProvince(Province province);
    void deleteProvince(Province province);
}
