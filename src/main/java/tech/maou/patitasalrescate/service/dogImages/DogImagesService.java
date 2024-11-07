package tech.maou.patitasalrescate.service.dogImages;

import org.springframework.web.multipart.MultipartFile;
import tech.maou.patitasalrescate.persistence.entity.DogEntity;
import tech.maou.patitasalrescate.persistence.entity.DogImageEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface DogImagesService {
    List<DogImageEntity> findById(UUID id);
    void save(DogImageEntity dogImage);
    Set<DogImageEntity> getDogImageEntities(MultipartFile[] images, DogEntity dogEntity);
}
