package mx.com.endtoend.infrastructure.cash.emailReport.common.business;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.cash.emailReport.common.repositories.BaseEmailReportCashRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.infrastructure.cash.emailReport.common.persistence.GenericEmailCashConfigurationPersistence;
import mx.com.endtoend.infrastructure.cash.emailReport.common.converters.EmailReportCashConverter;
import mx.com.endtoend.infrastructure.cash.emailReport.common.entities.EmailReportCashDEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;

public class BaseEmailCashConfigurationRepository implements GenericEmailCashConfigurationPersistence {

	@Autowired
	private EmailReportCashConverter emailReportCashConverter;

	private final BaseEmailReportCashRepository emailReportCashRepository;
	private final Logger LOG;

	public BaseEmailCashConfigurationRepository(Class<?> loggerClass, BaseEmailReportCashRepository emailReportCashRepository) {
		LOG = LoggerFactory.getLogger(loggerClass);
		this.emailReportCashRepository = emailReportCashRepository;
	}

	@Transactional
	@Override
	public EmailReportCashDto createEmailReport(EmailReportCashDto emailReportCashDto, String idOperation) {
		try {
			LOG.info(String.format("%s INIT createEmailReport()", idOperation));
			EmailReportCashDEntity emailReportCashDEntity = emailReportCashConverter
					.emailReportCashDtoToEmailReportCashEntity(emailReportCashDto);
			emailReportCashDEntity = emailReportCashRepository.save(emailReportCashDEntity);
			return emailReportCashConverter.emailReportCashEntityToEmailReportCashDto(emailReportCashDEntity);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN createEmailReport(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public EmailReportCashDto updateEmailReport(EmailReportCashDto emailReportCashDto, String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateEmailReport()", idOperation));

			Optional<EmailReportCashDEntity> emailCashOptional = emailReportCashRepository
					.findById(emailReportCashDto.getId());
			if(emailCashOptional.isPresent()) {
				emailReportCashDto.setCloseAttemp(emailCashOptional.get().getCloseAttemp());
			}

			EmailReportCashDEntity emailReportCashDEntity = emailReportCashConverter
					.emailReportCashDtoToEmailReportCashEntity(emailReportCashDto);
			emailReportCashDEntity = emailReportCashRepository.save(emailReportCashDEntity);
			return emailReportCashConverter.emailReportCashEntityToEmailReportCashDto(emailReportCashDEntity);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN createEmailReport(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public EmailReportCashDto getEmailReport(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getEmailReport()", idOperation));
			List<EmailReportCashDEntity> emailReportCashDEntityList = emailReportCashRepository.findAll();
			EmailReportCashDto emailReportCashDto = null;
			if (!emailReportCashDEntityList.isEmpty()) {
				emailReportCashDto = emailReportCashConverter
						.emailReportCashEntityToEmailReportCashDto(emailReportCashDEntityList.get(0));
			}
			return emailReportCashDto;
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN createEmailReport(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}
}
