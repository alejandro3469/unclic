package mx.com.endtoend.infrastructure.services.posLegacy.payments.common.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.payments.common.entities.CobroTransferencia;

@Repository
public interface CobroTransferenciaCaRepository extends JpaRepository<CobroTransferencia, UUID> {

	@Query("SELECT COALESCE(SUM(ct.MontoMN),0) FROM CobroTransferencia ct WHERE ct.DCTO =:orderCode AND ct.DOCO=:orderNumber")
	Double getTotalByOrderNumberAndOrderCode(@Param("orderCode") String orderCode,
			@Param("orderNumber") BigDecimal orderNumber);
}
