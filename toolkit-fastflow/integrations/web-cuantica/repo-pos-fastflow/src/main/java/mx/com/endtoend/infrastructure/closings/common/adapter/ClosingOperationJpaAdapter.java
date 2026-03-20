package mx.com.endtoend.infrastructure.closings.common.adapter;

import mx.com.endtoend.infrastructure.closings.common.factory.ClosingOperationRepositoryFactory;
import mx.com.endtoend.infrastructure.closings.common.repositories.GenericClosingOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.closings.ports.ClosingOperationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase encargada de obtener la implementación concreta de los repositorias con
 * base en el código de compañia configurada en el sistema
 * 
 * @author ddcasas
 *
 */
@Service
public class ClosingOperationJpaAdapter implements ClosingOperationPersistencePort {

	@Autowired
	private ClosingOperationRepositoryFactory closingOperationRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(ClosingOperationJpaAdapter.class);

	@Override
	public ResponseModel createClosingOperationByCompanyCode(ClosingOperationDto closingOperationDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createClosingOperationByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closingOperationDto: %s , companyCode: %s ] ", idOperation,
				closingOperationDto.toString(), companyCode));
		GenericClosingOperationRepository repository = closingOperationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ClosingOperationDto closingOperationCreated = repository.createClosingOperation(closingOperationDto,
				idOperation);
		return new ResponseModel(closingOperationCreated);
	}

	@Override
	public ResponseModel findClosePaymentInstrumentByIdAndCompanyCode(Long id, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findClosePaymentInstrumentByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ id: %s , companyCode: %s ] ", idOperation, id.toString(), companyCode));
		GenericClosingOperationRepository repository = closingOperationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ClosePaymentInstrumentDto closePaymentInstrumentDto = repository
				.findClosePaymentInstrumentByIdAndCompanyCode(id, idOperation);
		return new ResponseModel(closePaymentInstrumentDto);
	}

	@Override
	public ResponseModel findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(String employeeEmail,
			String branchCode, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ employeeEmail: %s , branchCode: %s , companyCode: %s ] ", idOperation,
				employeeEmail, branchCode, companyCode));
		GenericClosingOperationRepository repository = closingOperationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		EmployeeDto employeeDto = repository.findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(employeeEmail,
				branchCode, idOperation);
		return new ResponseModel(employeeDto);
	}

}
