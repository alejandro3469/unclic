package mx.com.endtoend.domain.cash.closingInstruments.ports.spi;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClosingInstrumentPersistencePort {
	
	ResponseModel createClosePaymentInstrumentByCompanyCode(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String companyCode, String idOperation);

	ResponseModel updateClosePaymentInstrumentByCompanyCode(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String companyCode, String idOperation);

	ResponseModel validExisteByCodeOrNameByCompanyCodeToCreate(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String companyCode, String idOperation);

	ResponseModel validExisteByCodeOrNameByCompanyCodeToUpdate(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String companyCode, String idOperation);

	ResponseModel getClosePaymentInstrumentListByEnableAndCompanyCode(boolean enable, String companyCode,
			String idOperation);

	ResponseModel getClosePaymentInstrumentByIdAndCompanyCode(Long id, String companyCode, String idOperation);

}
