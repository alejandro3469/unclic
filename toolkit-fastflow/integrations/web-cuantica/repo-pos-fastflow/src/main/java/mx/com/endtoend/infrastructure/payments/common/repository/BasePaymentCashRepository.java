package mx.com.endtoend.infrastructure.payments.common.repository;

import mx.com.endtoend.infrastructure.payments.common.entities.PaymentCashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BasePaymentCashRepository extends JpaRepository<PaymentCashEntity, Long> {

    @Query("SELECT pc FROM PaymentCashEntity pc WHERE pc.paymentId=:id")
    List<PaymentCashEntity> finByPaymentId(@Param("id") Long id);

}
