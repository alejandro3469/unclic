package mx.com.endtoend.domain.cash.closingInstruments.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.closingInstruments.business.validations.GenericCloseInstrumentValidation;
import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.cash.closingInstruments.ports.spi.ClosingInstrumentPersistencePort;
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
public class ClosingInstrumentMethodOne implements ClosingInstrumentInterface {

	private GenericCloseInstrumentValidation genericCloseInstrumentValidation = new GenericCloseInstrumentValidation();

	private final Logger LOG = LoggerFactory.getLogger(ClosingInstrumentMethodOne.class);

	/**
	 * Método para la creación de instrumentos de pago empleados en cierres de
	 * operación
	 * 
	 * @param closingInstrumentPersistencePort
	 * @param closePaymentInstrumentDto
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y el código
	 *         de respuesta de la acción ejecutada
	 */
	@Override
	public ResponseModel createClosePaymentInstrumentByCompanyCode(
			ClosingInstrumentPersistencePort closingInstrumentPersistencePort,
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createClosePaymentInstrumentByCompanyCode()", idOperation));
		String validations = genericCloseInstrumentValidation.validDataToSave(closingInstrumentPersistencePort,
				closePaymentInstrumentDto, true, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnCreate = closingInstrumentPersistencePort
				.createClosePaymentInstrumentByCompanyCode(closePaymentInstrumentDto, companyCode, idOperation);
		closePaymentInstrumentDto = (ClosePaymentInstrumentDto) responseOnCreate.getData();
		if (closePaymentInstrumentDto == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(closePaymentInstrumentDto);
	}

	/**
	 * Método para la actualización de instrumentos de pago empleados en cierres de
	 * operación
	 * 
	 * @param closingInstrumentPersistencePort
	 * @param closePaymentInstrumentDto
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y el código
	 *         de respuesta de la acción ejecutada
	 */
	@Override
	public ResponseModel updateClosePaymentInstrumentByCompanyCodeAndId(
			ClosingInstrumentPersistencePort closingInstrumentPersistencePort,
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateClosePaymentInstrumentByCompanyCodeAndId()", idOperation));
		String validations = genericCloseInstrumentValidation.validDataToSave(closingInstrumentPersistencePort,
				closePaymentInstrumentDto, false, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnUpdate = closingInstrumentPersistencePort
				.updateClosePaymentInstrumentByCompanyCode(closePaymentInstrumentDto, companyCode, idOperation);
		closePaymentInstrumentDto = (ClosePaymentInstrumentDto) responseOnUpdate.getData();
		if (closePaymentInstrumentDto == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(closePaymentInstrumentDto);
	}

	/**
	 * Método para la obtención de instrumentos de pago empleados en cierres de
	 * operación activos o inactivos
	 * 
	 * @param closingInstrumentPersistencePort
	 * @param enabled
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y el código
	 *         de respuesta de la acción ejecutada
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel viewClosePaymentInstrumentListByCompanyCodeAndEnable(
			ClosingInstrumentPersistencePort closingInstrumentPersistencePort, boolean enabled, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT viewClosePaymentInstrumentListByCompanyCodeAndEnable()", idOperation));
		ResponseModel responseLits = closingInstrumentPersistencePort
				.getClosePaymentInstrumentListByEnableAndCompanyCode(enabled, companyCode, idOperation);
		List<ClosePaymentInstrumentDto> closePaymentInstrumentDtos = (List<ClosePaymentInstrumentDto>) responseLits
				.getData();
		if (closePaymentInstrumentDtos == null) {
			LOG.warn(String.format("%s RETURN EMPTY LIST", idOperation));
			closePaymentInstrumentDtos = new ArrayList<>();
		}
		return new ResponseModel(closePaymentInstrumentDtos);
	}

	/**
	 * Mpetodo para la recuperación del detalle de un instrumento de pago de cierre
	 * por su id
	 * 
	 * @param closingInstrumentPersistencePort
	 * @param id
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y el código
	 *         de respuesta de la acción ejecutada
	 */
	@Override
	public ResponseModel viewClosePaymentInstrumentByIdAndCompanyCode(
			ClosingInstrumentPersistencePort closingInstrumentPersistencePort, Long id, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT viewClosePaymentInstrumentByIdAndCompanyCode()", idOperation));
		ResponseModel responseDetail = closingInstrumentPersistencePort.getClosePaymentInstrumentByIdAndCompanyCode(id,
				companyCode, idOperation);
		ClosePaymentInstrumentDto closePaymentInstrumentDto = (ClosePaymentInstrumentDto) responseDetail.getData();
		if (closePaymentInstrumentDto == null) {
			LOG.warn(String.format("%s DATA NOT FOUND", idOperation));
			closePaymentInstrumentDto = new ClosePaymentInstrumentDto();
		}
		return new ResponseModel(closePaymentInstrumentDto);
	}

}
