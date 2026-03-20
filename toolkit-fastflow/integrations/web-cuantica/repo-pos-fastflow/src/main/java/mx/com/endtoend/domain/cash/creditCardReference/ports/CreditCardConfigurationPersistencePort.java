package mx.com.endtoend.domain.cash.creditCardReference.ports;

import java.util.List;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardHistoryChangeDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CreditCardConfigurationPersistencePort {

	ResponseModel createCreditCardReferenceByCompanyCode(CreditCardDto creditCardDto, String companyCode,
			String idOperation);

	ResponseModel updateCreditCardReferenceByCompanyCode(CreditCardDto creditCardDto, String companyCode,
			String idOperation);

	void saveCreditCardChangesByCompanyCode(List<CreditCardHistoryChangeDto> creditCardHistoryChangeList,
			String companyCode, String idOperation);

	ResponseModel getCreditCardReferenceByIdAndCompanyCode(Long id, String companyCode, String idOperation);

	ResponseModel getCreditCardReferenceListByCompanyCodeAndEnable(boolean enable, String companyCode,
			String idOperation);

	ResponseModel findCreditCardReferenceByCodeOrInstitutionOrTypeAndCompanyCode(CreditCardDto creditCardDto,
			String companyCode, String idOperation);

	ResponseModel findCreditCardReferenceByCodeOrInstitutionOrTypeAndIdNotAndCompanyCode(CreditCardDto creditCardDto,
			String companyCode, String idOperation);

	ResponseModel findPaymentOptionByPeriodAndInstitutionAndTypeCompanyCode(String period, String institution,
			String type, String companyCode, String idOperation);

	ResponseModel findPaymentOptionByPeriodAndInstitutionAndTypeAndIdNotAndCompanyCode(Long idPaymentOption,
			String period, String institution, String type, String companyCode, String idOperation);

}
