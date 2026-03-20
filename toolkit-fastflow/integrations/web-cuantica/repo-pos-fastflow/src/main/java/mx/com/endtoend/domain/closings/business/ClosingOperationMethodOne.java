package mx.com.endtoend.domain.closings.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.accountingRecord.services.GenerateAccountingRecordService;
import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.domain.closings.business.validations.GenericClosingOperationValidation;
import mx.com.endtoend.domain.closings.dto.AccountingOperatingSummaryDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.closings.ports.ClosingOperationPersistencePort;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.SemiFullFunction;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ClosingOperationMethodOne implements ClosingOperationInterface {

	private static final int DEFAULT_CLOSE_ATTEMP = 2;

	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	private OpeningOperationPersistencePort openingOperationPersistencePort;

	private EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort;

	public ClosingOperationMethodOne(AccountingRecordPersistencePort accountingRecordPersistencePort,
			OpeningOperationPersistencePort openingOperationPersistencePort,
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort) {
		this.accountingRecordPersistencePort = accountingRecordPersistencePort;
		this.openingOperationPersistencePort = openingOperationPersistencePort;
		this.emailCashConfigurationPersistencePort = emailCashConfigurationPersistencePort;
	}

	private GenericClosingOperationValidation closingOperationValidation = new GenericClosingOperationValidation();

	private GenerateAccountingRecordService accountingRecordService = new GenerateAccountingRecordService();

	private final Logger LOG = LoggerFactory.getLogger(ClosingOperationMethodOne.class);

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel createClosingOperationByCompanyCode(
			ClosingOperationPersistencePort closingOperationPersistencePort, ClosingOperationDto closingOperationDto,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createOpeningOperation()", idOperation));

		// Validación: El detalle de cierre de operación no puede estar vacío
		if (closingOperationDto.getClosingOperationDetail() == null || 
		    closingOperationDto.getClosingOperationDetail().isEmpty()) {
		    LOG.error(String.format("%s ERROR: closingOperationDetail está vacío", idOperation));
		    throw new ValidationError("El detalle de cierre de operación no puede estar vacío.");
		}

		String validOperativeData = closingOperationValidation.validOperativeDataToCreate(
				closingOperationPersistencePort, closingOperationDto, companyCode, idOperation);
		if (!validOperativeData.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS", idOperation));
			throw new ValidationError(validOperativeData);
		}

		Integer closeConfigAttemps = DEFAULT_CLOSE_ATTEMP;
		ResponseModel responseCashConfig = emailCashConfigurationPersistencePort
				.getEmailReportByCompanyCode(companyCode, idOperation);
		EmailReportCashDto emailReportCash = (EmailReportCashDto) responseCashConfig.getData();

		if (emailReportCash != null) {
			closeConfigAttemps = emailReportCash.getCloseAttemp() != null ? emailReportCash.getCloseAttemp()
					: DEFAULT_CLOSE_ATTEMP;
		}

		ResponseModel responseGetOpeningOperation = openingOperationPersistencePort
				.findOpeningOperationActiveByEmployeeEmailAndCompanyCode(closingOperationDto.getEmployeeEmail(),
						companyCode, idOperation);
		OpeningOperationDto openingOperation = (OpeningOperationDto) responseGetOpeningOperation.getData();

		String validOpeningOperation = closingOperationValidation.validOpeningOperationActive(openingOperation);
		if (!validOpeningOperation.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS", idOperation));
			throw new ValidationError(validOpeningOperation);
		}

		ResponseModel responseGetAccountingRecords = accountingRecordPersistencePort
				.fingAccountingRecordByOpeningIdAndCompanyCode(openingOperation.getOpeningId(), companyCode,
						idOperation);
		List<AccountingRecordDto> accountingRecordList = (List<AccountingRecordDto>) responseGetAccountingRecords
				.getData();

		AccountingOperatingSummaryDto theoreticalRecords = new AccountingOperatingSummaryDto(accountingRecordList);

		AccountingOperatingSummaryDto physicalRecords = AccountingOperatingSummaryDto
				.generateByClosingOperation(closingOperationDto.getClosingOperationDetail());

		int closeAttempts = openingOperation.getCloseAttempts();
		closeAttempts++;
		String validAccountingRecordToClosing = closingOperationValidation
				.validIncomingVersusOutgoingAccountingOperation(theoreticalRecords, physicalRecords, closeAttempts,
						closeConfigAttemps);

		if (!validAccountingRecordToClosing.isEmpty() && openingOperation.getCloseAttempts() < closeConfigAttemps) {
			LOG.warn(String.format("%s BAD CLOSING OPERATION ", idOperation));
			openingOperation.setIsActive(true);
			openingOperation.setCloseAttempts(closeAttempts);
			openingOperationPersistencePort.updateOperativeDataByIdAndCompanyCode(openingOperation, companyCode,
					idOperation);
			throw new ValidationError(validAccountingRecordToClosing);
		}

		ClosingOperationDto closingOperationCreated = new ClosingOperationDto(closingOperationDto);
		ResponseModel responseCreateClosingOperation = closingOperationPersistencePort
				.createClosingOperationByCompanyCode(closingOperationCreated, companyCode, idOperation);
		closingOperationCreated = (ClosingOperationDto) responseCreateClosingOperation.getData();

		openingOperation.setIsActive(false);
		openingOperation.setCloseAttempts(closeAttempts);
		openingOperation.setClosingId(closingOperationCreated.getClosingId());
		ResponseModel responseUpdateOpeningOperation = openingOperationPersistencePort
				.updateOperativeDataByIdAndCompanyCode(openingOperation, companyCode, idOperation);
		boolean updateOpeningOperation = (boolean) responseUpdateOpeningOperation.getData();

		boolean createdAccountingRecords = createAccountingRecords(closingOperationPersistencePort,
				closingOperationCreated, openingOperation.getOpeningId(), companyCode, idOperation);

		if (!createdAccountingRecords || !updateOpeningOperation) {
			LOG.warn(String.format("%s ERROR IN SAVE ACCOUNTING RECORDS OR UPDATE OPENINIG OPERATION", idOperation));
			LOG.warn(String.format("%s IS CREATE ACCOUNTING RECORDS: " + createdAccountingRecords, idOperation));
			LOG.warn(String.format("%s IS UPDATED OPENING OPERATION: " + updateOpeningOperation, idOperation));
			throw new SemiFullFunction(
					"ERROR SAVING ACCOUNTING RECORDS OR UPDATING OPENING OPERATION. CONTACT YOUR ADMINISTRATOR", true);
		}

		return new ResponseModel(true);
	}

	private boolean createAccountingRecords(ClosingOperationPersistencePort closingOperationPersistencePort,
			ClosingOperationDto closingOperationCreated, Long openingId, String companyCode, String idOperation) {
		LOG.info(String.format("%s FIND EMPLOYEE CONFIGURATION", idOperation));
		ResponseModel responseGetEmployee = closingOperationPersistencePort
				.findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(closingOperationCreated.getEmployeeEmail(),
						closingOperationCreated.getBranchCode(), companyCode, idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseGetEmployee.getData();

		LOG.info(String.format("%s INIT GENERATE ACCOUNTING RECORDS", idOperation));
		List<AccountingRecordDto> accountingRecordList = accountingRecordService
				.generateByClosingOperation(closingOperationCreated, employeeDto.getUserId(), openingId);
		System.out.println(accountingRecordList.toString());
		LOG.info(String.format("%s INIT SAVE ACCOUNTING RECORDS", idOperation));
		ResponseModel responseCreateAccountingRecord = accountingRecordPersistencePort
				.createAccountingRecordMovementByCompanyCode(accountingRecordList, companyCode, idOperation);

		boolean created = (boolean) responseCreateAccountingRecord.getData();

		return created;
	}

}
