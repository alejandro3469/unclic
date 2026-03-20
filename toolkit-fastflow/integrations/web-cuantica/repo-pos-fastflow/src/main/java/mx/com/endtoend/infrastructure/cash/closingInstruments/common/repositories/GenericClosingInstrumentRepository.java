package mx.com.endtoend.infrastructure.cash.closingInstruments.common.repositories;

import java.util.List;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;

public interface GenericClosingInstrumentRepository {

	ClosePaymentInstrumentDto createClosePaymentInstrument(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String idOperation);

	ClosePaymentInstrumentDto updateClosePaymentInstrument(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String idOperation);

	ClosePaymentInstrumentDto validExisteByCodeOrNameToCreate(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String idOperation);

	ClosePaymentInstrumentDto validExisteByCodeOrNameToUpdate(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String idOperation);

	List<ClosePaymentInstrumentDto> getClosePaymentInstrumentListByEnable(boolean enable, String idOperation);

	ClosePaymentInstrumentDto getClosePaymentInstrumentById(Long id, String idOperation);

}
