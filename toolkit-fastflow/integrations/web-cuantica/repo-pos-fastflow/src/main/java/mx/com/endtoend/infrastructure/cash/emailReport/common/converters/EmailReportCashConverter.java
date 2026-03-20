package mx.com.endtoend.infrastructure.cash.emailReport.common.converters;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.infrastructure.cash.emailReport.common.entities.EmailReportCashDEntity;

@Component
public class EmailReportCashConverter {

	private static final int DEFAULT_CLOSE_ATTEMP = 2;

	public EmailReportCashDEntity emailReportCashDtoToEmailReportCashEntity(EmailReportCashDto emailReportCashDto) {

		EmailReportCashDEntity emailReportCashDEntity = new EmailReportCashDEntity();

		emailReportCashDEntity.setId(emailReportCashDto.getId());
		emailReportCashDEntity.setEmail(emailReportCashDto.getEmail());
		emailReportCashDEntity
				.setCloseAttemp(emailReportCashDto.getCloseAttemp() == null ? DEFAULT_CLOSE_ATTEMP : emailReportCashDto.getCloseAttemp());

		return emailReportCashDEntity;

	}

	public EmailReportCashDto emailReportCashEntityToEmailReportCashDto(EmailReportCashDEntity emailReportCashDEntity) {

		EmailReportCashDto emailReportCashDto = new EmailReportCashDto();

		emailReportCashDto.setId(emailReportCashDEntity.getId());
		emailReportCashDto.setEmail(emailReportCashDEntity.getEmail());
		emailReportCashDto.setCloseAttemp(emailReportCashDEntity.getCloseAttemp());
		
		return emailReportCashDto;
	}

}
