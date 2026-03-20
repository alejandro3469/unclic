package mx.com.endtoend.infrastructure.paymentsCredit.common.repository;

import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.StatusSaleResponseEntity;
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
public interface BaseStatusSaleResponseRepository extends JpaRepository<StatusSaleResponseEntity, Long> {

    @Query("SELECT ssr FROM StatusSaleResponseEntity ssr WHERE ssr.orderNumber=:orderNumber AND ssr.orderCode=:orderCode AND ssr.statusActive=true ORDER BY ssr.creationDate DESC")
    List<StatusSaleResponseEntity> findLastRecordByOrderNumberAndOrderCode(@Param("orderNumber") BigDecimal orderNumber,
                                                                           @Param("orderCode") String orderCode);

    @Transactional
    @Modifying
    @Query("UPDATE StatusSaleResponseEntity csr set csr.statusActive=:status WHERE csr.orderNumber=:orderNumber AND csr.orderCode=:orderCode")
    int changeCreditStatus(@Param("status") boolean status, @Param("orderNumber") BigDecimal orderNumber,
                           @Param("orderCode") String orderCode);

}
