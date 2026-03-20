package mx.com.endtoend.infrastructure.payments.common.repository;

import mx.com.endtoend.infrastructure.payments.common.entities.CreditPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseCreditPaymentRepository extends JpaRepository<CreditPaymentEntity, Long> {

    @Query("SELECT cp FROM CreditPaymentEntity cp WHERE cp.paymentId=:id")
    List<CreditPaymentEntity> finByPaymentId(@Param("id") Long id);

}
