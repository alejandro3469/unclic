package mx.com.endtoend.infrastructure.payments.common.repository;

import mx.com.endtoend.infrastructure.payments.common.entities.CreditNotePaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseCreditNotePaymentRepository extends JpaRepository<CreditNotePaymentEntity, Long> {

    @Query("SELECT cn FROM CreditNotePaymentEntity cn WHERE cn.paymentId=:id")
    List<CreditNotePaymentEntity> finByPaymentId(@Param("id") Long id);
}

