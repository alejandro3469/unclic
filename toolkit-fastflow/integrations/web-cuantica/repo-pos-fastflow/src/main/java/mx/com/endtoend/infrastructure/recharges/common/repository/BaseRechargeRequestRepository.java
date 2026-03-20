package mx.com.endtoend.infrastructure.recharges.common.repository;

import mx.com.endtoend.infrastructure.recharges.calzada.entities.RechargeRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public interface BaseRechargeRequestRepository extends JpaRepository<RechargeRequestEntity, Long> {

    @Query("SELECT rr FROM RechargeRequestEntity rr WHERE rr.orderNumber=:order AND rr.orderCode=:orderCode")
    Optional<RechargeRequestEntity> findByOrderNumberAndOrderCode(@Param("order") BigDecimal order,
                                                                  @Param("orderCode") String orderCode);

}
