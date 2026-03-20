package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.reports.sales.closingOperation.ports.ReportClosingOperationPersistencePort;
import mx.com.endtoend.domain.reports.sales.closingOperation.ports.ReportClosingOperationServicePort;
import mx.com.endtoend.domain.reports.sales.closingOperation.service.ClosingOperationReportServiceImpl;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.adapter.ReportClosingOperationJpaAdapter;

@Configuration
public class ReportClosingConfiguration {

	@Bean
	ReportClosingOperationPersistencePort reportClosingOperationPersistencePort() {
		return new ReportClosingOperationJpaAdapter();
	}

	@Bean
	ReportClosingOperationServicePort reportClosingOperationServicePort() {
		return new ClosingOperationReportServiceImpl(reportClosingOperationPersistencePort());
	}

}
