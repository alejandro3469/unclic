package mx.com.endtoend.infrastructure.cash.emailReport.common.persistence;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;

public interface GenericEmailCashConfigurationPersistence {

	EmailReportCashDto createEmailReport(EmailReportCashDto emailReportCashDto, String idOperation);

	EmailReportCashDto updateEmailReport(EmailReportCashDto emailReportCashDto, String idOperation);

	EmailReportCashDto getEmailReport(String idOperation);

}
