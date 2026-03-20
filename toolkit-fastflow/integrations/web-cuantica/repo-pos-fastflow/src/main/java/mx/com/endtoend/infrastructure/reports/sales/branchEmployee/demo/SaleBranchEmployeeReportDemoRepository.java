package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.demo;

import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.BaseSaleBranchEmployeeReportRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.demo.repositories.CustomDSLPaymentBranchEmployeeDemoRepository;

@Service
public class SaleBranchEmployeeReportDemoRepository extends BaseSaleBranchEmployeeReportRepository {

    public SaleBranchEmployeeReportDemoRepository(CustomDSLPaymentBranchEmployeeDemoRepository customDSLPaymentBranchEmployeeFCarredanaRepository){
        super(SaleBranchEmployeeReportDemoRepository.class, customDSLPaymentBranchEmployeeFCarredanaRepository);
    }
}
