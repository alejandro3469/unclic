package mx.com.endtoend.infrastructure.catalogue.client.carredana.zapata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.client.common.entities.CFDIEntity;

@Repository
public interface CFDICZapataRepository extends JpaRepository<CFDIEntity, Long>{

}
