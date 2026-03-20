package mx.com.endtoend.infrastructure.catalogue.orders.common.repository;

import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.CatalogDirectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

@MappedSuperclass
@NoRepositoryBean
public interface BaseCatalogDirectionRepository extends JpaRepository<CatalogDirectionEntity, Serializable> {

    @Query(value = "select * from catalog_direction where (trim(postal_code)) = :cp", nativeQuery = true)
    List<CatalogDirectionEntity> findByCp(@Param("cp") String cp);

    @Query(value = "select * from catalog_direction where (trim(colony)) = :colony and (trim(postal_code)) = :cp", nativeQuery = true)
    List<CatalogDirectionEntity> findByColony(@Param("colony") String colony,@Param("cp") String cp);

    @Query(value = "select * from catalog_direction where (trim(state_id)) = :state", nativeQuery = true)
    List<CatalogDirectionEntity> findByState(@Param("state") String state);

}

