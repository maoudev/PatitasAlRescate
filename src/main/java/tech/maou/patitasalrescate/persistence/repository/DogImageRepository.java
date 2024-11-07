package tech.maou.patitasalrescate.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import tech.maou.patitasalrescate.persistence.entity.DogImageEntity;

import java.util.UUID;

public interface DogImageRepository extends CrudRepository<DogImageEntity, UUID> {
}
