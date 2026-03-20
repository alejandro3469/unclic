package mx.com.endtoend.infrastructure.catalogue.client.ferresamano.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.client.common.entities.WorkTypeEntity;

@Repository
public interface WorkTypeFSamanoRepository extends JpaRepository<WorkTypeEntity, Long>{

}
