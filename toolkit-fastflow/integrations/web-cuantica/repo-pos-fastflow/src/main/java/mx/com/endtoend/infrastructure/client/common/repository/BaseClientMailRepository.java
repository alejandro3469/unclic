package mx.com.endtoend.infrastructure.client.common.repository;

import mx.com.endtoend.infrastructure.client.common.entities.ClientMailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseClientMailRepository extends JpaRepository<ClientMailEntity, Serializable> {

    List<ClientMailEntity> findByIdClientId(Long id);

    ClientMailEntity findFirstByIdClientId(Long id);

    @Transactional
    void deleteByIdClientIdAndIdNotIn(Long clientId, List<Long> id);

}
