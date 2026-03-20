package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.calzada.fragua.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.BaseCustomDSLPaymentBranchEmployeeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentBranchEmployeeCFraguaRepository  extends BaseCustomDSLPaymentBranchEmployeeRepository {

	public CustomDSLPaymentBranchEmployeeCFraguaRepository(@Qualifier("fraguaDataEntityManagerFactory")
														   EntityManager em) {
		super(CustomDSLPaymentBranchEmployeeCFraguaRepository.class,em);
	}
}
