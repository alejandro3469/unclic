package mx.com.endtoend.infrastructure.payments.common.repository;

import mx.com.endtoend.infrastructure.payments.common.entities.InvoiceRerefenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Optional;

@MappedSuperclass
@NoRepositoryBean
public interface BaseInvoiceReferenceRepository extends JpaRepository<InvoiceRerefenceEntity, Long> {

    @Query(" SELECT ir FROM InvoiceRerefenceEntity ir WHERE ir.paymentId=:id")
    Optional<InvoiceRerefenceEntity> finByPaymentId(@Param("id") Long paymentId);

    @Query(" SELECT ir FROM InvoiceRerefenceEntity ir WHERE ir.orderNumber=:orderNumber AND ir.orderCode=:orderCode")
    Optional<InvoiceRerefenceEntity> findByOrderNumberAndOrderCode(@Param("orderNumber") BigDecimal orderNumber,
                                                                   @Param("orderCode") String orderCode);

}
