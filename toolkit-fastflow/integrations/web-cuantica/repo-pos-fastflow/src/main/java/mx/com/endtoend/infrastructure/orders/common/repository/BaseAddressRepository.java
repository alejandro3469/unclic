package mx.com.endtoend.infrastructure.orders.common.repository;

import mx.com.endtoend.infrastructure.orders.common.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseAddressRepository extends JpaRepository<AddressEntity, Long> {

    AddressEntity findByOrderOrderIdAndAddressType(Long id,String code);

    List<AddressEntity> findByOrderOrderId(long id);

}
