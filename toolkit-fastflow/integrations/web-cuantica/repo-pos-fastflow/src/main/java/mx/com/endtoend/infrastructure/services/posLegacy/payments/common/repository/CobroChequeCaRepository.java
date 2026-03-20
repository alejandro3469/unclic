package mx.com.endtoend.infrastructure.services.posLegacy.payments.common.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.payments.common.entities.CobroCheque;

@Repository
public interface CobroChequeCaRepository extends JpaRepository<CobroCheque, UUID> {

	@Query("SELECT COALESCE(SUM(cc.MontoMN),0) FROM CobroCheque cc WHERE cc.DCTO =:orderCode AND cc.DOCO=:orderNumber")
	Double getTotalByOrderNumberAndOrderCode(@Param("orderCode") String orderCode,
			@Param("orderNumber") BigDecimal orderNumber);

}
