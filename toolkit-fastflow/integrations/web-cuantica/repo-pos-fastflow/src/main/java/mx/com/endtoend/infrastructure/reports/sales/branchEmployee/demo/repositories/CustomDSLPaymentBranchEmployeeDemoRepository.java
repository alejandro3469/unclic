package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.demo.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.BaseCustomDSLPaymentBranchEmployeeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentBranchEmployeeDemoRepository extends BaseCustomDSLPaymentBranchEmployeeRepository {

    public CustomDSLPaymentBranchEmployeeDemoRepository(@Qualifier("demoDataEntityManagerFactory") EntityManager em) {
        super(CustomDSLPaymentBranchEmployeeDemoRepository.class, em);
    }
}
