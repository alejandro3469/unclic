package mx.com.endtoend.infrastructure.catalogue.orders.common.repository;

import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.CatalogueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseCatalogueRepository extends JpaRepository<CatalogueEntity, Serializable> {

    CatalogueEntity findByCode(String code);

    CatalogueEntity findByCodeAndType(String code, String type);

    CatalogueEntity findById(Long id);

    List<CatalogueEntity> findByType(String type);

    CatalogueEntity findByName(String name);

}
