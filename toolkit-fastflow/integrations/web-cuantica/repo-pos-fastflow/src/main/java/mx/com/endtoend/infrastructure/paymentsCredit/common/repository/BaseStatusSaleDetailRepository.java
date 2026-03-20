package mx.com.endtoend.infrastructure.paymentsCredit.common.repository;

import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.StatusSaleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.Optional;

@MappedSuperclass
@NoRepositoryBean
public interface BaseStatusSaleDetailRepository extends JpaRepository<StatusSaleDetailEntity, Long> {

    @Query("SELECT ssd FROM StatusSaleDetailEntity ssd WHERE ssd.statusSaleResponseId=:id")
    Optional<StatusSaleDetailEntity> findAllByRequestId(@Param("id") Long id);

}
