package tech.maou.patitasalrescate.service.dog;

import org.springframework.data.domain.Page;
import tech.maou.patitasalrescate.exception.UploadBlobException;
import tech.maou.patitasalrescate.persistence.entity.DTO.DogDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.DogRegisterDTO;


public interface DogService {
    DogDTO findById(String id);
    Page<DogDTO> findAll(int page, int elements);
    void save(DogRegisterDTO dogDTO) throws UploadBlobException;
}
