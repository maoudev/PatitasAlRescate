package tech.maou.patitasalrescate.service.dog;

import tech.maou.patitasalrescate.exception.DogNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.maou.patitasalrescate.exception.InvalidDogUUID;
import tech.maou.patitasalrescate.exception.InvalidFieldException;
import tech.maou.patitasalrescate.exception.UploadBlobException;
import tech.maou.patitasalrescate.persistence.entity.DTO.DogDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.DogImageDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.DogRegisterDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserDTO;
import tech.maou.patitasalrescate.persistence.entity.DogEntity;
import tech.maou.patitasalrescate.persistence.entity.DogImageEntity;
import tech.maou.patitasalrescate.persistence.entity.UserEntity;
import tech.maou.patitasalrescate.persistence.repository.DogPagSortRepository;
import tech.maou.patitasalrescate.persistence.repository.DogRepository;
import tech.maou.patitasalrescate.service.dogImages.DogImagesService;
import tech.maou.patitasalrescate.service.user.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class DogServiceImpl implements DogService {
    private final DogImagesService dogImagesService;
    private final UserService userService;
    private final DogRepository dogRepository;
    private final DogPagSortRepository dogPagSortRepository;

    @Autowired
    public DogServiceImpl(DogImagesService dogImagesService, UserService userService, DogRepository dogRepository, DogPagSortRepository dogPagSortRepository) {
        this.dogImagesService = dogImagesService;
        this.userService = userService;
        this.dogRepository = dogRepository;
        this.dogPagSortRepository = dogPagSortRepository;
    }

    @Override
    public DogDTO findById(String id) {
        try {
            UUID uuid = UUID.fromString(id);

            DogEntity dogEntity =  this.dogRepository.findById(uuid)
                    .orElseThrow(() -> new DogNotFound("No se encontró el perro", "err-18"));
            return this.convertDogEntityToDogDTO(dogEntity);
        } catch (IllegalArgumentException e) {
            throw new InvalidDogUUID("El id del perro es inválido", "err-19");
        }
    }

    @Override
    public Page<DogDTO> findAll(int page, int elements) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, elements, sort);
        Page<DogEntity> dogs = this.dogPagSortRepository.findAll(pageable);
        return dogs.map(this::convertDogEntityToDogDTO);
    }

    @Override
    public void save(DogRegisterDTO dogDTO) throws UploadBlobException {
        UserEntity currentUser = this.userService.getCurrentUser();

        this.validateDog(dogDTO);

        DogEntity dog = DogEntity.builder()
                .title(dogDTO.getTitle())
                .description(dogDTO.getDescription())
                .age(dogDTO.getAge())
                .sex(dogDTO.getSex())
                .vaccinated(dogDTO.isVaccinated())
                .sterilized(dogDTO.isSterilized())
                .adopted(dogDTO.isAdopted())
                .user(currentUser)
                .build();

        DogEntity dogEntity = this.dogRepository.save(dog);
        Set<DogImageEntity> dogImageEntities = this.dogImagesService.getDogImageEntities(dogDTO.getImages(), dogEntity);
        dogEntity.setImages(dogImageEntities);
        this.dogRepository.save(dogEntity);
    }

    private DogDTO convertDogEntityToDogDTO(DogEntity dogEntity) {

        Set<DogImageDTO> dogImageDTOS = new HashSet<>();
        dogEntity.getImages().stream()
                .map(this::convertDogImageEntityToDogImageDTO)
                .forEach(dogImageDTOS::add);

        UserDTO userDTO = this.userService.getCurrentUserDTO();

        return DogDTO.builder()
                .id(dogEntity.getId())
                .title(dogEntity.getTitle())
                .description(dogEntity.getDescription())
                .age(dogEntity.getAge())
                .sex(dogEntity.getSex())
                .vaccinated(dogEntity.isVaccinated())
                .sterilized(dogEntity.isSterilized())
                .adopted(dogEntity.isAdopted())
                .user(userDTO)
                .images(dogImageDTOS)
                .build();
    }

    private DogImageDTO convertDogImageEntityToDogImageDTO(DogImageEntity dogImageEntity) {
        return DogImageDTO.builder()
                .url(dogImageEntity.getUrl())
                .build();
    }


    private void validateDog(DogRegisterDTO dogDTO) {

        Set<String> sexes = new HashSet<>(Arrays.asList("Macho", "Hembra"));

        if (dogDTO.getTitle().isEmpty()) {
            throw new InvalidFieldException("El título es obligatorio", "err-14");
        }

        if (dogDTO.getDescription().isEmpty()) {
            throw new InvalidFieldException("La descripción es obligatoria", "err-15");
        }

        if (dogDTO.getAge() <= 0) {
            throw new InvalidFieldException("La edad debe ser igual o mayor a 0", "err-16");
        }

        if (dogDTO.getSex().isEmpty() || !sexes.contains(dogDTO.getSex())) {
            throw new InvalidFieldException("El sexo solo puede ser 'Macho' o   'Hembra'", "err-17");
        }
    }
}