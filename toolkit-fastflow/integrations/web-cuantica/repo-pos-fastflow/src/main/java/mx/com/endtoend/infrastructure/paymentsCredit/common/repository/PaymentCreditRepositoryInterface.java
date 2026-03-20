package mx.com.endtoend.infrastructure.paymentsCredit.common.repository;

import java.math.BigDecimal;

import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleStausDto;
import mx.com.endtoend.domain.paymentsCredit.dto.PaymentCreditTicketDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface PaymentCreditRepositoryInterface {

	Boolean saveCreditSaleRequest(CreditSaleRequestDto creditSaleRequestDto, String idOperation);

	Boolean saveCreditSaleResponse(CreditSaleResponseDto creditSaleResponseDto, String idOperation);

	Boolean savePaymentStateRequest(StatusSaleRequestDto statusSaleRequestDto, String idOperation);

	Boolean savePaymentStateResponse(StatusSaleResponseDto statusSaleResponseDto, String idOperation);

	CreditSaleStausDto getPaymentStateByOrderNumberAndCode(BigDecimal orderNumber, String orderCode,
			String idOperation);

	ResponseModel generateCreditPaymentTicket(PaymentCreditTicketDto paymentCreditTicketDto, String idOperation);

	Boolean cancelCreditPaymentByOrderNumberAndCode(BigDecimal orderNumber, String orderCode, String idOperation);

}
