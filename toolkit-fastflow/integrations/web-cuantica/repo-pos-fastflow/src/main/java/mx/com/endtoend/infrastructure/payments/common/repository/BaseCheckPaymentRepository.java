package mx.com.endtoend.infrastructure.payments.common.repository;

import mx.com.endtoend.infrastructure.payments.common.entities.CheckPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseCheckPaymentRepository extends JpaRepository<CheckPaymentEntity, Long> {

    @Query("SELECT cp FROM CheckPaymentEntity cp WHERE cp.paymentId=:id")
    List<CheckPaymentEntity> finByPaymentId(@Param("id") Long id);
}