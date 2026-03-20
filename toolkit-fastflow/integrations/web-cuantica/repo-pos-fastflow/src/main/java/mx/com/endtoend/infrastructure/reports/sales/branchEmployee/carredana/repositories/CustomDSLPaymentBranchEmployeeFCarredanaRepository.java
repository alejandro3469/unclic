package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.carredana.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.BaseCustomDSLPaymentBranchEmployeeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentBranchEmployeeFCarredanaRepository extends BaseCustomDSLPaymentBranchEmployeeRepository {

	public CustomDSLPaymentBranchEmployeeFCarredanaRepository(@Qualifier("carredanaDataEntityManagerFactory") EntityManager em) {
		super(CustomDSLPaymentBranchEmployeeFCarredanaRepository.class, em);
	}
}
