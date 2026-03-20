package mx.com.endtoend.domain.payments.ports;

import java.math.BigDecimal;

import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

public interface PaymentServicePort {

	void saveAllOrders(PaymentCustomParams paymentCustomParams);

	ResponseModel getOrderByCompanyCodeAndParams(PaymentCustomParams paymentCustomParams,
			GenericSerchParamsOrderDto genericSerchParamsOrderDto, String method);

	ResponseModel createPayment(PaymentCustomParams paymentCustomParams, String method, PaymentDto paymentDto);

	ResponseModel cancelPaymentByCompanyCodeAndParams(PaymentCustomParams paymentCustomParams, String method,
			GenericSerchParamsOrderDto genericSerchParamsOrderDto);

	ResponseModel createPaymentTicketByOrderCodeAndType(PaymentCustomParams paymentCustomParams, String method,
			BigDecimal orderNumber, String orderCode);

	ResponseModel searchPaidOrderSummaryByCompanyCode(PaymentCustomParams paymentCustomParams,
			GenericSearchPaymentDto genericSearchPaymentDto, String method);

	ResponseModel searchPaidOrderDetailByCompanyCode(PaymentCustomParams paymentCustomParams,
			GenericSearchPaymentDto genericSearchPaymentDto, String method);

	ResponseModel approvalPaymentByParamsAndCompanyCode(PaymentCustomParams paymentCustomParams,
			AuthorizationParmasDto authorizationParmasDto, String method);

}
