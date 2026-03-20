package mx.com.endtoend.infrastructure.reports.sales.closingOperation.carredana.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.BaseCustomDSLClosingOperationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
@Repository
public class CustomDSLClosingOperationCarredanaRepository extends BaseCustomDSLClosingOperationRepository {

	public CustomDSLClosingOperationCarredanaRepository(@Qualifier("carredanaDataEntityManagerFactory")
														EntityManager em){
		super(CustomDSLClosingOperationCarredanaRepository.class, em);
	}
}
