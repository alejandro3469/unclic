package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.calzada.repositories;

import javax.persistence.EntityManager;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.BaseCustomDSLPaymentBranchEmployeeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentBranchEmployeeCalzadaRepository extends BaseCustomDSLPaymentBranchEmployeeRepository {

	public CustomDSLPaymentBranchEmployeeCalzadaRepository(@Qualifier("calzadaDataEntityManagerFactory")
														   EntityManager em) {
		super(CustomDSLPaymentBranchEmployeeCalzadaRepository.class,em);
	}
}
