package mx.com.endtoend.domain.closings.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.domain.closings.business.ClosingOperationFactory;
import mx.com.endtoend.domain.closings.business.ClosingOperationInterface;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.closings.ports.ClosingOperationPersistencePort;
import mx.com.endtoend.domain.closings.ports.ClosingOperationServicePort;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase que se encarga de obtener la implementación concreta para la creación
 * de cierres operativos
 * 
 * @author ddcasas
 *
 */
public class ClosingOperationServiceImpl implements ClosingOperationServicePort {

	private ClosingOperationPersistencePort closingOperationPersistencePort;

	public ClosingOperationServiceImpl(ClosingOperationPersistencePort closingOperationPersistencePort) {
		this.closingOperationPersistencePort = closingOperationPersistencePort;
	}

	private ClosingOperationFactory closingOperationFactory = new ClosingOperationFactory();

	private final Logger LOG = LoggerFactory.getLogger(ClosingOperationServiceImpl.class);

	/**
	 * Método que se encarga de obtener la implementación concreta para la creación
	 * de cierres de operación con base en el método configurado a las compañias del
	 * sistema
	 * 
	 * @param accountingRecordPersistencePort puerto de comunicación hacia
	 *                                        infraestructura de los registros
	 *                                        contables
	 * @param openingOperationPersistencePort puerto de comunicacipon hacia
	 *                                        infraestrutura de las aperturas de
	 *                                        operacion
	 * @param closingOperationDto             datos operativos del cierre
	 * @param method                          método configurado a la compañía
	 * @param companyCode                     código de compañia
	 * @param idOperation                     identificador de traza de operación
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel createClosingOperationByCompanyCode(
			AccountingRecordPersistencePort accountingRecordPersistencePort,
			OpeningOperationPersistencePort openingOperationPersistencePort,
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort,
			ClosingOperationDto closingOperationDto, String method, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createClosingOperationByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ closingOperationDto: %s , companyCode: %s , method: %s ]", idOperation,
				closingOperationDto.toString(), companyCode, method));

		ClosingOperationInterface closingOperation = closingOperationFactory.getImplementationByCode(
				accountingRecordPersistencePort, openingOperationPersistencePort, emailCashConfigurationPersistencePort,
				method);

		if (closingOperation == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = closingOperation.createClosingOperationByCompanyCode(
				closingOperationPersistencePort, closingOperationDto, companyCode, idOperation);

		return responseModel;
	}

}
