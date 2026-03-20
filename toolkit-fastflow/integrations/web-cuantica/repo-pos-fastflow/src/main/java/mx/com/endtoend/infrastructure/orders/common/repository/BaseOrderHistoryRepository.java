package mx.com.endtoend.infrastructure.orders.common.repository;

import mx.com.endtoend.infrastructure.orders.common.entities.OrderHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.List;

@MappedSuperclass
@NoRepositoryBean
public interface BaseOrderHistoryRepository extends JpaRepository<OrderHistoryEntity, Long> {

    @Query("SELECT oh FROM OrderHistoryEntity oh WHERE oh.orderNumber=:orderNumber")
    List<OrderHistoryEntity> findByOrderCode(@Param("orderNumber") BigDecimal orderNumber);

}
