package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.carredana.zapata.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.BaseCustomDSLPaymentBranchEmployeeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentBranchEmployeeFCZapataRepository extends BaseCustomDSLPaymentBranchEmployeeRepository {


	public CustomDSLPaymentBranchEmployeeFCZapataRepository(@Qualifier("zapataDataEntityManagerFactory") EntityManager em) {
		super(CustomDSLPaymentBranchEmployeeFCZapataRepository.class, em);
	}
}
