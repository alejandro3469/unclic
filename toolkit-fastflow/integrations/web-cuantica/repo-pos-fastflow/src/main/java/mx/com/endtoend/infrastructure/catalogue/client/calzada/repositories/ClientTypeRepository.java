package mx.com.endtoend.infrastructure.catalogue.client.calzada.repositories;

import mx.com.endtoend.infrastructure.catalogue.client.common.repository.BaseClientTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.client.common.entities.ClientTypeEntity;

@Repository
public interface ClientTypeRepository extends BaseClientTypeRepository {

}
