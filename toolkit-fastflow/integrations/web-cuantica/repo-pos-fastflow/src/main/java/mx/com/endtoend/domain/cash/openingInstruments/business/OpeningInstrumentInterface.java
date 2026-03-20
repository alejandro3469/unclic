package mx.com.endtoend.domain.cash.openingInstruments.business;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.cash.openingInstruments.ports.spi.OpenigInstrumentPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface OpeningInstrumentInterface {

	ResponseModel createOpenPaymentInstrument(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String companyCode, String idOperation);

	ResponseModel viewOpenPaymentInstrumentById(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			Long id, String companyCode, String idOperation);

	ResponseModel updateOpenPaymentInstrument(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String companyCode, String idOperation);

	ResponseModel viewOpenPaymentInstrumentListByEnable(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			boolean enabled, String companyCode, String idOperation);

}
