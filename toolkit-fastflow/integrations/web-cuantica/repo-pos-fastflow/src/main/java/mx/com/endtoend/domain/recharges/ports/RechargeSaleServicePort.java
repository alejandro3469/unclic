package mx.com.endtoend.domain.recharges.ports;

import mx.com.endtoend.domain.recharges.dto.CustomRechargeSaleParams;
import mx.com.endtoend.domain.recharges.dto.RechargeSaleDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface RechargeSaleServicePort {

	ResponseModel createRechargeSaleByCompanyCode(RechargeSaleDto rechargeSaleDto,
			CustomRechargeSaleParams customRechargeSaleParams);

	ResponseModel generateTicketRechargeByCompanyCode(CustomRechargeSaleParams customRechargeSaleParams);
	
	ResponseModel getCompanyPhoneList(CustomRechargeSaleParams customRechargeSaleParams);

}
