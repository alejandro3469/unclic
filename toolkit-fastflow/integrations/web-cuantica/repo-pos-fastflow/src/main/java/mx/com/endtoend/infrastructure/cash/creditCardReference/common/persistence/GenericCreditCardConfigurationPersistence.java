package mx.com.endtoend.infrastructure.cash.creditCardReference.common.persistence;

import java.util.List;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardHistoryChangeDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.PaymentOptionDto;

public interface GenericCreditCardConfigurationPersistence {

	CreditCardDto createCreditCardReference(CreditCardDto creditCardDto, String idOperation);

	CreditCardDto updateCreditCardReference(CreditCardDto creditCardDto, String idOperation);

	void saveCreditCardChanges(List<CreditCardHistoryChangeDto> creditCardHistoryChangeList, String idOperation);

	CreditCardDto getCreditCardReferenceById(Long id, String idOperation);

	List<CreditCardDto> getCreditCardReferenceListByEnable(boolean enable, String idOperation);

	CreditCardDto findCreditCardReferenceByCodeOrInstitutionOrType(CreditCardDto creditCardDto, String idOperation);

	CreditCardDto findCreditCardReferenceByCodeOrInstitutionOrTypeAndIdNot(CreditCardDto creditCardDto,
			String idOperation);

	PaymentOptionDto findPaymentOptionByPeriodAndInstitutionAndType(String period, String institution, String type,
			String idOperation);

	PaymentOptionDto findPaymentOptionByPeriodAndInstitutionAndTypeAndIdNot(Long idPaymentOption, String period,
			String institution, String type, String idOperation);

}
