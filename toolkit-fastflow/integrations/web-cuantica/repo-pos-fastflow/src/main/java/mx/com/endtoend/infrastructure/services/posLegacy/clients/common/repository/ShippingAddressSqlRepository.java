package mx.com.endtoend.infrastructure.services.posLegacy.clients.common.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.entities.ShippingAddressSqlEntity;



@Repository
public interface ShippingAddressSqlRepository extends JpaRepository<ShippingAddressSqlEntity, Serializable>{

	Optional<ShippingAddressSqlEntity> findByNoClient(Long noClient);
	
}
