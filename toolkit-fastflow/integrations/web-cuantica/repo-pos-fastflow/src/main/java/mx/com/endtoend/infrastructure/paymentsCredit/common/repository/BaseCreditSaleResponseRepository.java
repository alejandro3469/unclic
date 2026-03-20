package mx.com.endtoend.infrastructure.paymentsCredit.common.repository;

import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.CreditSaleResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@MappedSuperclass
@NoRepositoryBean
public interface BaseCreditSaleResponseRepository extends JpaRepository<CreditSaleResponseEntity, Long> {

    @Query("SELECT csr FROM CreditSaleResponseEntity csr WHERE csr.orderNumber=:orderNumber AND csr.orderCode=:orderCode AND csr.statusActive=true ORDER BY csr.creationDate DESC")
    List<CreditSaleResponseEntity> findLastRecordByOrderNumberAndOrderCode(
            @Param("orderNumber") BigDecimal orderNumber, @Param("orderCode") String orderCode);

    @Transactional
    @Modifying
    @Query("UPDATE CreditSaleResponseEntity csr set csr.statusActive=:status WHERE csr.orderNumber=:orderNumber AND csr.orderCode=:orderCode")
    int changeCreditStatus(@Param("status") boolean status, @Param("orderNumber") BigDecimal orderNumber,
                           @Param("orderCode") String orderCode);
}
