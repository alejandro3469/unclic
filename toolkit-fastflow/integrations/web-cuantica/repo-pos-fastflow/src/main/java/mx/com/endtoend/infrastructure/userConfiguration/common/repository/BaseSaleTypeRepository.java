package mx.com.endtoend.infrastructure.userConfiguration.common.repository;

import mx.com.endtoend.infrastructure.userConfiguration.common.entities.SaleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseSaleTypeRepository extends JpaRepository<SaleTypeEntity, Long> {

    @Query("SELECT st FROM SaleTypeEntity st")
    List<SaleTypeEntity> findAllSaleTypes();
}
