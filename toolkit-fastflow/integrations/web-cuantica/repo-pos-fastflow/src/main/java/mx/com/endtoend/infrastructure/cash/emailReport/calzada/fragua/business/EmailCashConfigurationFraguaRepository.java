package mx.com.endtoend.infrastructure.cash.emailReport.calzada.fragua.business;


import mx.com.endtoend.infrastructure.cash.emailReport.common.business.BaseEmailCashConfigurationRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.emailReport.calzada.fragua.repositories.EmailReportCashFraRepository;

@Service
public class EmailCashConfigurationFraguaRepository extends BaseEmailCashConfigurationRepository {

	public EmailCashConfigurationFraguaRepository(EmailReportCashFraRepository emailReportCashRepository){
		super(EmailCashConfigurationFraguaRepository.class,emailReportCashRepository);
	}
}
