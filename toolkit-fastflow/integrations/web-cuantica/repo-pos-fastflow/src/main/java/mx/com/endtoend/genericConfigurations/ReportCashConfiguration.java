package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.reports.cash.ports.ReportCashPersistencePort;
import mx.com.endtoend.domain.reports.cash.ports.ReportCashServicePort;
import mx.com.endtoend.domain.reports.cash.service.ReportCashServiceImpl;
import mx.com.endtoend.infrastructure.reports.cash.common.adapter.ReportCashJpaAdapter;

@Configuration
public class ReportCashConfiguration {
	
	@Bean
	public ReportCashPersistencePort reportCashPersistencePort() {
		return new ReportCashJpaAdapter();
	}
	
	@Bean
	public ReportCashServicePort reportCashServicePort() {
		return new ReportCashServiceImpl(reportCashPersistencePort());
	}

}
