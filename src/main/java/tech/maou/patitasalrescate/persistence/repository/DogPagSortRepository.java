package tech.maou.patitasalrescate.persistence.repository;

import org.springframework.data.repository.ListPagingAndSortingRepository;
import tech.maou.patitasalrescate.persistence.entity.DogEntity;

public interface DogPagSortRepository extends ListPagingAndSortingRepository<DogEntity, Long> {
}
