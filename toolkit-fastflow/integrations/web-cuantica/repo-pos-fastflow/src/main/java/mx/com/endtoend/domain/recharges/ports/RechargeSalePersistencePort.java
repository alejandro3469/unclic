package mx.com.endtoend.domain.recharges.ports;

import java.math.BigDecimal;

import mx.com.endtoend.domain.recharges.dto.RechargeRequestDto;
import mx.com.endtoend.domain.recharges.dto.RechargeTickteDto;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaRequest;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface RechargeSalePersistencePort {

	ResponseModel getCompanyConfigurationByRecharge(String companyCode, String idOperation);

	ResponseModel sendRechargeRequestToSELIAByCompanyCode(SeliaRequest seliaRequest, String companyCode,
			String idOperation);

	ResponseModel generateTicketRechargeSaleByCompanmyCode(RechargeTickteDto rechargeTickteDto, String companyCode,
			String idOperation);

	ResponseModel getCompanyPhoneListByCompanyCode(String companyCode, String idOperation);

	void saveRechargeRequestByCompanyCode(RechargeRequestDto rechargeRequestDto, String companyCode,
			String idOperation);

	ResponseModel getRechargeRequestByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
			String companyCode, String idOperation);

}
