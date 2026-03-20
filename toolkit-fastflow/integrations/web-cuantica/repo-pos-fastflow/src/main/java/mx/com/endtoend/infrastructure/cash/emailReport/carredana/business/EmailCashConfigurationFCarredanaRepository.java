package mx.com.endtoend.infrastructure.cash.emailReport.carredana.business;


import mx.com.endtoend.infrastructure.cash.emailReport.carredana.repositories.EmailReportCashFCarRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.common.business.BaseEmailCashConfigurationRepository;
import org.springframework.stereotype.Service;


@Service
public class EmailCashConfigurationFCarredanaRepository extends BaseEmailCashConfigurationRepository {

    public EmailCashConfigurationFCarredanaRepository(EmailReportCashFCarRepository emailReportCashRepository) {
        super(EmailCashConfigurationFCarredanaRepository.class, emailReportCashRepository);
    }

}
