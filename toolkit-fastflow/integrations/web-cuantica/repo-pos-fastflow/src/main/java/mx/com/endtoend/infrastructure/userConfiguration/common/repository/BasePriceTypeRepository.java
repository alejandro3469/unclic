package mx.com.endtoend.infrastructure.userConfiguration.common.repository;

import mx.com.endtoend.infrastructure.userConfiguration.common.entities.PriceTypeEntity;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.RoleJobTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BasePriceTypeRepository extends JpaRepository<RoleJobTypeEntity, Long> {

    @Query("SELECT pt FROM PriceTypeEntity pt ORDER BY pt.id ASC")
    List<PriceTypeEntity> findAllSaleTypes();
}
