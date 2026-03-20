package mx.com.endtoend.domain.cash.openingInstruments.ports.spi;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface OpenigInstrumentPersistencePort {

	ResponseModel createOpenPaymentInstrumentByCompanyCode(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String companyCode, String idOperation);

	ResponseModel updateOpenPaymentInstrumentByCompanyCode(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String companyCode, String idOperation);

	ResponseModel validExisteByCodeOrNameByCompanyCodeToCreate(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String companyCode, String idOperation);

	ResponseModel validExisteByCodeOrNameByCompanyCodeToUpdate(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String companyCode, String idOperation);

	ResponseModel getOpenPaymentInstrumentListByEnableAndCompanyCode(boolean enable, String companyCode,
			String idOperation);

	ResponseModel getOpenPaymentInstrumentByIdAndCompanyCode(Long id, String companyCode, String idOperation);

}
