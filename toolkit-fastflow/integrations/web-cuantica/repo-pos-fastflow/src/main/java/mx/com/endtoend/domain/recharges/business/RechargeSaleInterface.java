package mx.com.endtoend.domain.recharges.business;

import java.math.BigDecimal;

import mx.com.endtoend.domain.recharges.dto.RechargeSaleDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface RechargeSaleInterface {

	ResponseModel createRechargeSale(RechargeSaleDto rechargeSaleDto);

	ResponseModel generateTicketRecharge(String orderCode, BigDecimal orderNumber);

	ResponseModel getCompanyPhoneList();

}
