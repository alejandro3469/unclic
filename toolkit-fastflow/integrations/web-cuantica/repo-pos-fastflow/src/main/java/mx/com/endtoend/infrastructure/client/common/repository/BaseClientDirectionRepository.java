package mx.com.endtoend.infrastructure.client.common.repository;

import mx.com.endtoend.infrastructure.client.common.entities.ClientDirectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public interface BaseClientDirectionRepository extends JpaRepository<ClientDirectionEntity, Serializable> {

    ClientDirectionEntity findByIdClientId(Long id);

    ClientDirectionEntity findById(Long id);

//	@Query(value = "select * from clients_direction WHERE client_id = :id LIMIT 1", nativeQuery = true)
//	ClientDirectionEntity clientDirection(@Param("id") Long id);


    @Query("SELECT cd FROM ClientDirectionEntity cd JOIN cd.idClient c WHERE c.noClient=:clientNumber")
    Optional<ClientDirectionEntity> findByClientNumber(@Param("clientNumber") Long clientNumber);
}
