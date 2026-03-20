package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.reports.sales.branchEmployee.ports.ReportSaleBranchEmployeePersistencePort;
import mx.com.endtoend.domain.reports.sales.branchEmployee.ports.ReportSaleBranchEmployeeServicePort;
import mx.com.endtoend.domain.reports.sales.branchEmployee.service.SaleBranchEmlpoyeeReportServiceImpl;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.adapter.SaleBranchEmployeeReportJpaAdapter;

@Configuration
public class ReportSaleBranchEmployeeConfiguration {

	@Bean
	public ReportSaleBranchEmployeePersistencePort reportSaleBranchEmployeePersistencePort() {
		return new SaleBranchEmployeeReportJpaAdapter();
	}

	@Bean
	public ReportSaleBranchEmployeeServicePort reportSaleBranchEmployeeServicePort() {
		return new SaleBranchEmlpoyeeReportServiceImpl(reportSaleBranchEmployeePersistencePort());
	}

}
