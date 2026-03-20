package mx.com.endtoend.domain.paymentsCredit.business;

import java.math.BigDecimal;

import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface PaymentCreditInterface {

	ResponseModel saveCreditSaleRequest(CreditSaleRequestDto creditSaleRequestDto);

	ResponseModel saveCreditSaleResponse(CreditSaleResponseDto creditSaleResponseDto);

	ResponseModel savePaymentStateRequest(StatusSaleRequestDto saleStatusSaleRequestDto);

	ResponseModel savePaymentStateResponse(StatusSaleResponseDto statusSaleResponseDto);

	ResponseModel getPaymentStateByOrderNumberAndCode(BigDecimal orderNumber, String orderCode);

	ResponseModel generateCreditPaymentTicketByOrderNumberAndCode(BigDecimal orderNumber, String orderCode);

	ResponseModel cancelCreditPaymentByOrderNumberAndCode(BigDecimal orderNumber, String orderCode);

}
