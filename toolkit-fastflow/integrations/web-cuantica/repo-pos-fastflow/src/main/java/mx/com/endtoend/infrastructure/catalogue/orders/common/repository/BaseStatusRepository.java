package mx.com.endtoend.infrastructure.catalogue.orders.common.repository;

import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public interface BaseStatusRepository extends JpaRepository<StatusEntity, Long> {

    Optional<StatusEntity> findById(Long id);

    boolean existsByCode(String code);

    @Query("FROM StatusEntity s WHERE s.code =:code")
    Optional<StatusEntity> findByCode(@Param("code") String code);

}
