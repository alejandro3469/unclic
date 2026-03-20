package mx.com.endtoend.infrastructure.cash.emailReport.calzada.business;

import mx.com.endtoend.infrastructure.cash.emailReport.calzada.repositories.EmailReportCashRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.common.business.BaseEmailCashConfigurationRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailCashConfigurationCalzadaRepository extends BaseEmailCashConfigurationRepository {


    public EmailCashConfigurationCalzadaRepository(EmailReportCashRepository emailReportCashRepository){
        super(EmailCashConfigurationCalzadaRepository.class,emailReportCashRepository);
    }
}
