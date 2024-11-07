package tech.maou.patitasalrescate.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import tech.maou.patitasalrescate.persistence.entity.RoleEntity;
import tech.maou.patitasalrescate.persistence.entity.RoleEnum;


public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByRoleEnum(RoleEnum role);
}
