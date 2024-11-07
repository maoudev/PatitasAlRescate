package tech.maou.patitasalrescate.service.dogImages;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tech.maou.patitasalrescate.exception.UploadBlobException;
import tech.maou.patitasalrescate.persistence.entity.DogEntity;
import tech.maou.patitasalrescate.persistence.entity.DogImageEntity;
import tech.maou.patitasalrescate.persistence.repository.DogImageRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class DogImagesServiceImpl implements DogImagesService {
    private final DogImageRepository imageRepository;
    private final BlobServiceClient blobServiceClient;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;


    @Autowired
    public DogImagesServiceImpl(BlobServiceClient blobServiceClient, DogImageRepository imageRepository) {
        this.blobServiceClient = blobServiceClient;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<DogImageEntity> findById(UUID id) {
        return List.of();
    }

    @Override
    public void save(DogImageEntity dogImage) {
        this.imageRepository.save(dogImage);
    }

    private String uploadImage(MultipartFile file, String imageName) throws UploadBlobException {
        try {
            String  blobFilename = imageName + ".jpg";

            BlobClient blobClient = blobServiceClient
                    .getBlobContainerClient(containerName)
                    .getBlobClient(blobFilename);

            blobClient.upload(file.getInputStream(), file.getSize());

            return blobClient.getBlobUrl();
        } catch (Exception e) {
            throw new UploadBlobException("Error Guardando Imagen ", "err-13");
        }
    }

    @Override
    public Set<DogImageEntity> getDogImageEntities(MultipartFile[] images, DogEntity dogEntity) {
        Set<DogImageEntity> dogImageEntities = new HashSet<>();

        for (MultipartFile image : images) {
            String ext = StringUtils.getFilenameExtension(image.getOriginalFilename());
            String fileName = UUID.randomUUID() + ext;
            String url = this.uploadImage(image, fileName);


            DogImageEntity dogImageEntity = DogImageEntity.builder()
                    .name(fileName)
                    .dog(dogEntity)
                    .url(url)
                    .build();
            dogImageEntities.add(dogImageEntity);
        }

        return dogImageEntities;
    }
}
