package mx.com.endtoend.infrastructure.userConfiguration.common.repository;

import mx.com.endtoend.infrastructure.userConfiguration.common.entities.RoleJobTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseRoleJobTypeRepository extends JpaRepository<RoleJobTypeEntity, Long> {

    @Query("SELECT rj FROM RoleJobTypeEntity rj")
    List<RoleJobTypeEntity> findAllRoleJobTypes();
}
