package mx.com.endtoend.domain.payments.business;

import java.math.BigDecimal;

import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

public interface PaymentInterface {

	ResponseModel getOrderByCompanyCodeAndParams(PaymentPersistencePort paymentPersistencePort,
			PaymentCustomParams paymentCustomParams, GenericSerchParamsOrderDto genericSerchParamsOrderDto);

    ResponseModel saveAllOrders(PaymentPersistencePort paymentPersistencePort);

    ResponseModel createPayment(PaymentPersistencePort paymentPersistencePort, PaymentCustomParams paymentCustomParams,
                                PaymentDto paymentDto);

	ResponseModel cancelPaymentByParams(PaymentPersistencePort paymentPersistencePort,
			PaymentCustomParams paymentCustomParams, GenericSerchParamsOrderDto genericSerchParamsOrderDto);

	ResponseModel createPaymentTicketByOrderCodeAndType(PaymentPersistencePort paymentPersistencePort,
			PaymentCustomParams paymentCustomParams, BigDecimal orderNumber, String orderCode);

	ResponseModel searchPaidOrderSummaryByCompanyCode(PaymentPersistencePort paymentPersistencePort,
			PaymentCustomParams paymentCustomParams, GenericSearchPaymentDto genericSearchPaymentDto);

	ResponseModel searchPaidOrderDetailByCompanyCode(PaymentPersistencePort paymentPersistencePort,
			PaymentCustomParams paymentCustomParams, GenericSearchPaymentDto genericSearchPaymentDto);

	ResponseModel approvalPaymentByParamsAndCompanyCode(PaymentPersistencePort paymentPersistencePort,
			PaymentCustomParams paymentCustomParams, AuthorizationParmasDto authorizationParmasDto);

}
