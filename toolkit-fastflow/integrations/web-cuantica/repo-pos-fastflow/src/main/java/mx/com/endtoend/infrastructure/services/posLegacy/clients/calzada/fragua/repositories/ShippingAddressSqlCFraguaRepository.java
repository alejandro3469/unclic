package mx.com.endtoend.infrastructure.services.posLegacy.clients.calzada.fragua.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.entities.ShippingAddressSqlEntity;



@Repository
public interface ShippingAddressSqlCFraguaRepository extends JpaRepository<ShippingAddressSqlEntity, Serializable>{

	Optional<ShippingAddressSqlEntity> findByNoClient(Long noClient);
	
}
