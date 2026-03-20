package mx.com.endtoend.infrastructure.reports.sales.closingOperation.carredana.zapata.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.BaseCustomDSLClosingOperationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLClosingOperationZapataRepository extends BaseCustomDSLClosingOperationRepository {

	public CustomDSLClosingOperationZapataRepository(@Qualifier("zapataDataEntityManagerFactory")
													 EntityManager em){
		super(CustomDSLClosingOperationZapataRepository.class, em);
	}
}
