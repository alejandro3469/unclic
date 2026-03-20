package mx.com.endtoend.infrastructure.payments.common.repository;

import mx.com.endtoend.infrastructure.payments.common.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

@NoRepositoryBean
@MappedSuperclass
public interface BasePaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.orderNumber = :orderNumber AND p.orderCode = :orderCode ORDER BY p.paymentDate ASC")
    Stream<PaymentEntity> streamByOrderNumberAndOrderCode(@Param("orderNumber") BigDecimal orderNumber,
                                                          @Param("orderCode") String orderCode);

    @Transactional
    default Optional<PaymentEntity> findByOrderNumberAndOrderCode(BigDecimal orderNumber, String orderCode) {
        try (Stream<PaymentEntity> stream = streamByOrderNumberAndOrderCode(orderNumber, orderCode)) {
            return stream.findFirst();
        }
    }

    @Transactional
    @Modifying
    @Query("UPDATE PaymentEntity p SET p.isPrinted = :state WHERE p.paymentId = :id")
    int updatePrintSatateByPaymentId(@Param("state") boolean state, @Param("id") Long id);

}
