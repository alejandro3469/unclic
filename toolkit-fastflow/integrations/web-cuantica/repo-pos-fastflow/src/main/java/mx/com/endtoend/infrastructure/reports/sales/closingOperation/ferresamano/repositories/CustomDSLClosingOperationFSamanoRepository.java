package mx.com.endtoend.infrastructure.reports.sales.closingOperation.ferresamano.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.BaseCustomDSLClosingOperationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLClosingOperationFSamanoRepository extends BaseCustomDSLClosingOperationRepository {

	public CustomDSLClosingOperationFSamanoRepository(@Qualifier("ferresamanoDataEntityManagerFactory")
													  EntityManager em){
		super(CustomDSLClosingOperationFSamanoRepository.class, em);

	}
}
