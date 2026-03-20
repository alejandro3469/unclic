package mx.com.endtoend.infrastructure.services.posLegacy.payments.common.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.payments.common.entities.CobroDocumento;

@Repository
public interface CobroDocumentoCaRepository extends JpaRepository<CobroDocumento, UUID> {

	@Query("SELECT COALESCE(SUM(cd.MontoMN),0) FROM CobroDocumento cd WHERE cd.DCTO =:orderCode AND cd.DOCO=:orderNumber")
	Double getTotalByOrderNumberAndOrderCode(@Param("orderCode") String orderCode,
			@Param("orderNumber") BigDecimal orderNumber);
}
