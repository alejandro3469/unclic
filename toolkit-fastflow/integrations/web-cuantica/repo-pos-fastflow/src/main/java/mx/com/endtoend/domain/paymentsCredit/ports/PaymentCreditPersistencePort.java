package mx.com.endtoend.domain.paymentsCredit.ports;

import java.math.BigDecimal;

import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.dto.PaymentCreditTicketDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface PaymentCreditPersistencePort {

	ResponseModel saveCreditSaleRequest(CreditSaleRequestDto creditSaleRequestDto, String companyCode,
			String idOperation);

	ResponseModel saveCreditSaleResponse(CreditSaleResponseDto creditSaleResponseDto, String companyCode,
			String idOperation);

	ResponseModel savePaymentStateRequest(StatusSaleRequestDto statusSaleRequestDto, String companyCode,
			String idOperation);

	ResponseModel savePaymentStateResponse(StatusSaleResponseDto statusSaleResponseDto, String companyCode,
			String idOperation);

	ResponseModel getPaymentStateByOrderNumberAndCode(BigDecimal orderNumber, String orderCode, String companyCode,
			String idOperation);

	ResponseModel generateCreditPaymentTicket(PaymentCreditTicketDto paymentCreditTicketDto, String companyCode,
			String idOperation);

	ResponseModel cancelCreditPaymentByOrderNumberAndCode(BigDecimal orderNumber, String orderCode, String companyCode,
			String idOperation);
}
