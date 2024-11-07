package tech.maou.patitasalrescate.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import tech.maou.patitasalrescate.persistence.entity.DogEntity;

import java.util.List;
import java.util.UUID;

public interface DogRepository extends CrudRepository<DogEntity, UUID> {
    List<DogEntity> findAll();

}
