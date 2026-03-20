package mx.com.endtoend.infrastructure.paymentsCredit.common.repository;

import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.CreditSaleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
@NoRepositoryBean
public interface BaseCreditSaleDetailrepository extends JpaRepository<CreditSaleDetailEntity, Long> {

    @Query("SELECT csd FROM CreditSaleDetailEntity csd WHERE csd.creditSaleRequestId=:id")
    List<CreditSaleDetailEntity> findAllByRequestId(@Param("id") Long id);

}