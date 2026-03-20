package mx.com.endtoend.infrastructure.client.common.repository;

import mx.com.endtoend.infrastructure.client.common.entities.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Serializable> {

    List<ShippingAddressEntity> findByIdClientId(Long id);

    ShippingAddressEntity findById(Long id);

//	@Query(value = "select * from shippingAddress WHERE client_id = :id LIMIT 1", nativeQuery = true)
//	ShippingAddressEntity shippingAddress(@Param("id") Long id);

    ShippingAddressEntity findFirstByIdClientId(Long id);
}
