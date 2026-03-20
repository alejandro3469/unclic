package mx.com.endtoend.domain.openings.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.accountingRecord.services.GenerateAccountingRecordService;
import mx.com.endtoend.domain.openings.business.validations.GenericOpeningOperationValidation;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.SemiFullFunction;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class OpeningOperationMethodOne implements OpeningOperationInterface {

	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	public OpeningOperationMethodOne(AccountingRecordPersistencePort accountingRecordPersistencePort) {
		this.accountingRecordPersistencePort = accountingRecordPersistencePort;
	}

	private GenericOpeningOperationValidation openingOperationValidation = new GenericOpeningOperationValidation();

	private GenerateAccountingRecordService accountingRecordService = new GenerateAccountingRecordService();

	private final Logger LOG = LoggerFactory.getLogger(OpeningOperationMethodOne.class);

	@Override
	public ResponseModel createOpeningOperation(OpeningOperationPersistencePort openingOperationPersistencePort,
			OpeningOperationDto openingOperationDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createOpeningOperation()", idOperation));
		// Validación: El detalle de apertura de operación no puede estar vacío
		if (openingOperationDto.getOpeningOperationDetail() == null || 
		    openingOperationDto.getOpeningOperationDetail().isEmpty()) {
		    LOG.error(String.format("%s ERROR: openingOperationDetail está vacío", idOperation));
		    throw new ValidationError("El detalle de apertura de operación no puede estar vacío.");
		}
		String validations = openingOperationValidation.validOperativeDataToCreateOpeningOperation(
				openingOperationPersistencePort, openingOperationDto, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS", idOperation));
			throw new ValidationError(validations);
		}
		OpeningOperationDto openingOperationToCreate = new OpeningOperationDto(openingOperationDto);
		ResponseModel responseModelCreate = openingOperationPersistencePort
				.createOpeningOperationByCompanyCode(openingOperationToCreate, companyCode, idOperation);
		openingOperationToCreate = (OpeningOperationDto) responseModelCreate.getData();

		boolean createdAccountingRecords = createAccountingRecords(openingOperationPersistencePort,
				openingOperationToCreate, companyCode, idOperation);
		if (!createdAccountingRecords) {
			LOG.warn(String.format("%s ERROR IN SAVE ACCOUNTING RECORDS", idOperation));
			throw new SemiFullFunction("ERROR SAVING ACCOUNTING RECORDS. CONTACT YOUR ADMINISTRATOR", true);
		}

		return new ResponseModel(true);
	}

	private boolean createAccountingRecords(OpeningOperationPersistencePort openingOperationPersistencePort,
			OpeningOperationDto openingOperationToCreate, String companyCode, String idOperation) {
		LOG.info(String.format("%s FIND EMPLOYEE CONFIGURATION", idOperation));
		ResponseModel responseEmployeeConfiguration = openingOperationPersistencePort
				.findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(
						openingOperationToCreate.getEmployeeEmail(), openingOperationToCreate.getBranchCode(),
						companyCode, idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseEmployeeConfiguration.getData();

		LOG.info(String.format("%s INIT GENERATE ACCOUNTING RECORDS", idOperation));
		List<AccountingRecordDto> accountingRecordList = accountingRecordService
				.generateByOpeningOperation(openingOperationToCreate, employeeDto.getUserId());

		LOG.info(String.format("%s INIT SAVE ACCOUNTING RECORDS", idOperation));
		ResponseModel responseCreateAccountingRecord = accountingRecordPersistencePort
				.createAccountingRecordMovementByCompanyCode(accountingRecordList, companyCode, idOperation);

		boolean created = (boolean) responseCreateAccountingRecord.getData();

		return created;
	}

	@Override
	public ResponseModel getOpeningOperationStatusActiveByEmployeeEmail(
			OpeningOperationPersistencePort openingOperationPersistencePort, String employeeEmail, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getOpeningOperationStatusActiveByEmployeeEmail()", idOperation));
		ResponseModel responseGetStatus = openingOperationPersistencePort
				.findOpeningOperationActiveByEmployeeEmailAndCompanyCode(employeeEmail, companyCode, idOperation);
		OpeningOperationDto openingOperationDto = (OpeningOperationDto) responseGetStatus.getData();
		ResponseModel responseStatus = null;
		if (openingOperationDto == null) {
			responseStatus = new ResponseModel(false);
			responseStatus.setField("OPENING OPERATION NOT EXISTS");
			return responseStatus;
		}		
		boolean openingStatus = openingOperationValidation.isValidDate(openingOperationDto.getCreationDate());
		responseStatus = new ResponseModel(openingStatus);
		responseStatus.setField(openingStatus ? "OPENING OPERATION ACTIVE": "OPENING OPERATION NOT CORRESPOND TO THE CURRENT DAY");
		LOG.info(String.format("%s OPENING OPERATION STATUS %b", idOperation, openingStatus));
		return responseStatus;
	}

}
