package mx.com.endtoend.infrastructure.cash.openingInstruments.common.repository;

import java.util.List;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;

public interface GenericOpeningInstrumentRepository {

	OpenPaymentInstrumentDto createOpenPaymentInstrumentByCompanyCode(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String idOperation);

	OpenPaymentInstrumentDto updateOpenPaymentInstrumentByCompanyCode(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String idOperation);

	OpenPaymentInstrumentDto validExisteByCodeOrNameByCompanyCodeToCreate(
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String idOperation);

	OpenPaymentInstrumentDto validExisteByCodeOrNameByCompanyCodeToUpdate(
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String idOperation);

	List<OpenPaymentInstrumentDto> getOpenPaymentInstrumentListByEnableAndCompanyCode(boolean enable,
			String idOperation);

	OpenPaymentInstrumentDto getOpenPaymentInstrumentById(Long id, String idOperation);
}
