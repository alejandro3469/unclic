package mx.com.endtoend.infrastructure.advertising.common.repository;

import mx.com.endtoend.infrastructure.advertising.common.entities.SaleAdvertisingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseSaleAdversitingRepository  extends JpaRepository<SaleAdvertisingEntity, Long>{
}
