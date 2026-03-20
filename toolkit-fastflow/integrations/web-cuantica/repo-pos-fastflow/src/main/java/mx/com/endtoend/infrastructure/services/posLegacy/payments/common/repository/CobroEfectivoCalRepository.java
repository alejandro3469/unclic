package mx.com.endtoend.infrastructure.services.posLegacy.payments.common.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.payments.common.entities.CobroEfectivo;

@Repository
public interface CobroEfectivoCalRepository extends JpaRepository<CobroEfectivo, UUID> {

	@Query("SELECT COALESCE(SUM(ce.MontoMN),0) FROM CobroEfectivo ce WHERE ce.DCTO =:orderCode AND DOCO=:orderNumber")
	Double getTotalByOrderNumberAndOrderCode(@Param("orderCode") String orderCode,
			@Param("orderNumber") BigDecimal orderNumber);

}
