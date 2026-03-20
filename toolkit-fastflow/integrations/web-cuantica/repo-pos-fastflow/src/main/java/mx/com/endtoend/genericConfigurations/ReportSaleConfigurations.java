package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.reports.sales.branch.ports.SaleReportPersistencePort;
import mx.com.endtoend.domain.reports.sales.branch.ports.SaleReportServicePort;
import mx.com.endtoend.domain.reports.sales.branch.service.SaleReportServiceImpl;
import mx.com.endtoend.infrastructure.reports.sales.branch.common.adapter.SaleReportJpaAdapter;

@Configuration
public class ReportSaleConfigurations {

	@Bean
	public SaleReportPersistencePort saleReportPersistencePort() {
		return new SaleReportJpaAdapter();
	}

	@Bean
	public SaleReportServicePort saleReportServicePort() {
		return new SaleReportServiceImpl(saleReportPersistencePort());
	}

}
