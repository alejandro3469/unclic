package mx.com.endtoend.infrastructure.cash.emailReport.ferresamano.business;

import mx.com.endtoend.infrastructure.cash.emailReport.common.business.BaseEmailCashConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.emailReport.ferresamano.repositories.EmailReportCashFSamanoRepository;

@Service
public class EmailCashConfigurationCFSamanoRepository extends BaseEmailCashConfigurationRepository {

	public EmailCashConfigurationCFSamanoRepository(EmailReportCashFSamanoRepository emailReportCashRepository) {
		super(EmailCashConfigurationCFSamanoRepository.class,
				emailReportCashRepository);
	}
}
