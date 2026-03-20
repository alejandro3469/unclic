package mx.com.endtoend.domain.openings.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.openings.business.OpeningOperationFactory;
import mx.com.endtoend.domain.openings.business.OpeningOperationInterface;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.domain.openings.ports.OpeningOperationServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase que se encarga de obtener la implementación correspondiente a los
 * distintos métodos con base en la configuración de cada compañía registrada en
 * el sistema
 * 
 * @author ddcasas
 *
 */
public class OpeningOperationServiceImpl implements OpeningOperationServicePort {

	private OpeningOperationPersistencePort openingOperationPersistencePort;

	public OpeningOperationServiceImpl(OpeningOperationPersistencePort openingOperationPersistencePort) {
		this.openingOperationPersistencePort = openingOperationPersistencePort;
	}

	private OpeningOperationFactory openingOperationFactory = new OpeningOperationFactory();

	private final Logger LOG = LoggerFactory.getLogger(OpeningOperationServiceImpl.class);

	/**
	 * Método que se encarga de obtener la implementación concreta para la creación
	 * de apertura de operación con base en el método configurado a cada compañía
	 * 
	 * @param accountingRecordPersistencePort puerto de comunicación hacia capa de
	 *                                        persistencia de los registros
	 *                                        contables
	 * @param openingOperationDto             datos operativos de la apertura de
	 *                                        operación
	 * @param method                          método configurado a la compañía
	 * @param companyCode                     código de compañía
	 * @param idOperation                     identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel createOpeningOperationByCompanyCode(
			AccountingRecordPersistencePort accountingRecordPersistencePort, OpeningOperationDto openingOperationDto,
			String method, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createOpeningOperationByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ openingOperationDto: %s , companyCode: %s , method: %s ]", idOperation,
				openingOperationDto.toString(), companyCode, method));

		OpeningOperationInterface openingOperation = openingOperationFactory
				.getImplementationByCode(accountingRecordPersistencePort, method);

		if (openingOperation == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = openingOperation
				.createOpeningOperation(openingOperationPersistencePort, openingOperationDto, companyCode, idOperation);

		return responseFromPersistencePort;
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para obtener el
	 * estado de las aperturas de operaciones con base en el método configurado a
	 * cada compañía
	 * 
	 * @param accountingRecordPersistencePort puerto de comunicación hacia capa de
	 *                                        persistencia de los registros
	 *                                        contables
	 * @param method                          método configurado a la compañía
	 * @param employeeEmail                   correo de empleado para la búsqueda de
	 *                                        datos
	 * @param companyCode                     código de compañia
	 * @param idOperation                     identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel validOpeningOperationStatusByEmployeeEmailAndCompanyCode(
			AccountingRecordPersistencePort accountingRecordPersistencePort, String method, String employeeEmail,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT validOpeningOperationStatusByEmployeeEmailAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ employeeEmail: %s , companyCode: %s , method: %s ]", idOperation,
				employeeEmail, companyCode, method));

		OpeningOperationInterface openingOperation = openingOperationFactory
				.getImplementationByCode(accountingRecordPersistencePort, method);

		if (openingOperation == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = openingOperation.getOpeningOperationStatusActiveByEmployeeEmail(
				openingOperationPersistencePort, employeeEmail, companyCode, idOperation);

		return responseFromPersistencePort;
	}

}
