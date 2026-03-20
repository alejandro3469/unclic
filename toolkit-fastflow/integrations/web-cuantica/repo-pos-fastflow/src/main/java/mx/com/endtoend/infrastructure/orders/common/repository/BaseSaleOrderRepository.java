package mx.com.endtoend.infrastructure.orders.common.repository;

import mx.com.endtoend.infrastructure.orders.common.entities.SaleOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Optional;

@MappedSuperclass
@NoRepositoryBean
public interface BaseSaleOrderRepository extends JpaRepository<SaleOrderEntity, Long> {

    @Query("SELECT so FROM SaleOrderEntity so JOIN so.order o WHERE o.orderId=:orderId")
    Optional<SaleOrderEntity> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT so FROM SaleOrderEntity so JOIN so.order o WHERE o.orderNumber=:orderNumber")
    Optional<SaleOrderEntity> findByPrincipalOrderNumber(@Param("orderNumber") BigDecimal orderNumber);

}
