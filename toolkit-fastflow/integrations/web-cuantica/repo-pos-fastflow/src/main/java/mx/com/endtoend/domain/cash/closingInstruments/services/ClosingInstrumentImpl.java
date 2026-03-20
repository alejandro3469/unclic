package mx.com.endtoend.domain.cash.closingInstruments.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.closingInstruments.business.ClosingInstrumentFactory;
import mx.com.endtoend.domain.cash.closingInstruments.business.ClosingInstrumentInterface;
import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.cash.closingInstruments.ports.api.ClosingInstrumentServicePort;
import mx.com.endtoend.domain.cash.closingInstruments.ports.spi.ClosingInstrumentPersistencePort;
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
public class ClosingInstrumentImpl implements ClosingInstrumentServicePort {

	private ClosingInstrumentPersistencePort closingInstrumentPersistencePort;

	public ClosingInstrumentImpl(ClosingInstrumentPersistencePort closnigInstrumentPersistencePort) {
		this.closingInstrumentPersistencePort = closnigInstrumentPersistencePort;
	}

	private ClosingInstrumentFactory closingInstrumentFactory = new ClosingInstrumentFactory();

	private final Logger LOG = LoggerFactory.getLogger(ClosingInstrumentImpl.class);

	@Override
	public ResponseModel createClosePaymentInstrumentByCompanyCode(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createClosePaymentInstrumentByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ closePaymentInstrumentDto: %s , companyCode: %s , method: %s ]",
				idOperation, closePaymentInstrumentDto.toString(), companyCode, method));
		ClosingInstrumentInterface closingInstrument = closingInstrumentFactory.getImplementationByCode(method);
		if (closingInstrument == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = closingInstrument.createClosePaymentInstrumentByCompanyCode(
				closingInstrumentPersistencePort, closePaymentInstrumentDto, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel updateClosePaymentInstrumentByCompanyCodeAndId(
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String method, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT updateClosePaymentInstrumentByCompanyCodeAndId() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ closePaymentInstrumentDto: %s , companyCode: %s , method: %s ]",
				idOperation, closePaymentInstrumentDto.toString(), companyCode, method));
		ClosingInstrumentInterface closingInstrument = closingInstrumentFactory.getImplementationByCode(method);
		if (closingInstrument == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = closingInstrument.updateClosePaymentInstrumentByCompanyCodeAndId(
				closingInstrumentPersistencePort, closePaymentInstrumentDto, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel viewClosePaymentInstrumentListByCompanyCodeAndEnable(boolean enabled, String method,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewClosePaymentInstrumentListByCompanyCodeAndEnable() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ enabled: %b , companyCode: %s , method: %s ]", idOperation, enabled,
				companyCode, method));
		ClosingInstrumentInterface closingInstrument = closingInstrumentFactory.getImplementationByCode(method);
		if (closingInstrument == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = closingInstrument
				.viewClosePaymentInstrumentListByCompanyCodeAndEnable(closingInstrumentPersistencePort, enabled,
						companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel viewClosePaymentInstrumentByIdAndCompanyCode(Long id, String method, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT viewClosePaymentInstrumentByIdAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , method: %s ]", idOperation, id.toString(),
				companyCode, method));
		ClosingInstrumentInterface closingInstrument = closingInstrumentFactory.getImplementationByCode(method);
		if (closingInstrument == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = closingInstrument.viewClosePaymentInstrumentByIdAndCompanyCode(
				closingInstrumentPersistencePort, id, companyCode, idOperation);
		return responseFromPersistencePort;
	}

}
