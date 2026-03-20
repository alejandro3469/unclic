package mx.com.endtoend.domain.cash.closingInstruments.business;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.cash.closingInstruments.ports.spi.ClosingInstrumentPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClosingInstrumentInterface {

	ResponseModel createClosePaymentInstrumentByCompanyCode(
			ClosingInstrumentPersistencePort closingInstrumentPersistencePort,
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String companyCode, String idOperation);

	ResponseModel updateClosePaymentInstrumentByCompanyCodeAndId(
			ClosingInstrumentPersistencePort closingInstrumentPersistencePort,
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String companyCode, String idOperation);

	ResponseModel viewClosePaymentInstrumentListByCompanyCodeAndEnable(
			ClosingInstrumentPersistencePort closingInstrumentPersistencePort, boolean enabled, String companyCode,
			String idOperation);

	ResponseModel viewClosePaymentInstrumentByIdAndCompanyCode(
			ClosingInstrumentPersistencePort closingInstrumentPersistencePort, Long id, String companyCode,
			String idOperation);

}
