package mx.com.endtoend.infrastructure.reports.sales.closingOperation.calzada.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.BaseCustomDSLClosingOperationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLClosingOperationCalzadaRepository extends BaseCustomDSLClosingOperationRepository {

	public CustomDSLClosingOperationCalzadaRepository(@Qualifier("calzadaDataEntityManagerFactory")
													  EntityManager em){
		super(CustomDSLClosingOperationCalzadaRepository.class, em);
	}
}
