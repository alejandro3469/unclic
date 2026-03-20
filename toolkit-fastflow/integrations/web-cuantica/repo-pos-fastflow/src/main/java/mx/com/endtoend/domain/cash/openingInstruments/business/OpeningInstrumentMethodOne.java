package mx.com.endtoend.domain.cash.openingInstruments.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.openingInstruments.business.validations.GenericOpeningInstrumentValidation;
import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.cash.openingInstruments.ports.spi.OpenigInstrumentPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase con la implementación de la lógica de negocio correspondiente al método
 * uno de los instrumentos de pago empleados en las aperturas de operación
 * 
 * @author ddcasas
 *
 */
public class OpeningInstrumentMethodOne implements OpeningInstrumentInterface {

	private GenericOpeningInstrumentValidation genericOpeningInstrumentValidation = new GenericOpeningInstrumentValidation();

	private final Logger LOG = LoggerFactory.getLogger(OpeningInstrumentMethodOne.class);

	/**
	 * Método para la creación de instrumentos de pago empleados en aperturas de
	 * operación
	 * 
	 * @param openigInstrumentPersistencePort puerto de comunicación con la capa de
	 *                                        infraestructura
	 * @param openPaymentInstrumentDto        datos operativos del instrumento de
	 *                                        pago de aprturas
	 * @param companyCode                     código de compañía
	 * @param idOperation                     identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y el código
	 *         de respuesta de la acción ejecutada
	 */
	@Override
	public ResponseModel createOpenPaymentInstrument(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createOpenPaymentInstrument()", idOperation));
		String validations = genericOpeningInstrumentValidation.validDataToSave(openigInstrumentPersistencePort,
				openPaymentInstrumentDto, true, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnCreate = openigInstrumentPersistencePort
				.createOpenPaymentInstrumentByCompanyCode(openPaymentInstrumentDto, companyCode, idOperation);
		openPaymentInstrumentDto = (OpenPaymentInstrumentDto) responseOnCreate.getData();
		if (openPaymentInstrumentDto == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(openPaymentInstrumentDto);
	}

	/**
	 * Método para la actualización de instrumentos de pago empleados en aperturas
	 * de operación
	 * 
	 * @param openigInstrumentPersistencePort puerto de comunicación con la capa de
	 *                                        infraestructura
	 * 
	 * @param openPaymentInstrumentDto        datos operativos del instrumento de
	 *                                        pago de aprturas
	 * @param companyCode                     código de compañía
	 * @param idOperation                     identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y el código
	 *         de respuesta de la acción ejecutada
	 */
	@Override
	public ResponseModel updateOpenPaymentInstrument(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateOpenPaymentInstrument()", idOperation));
		String validations = genericOpeningInstrumentValidation.validDataToSave(openigInstrumentPersistencePort,
				openPaymentInstrumentDto, false, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnUpdate = openigInstrumentPersistencePort
				.updateOpenPaymentInstrumentByCompanyCode(openPaymentInstrumentDto, companyCode, idOperation);
		openPaymentInstrumentDto = (OpenPaymentInstrumentDto) responseOnUpdate.getData();
		if (openPaymentInstrumentDto == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(openPaymentInstrumentDto);
	}

	/**
	 * Mpetodo para la recuperación del detalle de un instrumento de pago de
	 * apertura por su id
	 * 
	 * @param openigInstrumentPersistencePort puerto de comunicación con la capa de
	 *                                        infraestructura
	 * @param id                              identificador de instrumento de
	 *                                        apertura
	 * @param companyCode                     código de compañia
	 * @param idOperation                     identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y el código
	 *         de respuesta de la acción ejecutada
	 */
	@Override
	public ResponseModel viewOpenPaymentInstrumentById(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			Long id, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewOpenPaymentInstrumentById()", idOperation));
		ResponseModel responseDetail = openigInstrumentPersistencePort.getOpenPaymentInstrumentByIdAndCompanyCode(id,
				companyCode, idOperation);
		OpenPaymentInstrumentDto openPaymentInstrumentDto = (OpenPaymentInstrumentDto) responseDetail.getData();
		if (openPaymentInstrumentDto == null) {
			LOG.warn(String.format("%s DATA NOT FOUND", idOperation));
			openPaymentInstrumentDto = new OpenPaymentInstrumentDto();
		}
		return new ResponseModel(openPaymentInstrumentDto);
	}

	/**
	 * Método para la obtención de instrumentos de pago empleados en aperturas de
	 * operación activos o inactivos
	 * 
	 * @param openigInstrumentPersistencePort puerto de comunicación con la capa de
	 *                                        infraestructura
	 * @param enabled                         indicador para obtener listas activas
	 *                                        (true) o inactivas(false)
	 * @param companyCode                     código de compañía
	 * @param idOperation                     identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y el código
	 *         de respuesta de la acción ejecutada
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel viewOpenPaymentInstrumentListByEnable(
			OpenigInstrumentPersistencePort openigInstrumentPersistencePort, boolean enabled, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT viewOpenPaymentInstrumentListByEnable()", idOperation));
		ResponseModel responseLits = openigInstrumentPersistencePort
				.getOpenPaymentInstrumentListByEnableAndCompanyCode(enabled, companyCode, idOperation);
		List<OpenPaymentInstrumentDto> openPaymentInstrumentDtos = (List<OpenPaymentInstrumentDto>) responseLits
				.getData();
		if (openPaymentInstrumentDtos == null) {
			LOG.warn(String.format("%s RETURN EMPTY LIST", idOperation));
			openPaymentInstrumentDtos = new ArrayList<>();
		}
		return new ResponseModel(openPaymentInstrumentDtos);
	}

}
