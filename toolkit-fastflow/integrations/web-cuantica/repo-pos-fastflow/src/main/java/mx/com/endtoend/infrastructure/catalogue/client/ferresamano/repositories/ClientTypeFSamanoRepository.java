package mx.com.endtoend.infrastructure.catalogue.client.ferresamano.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.client.common.entities.ClientTypeEntity;

@Repository
public interface ClientTypeFSamanoRepository extends JpaRepository<ClientTypeEntity, Long>{

}
