package mx.com.endtoend.infrastructure.cash.emailReport.carredana.zapata.business;

import mx.com.endtoend.infrastructure.cash.emailReport.common.business.BaseEmailCashConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.emailReport.carredana.zapata.repositories.EmailReportCashCZapataRepository;

@Service
public class EmailCashConfigurationCZapataRepository extends BaseEmailCashConfigurationRepository {

	 public EmailCashConfigurationCZapataRepository(EmailReportCashCZapataRepository emailReportCashRepository){
		 super(EmailCashConfigurationCZapataRepository.class,
				 emailReportCashRepository);
	 }
}
