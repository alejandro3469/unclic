package mx.com.endtoend.domain.cash.openingInstruments.ports.api;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface OpeningInstrumentServicePort {

	ResponseModel createOpenPaymentInstrumentByCompanyCode(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String method, String companyCode, String idOperation);

	ResponseModel updateOpenPaymentInstrumentByCompanyCodeAndId(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String method, String companyCode, String idOperation);

	ResponseModel viewOpenPaymentInstrumentByIAndCompanyCode(Long id, String method, String companyCode,
			String idOperation);

	ResponseModel viewOpenPaymentInstrumentListByCompanyCodeAndEnable(boolean enabled, String method,
			String companyCode, String idOperation);
}
