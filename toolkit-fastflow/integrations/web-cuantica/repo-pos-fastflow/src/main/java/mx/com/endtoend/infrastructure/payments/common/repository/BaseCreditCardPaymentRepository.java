package mx.com.endtoend.infrastructure.payments.common.repository;

import mx.com.endtoend.infrastructure.payments.common.entities.CreditCardPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseCreditCardPaymentRepository extends JpaRepository<CreditCardPaymentEntity, Long> {

    @Query("SELECT ccp FROM CreditCardPaymentEntity ccp WHERE ccp.paymentId=:id")
    List<CreditCardPaymentEntity> finByPaymentId(@Param("id") Long id);
}

