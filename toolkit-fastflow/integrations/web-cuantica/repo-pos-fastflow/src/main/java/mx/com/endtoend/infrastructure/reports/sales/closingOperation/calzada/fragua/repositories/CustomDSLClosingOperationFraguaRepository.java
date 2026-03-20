package mx.com.endtoend.infrastructure.reports.sales.closingOperation.calzada.fragua.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.BaseCustomDSLClosingOperationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLClosingOperationFraguaRepository extends BaseCustomDSLClosingOperationRepository {

	public CustomDSLClosingOperationFraguaRepository(@Qualifier("fraguaDataEntityManagerFactory")
													 EntityManager em){
		super(CustomDSLClosingOperationFraguaRepository.class, em);
	}
}
