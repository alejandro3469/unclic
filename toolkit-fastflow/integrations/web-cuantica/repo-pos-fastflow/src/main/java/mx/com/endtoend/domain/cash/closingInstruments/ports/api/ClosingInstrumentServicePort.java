package mx.com.endtoend.domain.cash.closingInstruments.ports.api;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClosingInstrumentServicePort {

	ResponseModel createClosePaymentInstrumentByCompanyCode(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String method, String companyCode, String idOperation);

	ResponseModel updateClosePaymentInstrumentByCompanyCodeAndId(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String method, String companyCode, String idOperation);

	ResponseModel viewClosePaymentInstrumentListByCompanyCodeAndEnable(boolean enabled, String method,
			String companyCode, String idOperation);

	ResponseModel viewClosePaymentInstrumentByIdAndCompanyCode(Long id, String method, String companyCode,
			String idOperation);
}
