package mx.com.endtoend.infrastructure.recharges.common.repository;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.recharges.dto.CompanyPhoneDto;
import mx.com.endtoend.domain.recharges.dto.CompanyRechargeConfigurationDto;
import mx.com.endtoend.domain.recharges.dto.RechargeRequestDto;
import mx.com.endtoend.domain.recharges.dto.RechargeTickteDto;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaRequest;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaResponse;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericRechargeSaleRepository {

	CompanyRechargeConfigurationDto getCompanyConfigurationByRecharge(String idOperation);

	SeliaResponse sendRechargeRequestToSELIAByCompanyCode(SeliaRequest seliaRequest, String idOperation);

	ResponseModel generateTicketRechargeSaleByCompanmyCode(RechargeTickteDto rechargeTickteDto, String idOperation);

	List<CompanyPhoneDto> getCompanyPhoneListByCompanyCode(String idOperation);

	void saveRechargeRequest(RechargeRequestDto rechargeRequestDto, String idOperation);

	RechargeRequestDto getRechargeRequestByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
			String idOperation);
}
