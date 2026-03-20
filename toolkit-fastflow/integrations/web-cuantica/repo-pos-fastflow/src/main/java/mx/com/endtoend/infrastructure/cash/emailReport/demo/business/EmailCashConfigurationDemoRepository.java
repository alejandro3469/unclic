package mx.com.endtoend.infrastructure.cash.emailReport.demo.business;


import mx.com.endtoend.infrastructure.cash.emailReport.demo.repositories.EmailReportCashDemoRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.common.business.BaseEmailCashConfigurationRepository;
import org.springframework.stereotype.Service;


@Service
public class EmailCashConfigurationDemoRepository extends BaseEmailCashConfigurationRepository {

    public EmailCashConfigurationDemoRepository(EmailReportCashDemoRepository emailReportCashRepository) {
        super(EmailCashConfigurationDemoRepository.class, emailReportCashRepository);
    }

}
