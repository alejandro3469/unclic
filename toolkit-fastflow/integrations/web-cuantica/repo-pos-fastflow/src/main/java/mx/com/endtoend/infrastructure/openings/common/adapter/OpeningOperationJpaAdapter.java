package mx.com.endtoend.infrastructure.openings.common.adapter;

import mx.com.endtoend.infrastructure.openings.common.repositories.GenericOpeningOperationRepository;
import mx.com.endtoend.infrastructure.openings.common.factory.OpeningOperationRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase encargada de obtener la clase controladora de los repositorios con base
 * en la compañía configurada en el sistema
 * 
 * @author ddcasas
 *
 */
@Service
public class OpeningOperationJpaAdapter implements OpeningOperationPersistencePort {

	@Autowired
	private OpeningOperationRepositoryFactory openingOperationRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(OpeningOperationJpaAdapter.class);

	@Override
	public ResponseModel findOpenPaymentInstrumentByIdAndCompanyCode(Long id, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findOpenPaymentInstrumentByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ id: %s , companyCode: %s ] ", idOperation, id.toString(), companyCode));
		GenericOpeningOperationRepository repository = openingOperationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpenPaymentInstrumentDto openPaymentInstrumentDto = repository.findOpenPaymentInstrumentById(id, idOperation);
		return new ResponseModel(openPaymentInstrumentDto);
	}

	@Override
	public ResponseModel findOpeningOperationActiveByEmployeeEmailAndCompanyCode(String employeeEmail,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findOpeningOperationActiveByEmployeeEmailAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ employeeEmail: %s , companyCode: %s ] ", idOperation, employeeEmail,
				companyCode));
		GenericOpeningOperationRepository repository = openingOperationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpeningOperationDto openingOperationDto = repository.findOpeningOperationActiveByEmployeeEmail(employeeEmail,
				idOperation);
		return new ResponseModel(openingOperationDto);
	}

	@Override
	public ResponseModel createOpeningOperationByCompanyCode(OpeningOperationDto openingOperationDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createOpeningOperationByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ openingOperationDto: %s , companyCode: %s ] ", idOperation,
				openingOperationDto.toString(), companyCode));
		GenericOpeningOperationRepository repository = openingOperationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpeningOperationDto openingOperationCreated = repository.createOpeningOperation(openingOperationDto,
				idOperation);
		return new ResponseModel(openingOperationCreated);
	}

	@Override
	public ResponseModel findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(String employeeEmail,
			String branchCode, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ employeeEmail: %s , branchCode: %s , companyCode: %s ] ", idOperation,
				employeeEmail, branchCode, companyCode));
		GenericOpeningOperationRepository repository = openingOperationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		EmployeeDto employeeDto = repository.findEmployeeConfigurationByEmailAndBranchCode(employeeEmail, branchCode,
				idOperation);
		return new ResponseModel(employeeDto);
	}

	@Override
	public ResponseModel updateOperativeDataByIdAndCompanyCode(OpeningOperationDto openingOperationDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateOperativeDataByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ openingOperationDto: %s , companyCode: %s ] ", idOperation,
				openingOperationDto.toString(), companyCode));
		GenericOpeningOperationRepository repository = openingOperationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		boolean isOpeninigOperationUpdate = repository.updateOperativeDataById(openingOperationDto, idOperation);
		return new ResponseModel(isOpeninigOperationUpdate);
	}
}