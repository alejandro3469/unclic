package mx.com.endtoend.domain.payments.ports;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.ticket.PaymentTicketDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;

public interface PaymentPersistencePort {

	ResponseModel getOrdersToSendToQueue(BigDecimal orderNumber, String orderCode, String companyCode,
										 String idOperation);

	ResponseModel getPaymentDetailByOrderNumberAndOrderCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
			String companyCode, String idOperation);

	ResponseModel getBranchDetailByBranchCodeAndCompanyCode(String branchCode, String companyCode, String idOperation);

	ResponseModel getUserInformationByEmailAndCompanyCode(String email, String companyCode, String idOperation);

	List<PaymentOrderJDE> getOrdersToSendToQueueV2(BigDecimal orderNumber, String orderCode, String companyCode,
												   String idOperation);

	ResponseModel getOrderByCompanyCodeAndParams(BigDecimal orderNumber, String orderCode, String companyCode,
												 String idOperation);

	ResponseModel createPayment(PaymentDto paymentDto, String companyCode, String idOperation);

	ResponseModel getOpeningOperationByEmailAndCompanyCode(String email, String companyCode, String idOperation);

	ResponseModel getEmployeConfigurationByEmailAndCompanyCode(String email, String companyCode, String idOperation);

	void updateOrderStatusByOrderNumberAndCompanyCode(BigDecimal orderNumber, String orderCode,
			String statusCode, BigDecimal pendingPayment, Long batchFolio, String companyCode, String idOperation);

	ResponseModel saveRecordInOrderHistory(OrderHistoryDto orderHistoryDto, String companyCode, String idOperation);

	ResponseModel updateFlagByCompanyCodeAndParams(BigDecimal orderNumber, String orderCode, boolean enable,
			String idOperation, String companyCode);

	ResponseModel orderHasPayment(BigDecimal orderNumber, String orderCode, String idOperation, String companyCode);

	ResponseModel getSellerInformationByEmailAndCompanyCode(Long userNumber, String companyCode, String idOperation);

	ResponseModel getPaymentTicketByCompanyCode(PaymentTicketDto paymentTicketDto, String companyCode,
												String idOperation);

	ResponseModel searchPaidOrderSummaryByCompanyCode(GenericSearchPaymentDto genericSearchPaymentDto,
			String companyCode, String idOperation);

	ResponseModel saveInvoiceReferenceByCompanyCode(InvoiceReferenceDto invoiceReference, PaymentDto paymentDto,
			String branchCode, String companyCode, String idOperation);

	ResponseModel getInvoiceReferenceByPaymentIdAndCompanyCode(Long paymentId, String companyCode, String idOperation);

	void updatePrintStateByPaymentIdAndCompanyCode(Long paymentId, boolean printStatus, String companyCode,
			String idOperation);

	Object orderHasPayment(BigDecimal orderNumber, String orderCode);
}
