package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.ferresamano.repositories;

import javax.persistence.EntityManager;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.BaseCustomDSLPaymentBranchEmployeeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentBranchEmployeeFSamanoRepository  extends BaseCustomDSLPaymentBranchEmployeeRepository {

	public CustomDSLPaymentBranchEmployeeFSamanoRepository(@Qualifier("ferresamanoDataEntityManagerFactory")
														   EntityManager em) {
		super(CustomDSLPaymentBranchEmployeeFSamanoRepository.class,em);
	}


}
