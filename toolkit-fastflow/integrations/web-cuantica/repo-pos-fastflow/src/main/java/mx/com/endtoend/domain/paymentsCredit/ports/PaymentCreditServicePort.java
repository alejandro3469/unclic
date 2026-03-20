package mx.com.endtoend.domain.paymentsCredit.ports;

import java.math.BigDecimal;

import mx.com.endtoend.domain.paymentsCredit.dto.CreditPaymentInterfaceService;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface PaymentCreditServicePort {

	ResponseModel saveCreditSaleRequestByMethod(CreditPaymentInterfaceService creditPaymentInterfaceService,
			CreditSaleRequestDto creditSaleRequestDto, String method);

	ResponseModel saveCreditSaleResponseByMethod(CreditPaymentInterfaceService creditPaymentInterfaceService,
			CreditSaleResponseDto creditSaleResponseDto, String method);

	ResponseModel savePaymentStateRequestByMethod(CreditPaymentInterfaceService creditPaymentInterfaceService,
			StatusSaleRequestDto saleStatusSaleRequestDto, String method);

	ResponseModel savePaymentStateResponseByMethod(CreditPaymentInterfaceService creditPaymentInterfaceService,
			StatusSaleResponseDto statusSaleResponseDto, String method);

	ResponseModel getPaymentStateByOrderNumberAndCodeByMethod(
			CreditPaymentInterfaceService creditPaymentInterfaceService, BigDecimal orderNumber, String orderCode,
			String method);

	ResponseModel generateCreditPaymentTicketByOrderNumberAndCodeByMethod(
			CreditPaymentInterfaceService creditPaymentInterfaceService, BigDecimal orderNumber, String orderCode,
			String method);
	
	ResponseModel cancelCreditPaymentByOrderNumberAndCodeByMethod(
			CreditPaymentInterfaceService creditPaymentInterfaceService, BigDecimal orderNumber, String orderCode,
			String method);
}
