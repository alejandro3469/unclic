package mx.com.endtoend.infrastructure.payments.common.repository;

import mx.com.endtoend.infrastructure.payments.common.entities.TransferPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseTransferPaymentRepository extends JpaRepository<TransferPaymentEntity, Long> {

    @Query("SELECT tp FROM TransferPaymentEntity tp WHERE tp.paymentId=:id")
    List<TransferPaymentEntity> finByPaymentId(@Param("id") Long id);
}
