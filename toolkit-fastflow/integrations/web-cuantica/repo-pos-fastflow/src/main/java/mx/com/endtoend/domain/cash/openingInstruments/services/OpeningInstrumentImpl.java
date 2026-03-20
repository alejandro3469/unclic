package mx.com.endtoend.domain.cash.openingInstruments.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.openingInstruments.business.OpeningInstrumentFactory;
import mx.com.endtoend.domain.cash.openingInstruments.business.OpeningInstrumentInterface;
import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.cash.openingInstruments.ports.api.OpeningInstrumentServicePort;
import mx.com.endtoend.domain.cash.openingInstruments.ports.spi.OpenigInstrumentPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase que se encarga de obtener las implementaciones correspondietes a los
 * distintos métodos con base en la configuración de cada compañía registrada en
 * el sistema
 * 
 * @author ddcasas
 *
 */
public class OpeningInstrumentImpl implements OpeningInstrumentServicePort {

	private OpenigInstrumentPersistencePort openigInstrumentPersistencePort;

	public OpeningInstrumentImpl(OpenigInstrumentPersistencePort openigInstrumentPersistencePort) {
		this.openigInstrumentPersistencePort = openigInstrumentPersistencePort;
	}

	private OpeningInstrumentFactory openingInstrumentFactory = new OpeningInstrumentFactory();

	private final Logger LOG = LoggerFactory.getLogger(OpeningInstrumentImpl.class);

	/**
	 * Método que se encarga de obtener la implementación concreta para la creación
	 * de los instrumentos de pago de las aperturas con base en el método
	 * configurado a cada compañía
	 * 
	 * @param openPaymentInstrumentDto objeto con los datos operativos de los
	 *                                 instrumentos de aperturas
	 * @param method                   código de identificación para la selección de
	 *                                 lógica de negocio
	 * @param companyCode              código de compañía
	 * @param idOperation              identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel createOpenPaymentInstrumentByCompanyCode(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String method, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createOpenPaymentInstrumentByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ openPaymentInstrumentDto: %s , companyCode: %s , method: %s ]",
				idOperation, openPaymentInstrumentDto.toString(), companyCode, method));

		OpeningInstrumentInterface openingInstrument = openingInstrumentFactory.getImplementationByCode(method);

		if (openingInstrument == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = openingInstrument.createOpenPaymentInstrument(
				openigInstrumentPersistencePort, openPaymentInstrumentDto, companyCode, idOperation);

		return responseFromPersistencePort;

	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la
	 * actualización de los instrumentos de de pago de las aperturas con base en el
	 * método configurado a cada compañía
	 * 
	 * @param openPaymentInstrumentDto objeto con datos operativos código de
	 *                                 identificación para la selección de lógica de
	 *                                 negocio
	 * @param method                   código de identificación para la selección de
	 *                                 lógica de negocio
	 * @param companyCode              código de compañía
	 * @param idOperation              identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel updateOpenPaymentInstrumentByCompanyCodeAndId(
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String method, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT updateOpenPaymentInstrumentByCompanyCodeAndId() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ openPaymentInstrumentDto: %s , companyCode: %s , method: %s ]",
				idOperation, openPaymentInstrumentDto.toString(), companyCode, method));

		OpeningInstrumentInterface openingInstrument = openingInstrumentFactory.getImplementationByCode(method);

		if (openingInstrument == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = openingInstrument.updateOpenPaymentInstrument(
				openigInstrumentPersistencePort, openPaymentInstrumentDto, companyCode, idOperation);

		return responseFromPersistencePort;
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la
	 * vizualización del detalle de los datos operativos de los instrumentos de pago
	 * por id con base en el método configurado a cada compañía
	 * 
	 * @param id          identificador de instrumento de pago de apertura
	 * @param method      código de identificación para la selección de lógica de
	 *                    negocio
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel viewOpenPaymentInstrumentByIAndCompanyCode(Long id, String method, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT viewOpenPaymentInstrumentByIAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , method: %s ]", idOperation, id.toString(),
				companyCode, method));

		OpeningInstrumentInterface openingInstrument = openingInstrumentFactory.getImplementationByCode(method);

		if (openingInstrument == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = openingInstrument
				.viewOpenPaymentInstrumentById(openigInstrumentPersistencePort, id, companyCode, idOperation);

		return responseFromPersistencePort;
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la consulta
	 * de los instrumentos de pago de las aperturas con base en el método
	 * configurado a cada compañía
	 * 
	 * @param enabled     indicador para obtener listas de datos activas o inactivas
	 * @param method      código de identificación para la selección de lógica de
	 *                    negocio
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel viewOpenPaymentInstrumentListByCompanyCodeAndEnable(boolean enabled, String method,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT viewOpenPaymentInstrumentListByCompanyCodeAndEnable() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ enabled: %b , companyCode: %s , method: %s ]", idOperation, enabled,
				companyCode, method));

		OpeningInstrumentInterface openingInstrument = openingInstrumentFactory.getImplementationByCode(method);

		if (openingInstrument == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = openingInstrument.viewOpenPaymentInstrumentListByEnable(
				openigInstrumentPersistencePort, enabled, companyCode, idOperation);

		return responseFromPersistencePort;
	}

}
