package mx.com.endtoend.infrastructure.payments.common.persistence;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaidOrderSummaryDto;
import mx.com.endtoend.domain.payments.dto.ticket.PaymentTicketDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

import javax.transaction.Transactional;

public interface GenericPaymentPersistenceInterface {

	@Transactional
	List<OrderDto> getOrdersToSendToQueue();

    @Transactional
    PaymentDto findByOrderNumberAndOrderCode(BigDecimal orderNumber, String orderCode, String companyCode, String idOperation);


	OrderDto getOrderByParams(BigDecimal orderNumber, String orderCode, String companyCode, String idOperation);

	PaymentDto createPayment(PaymentDto paymentDto, String idOperation);

	OpeningOperationDto getOpeningOperationByEmail(String email, String idOperation);

	EmployeeDto getEmployeConfigurationByEmailAndCompanyCode(String email, String companyCode, String idOperation);

	void updateOrderStatusByOrderNumberAndCompanyCode(BigDecimal orderNumber, String orderCode, String statusCode,
			BigDecimal pendingPayment, Long batchFolio, String idOperation);

	OrderHistoryDto saveRecordInOrderHistory(OrderHistoryDto orderHistoryDto, String idOperation);

	boolean updateFlagByParams(BigDecimal orderNumber, String orderCode, boolean enable, String idOperation);

	@Transactional
	UserDto getSellerInformationByEmailAndCompanyCode(Long userNumber, String companyCode, String idOperation);

	PaymentDto getPaymentDetailByOrderNumberAndOrderCode(BigDecimal orderNumber, String orderCode, String idOperation);

	BranchDto getBranchDetailByBranchCodeAndCompanyCode(String branchCode, String companyCode, String idOperation);

	UserDto getUserInformationByEmailAndCompanyCode(String email, String companyCode, String idOperation);

	ResponseModel getPaymentTicket(PaymentTicketDto paymentTicketDto, String idOperation);

	List<PaidOrderSummaryDto> searchPaidOrderSummaryByCompanyCode(GenericSearchPaymentDto genericSearchPaymentDto,
			String idOperation);

	boolean saveInvoiceReference(InvoiceReferenceDto invoiceReference, PaymentDto paymentDto, String branchCode,
			String idOperation);

	InvoiceReferenceDto getInvoiceReferenceByPaymentIdAndCompanyCode(Long paymentId, String idOperation);

	void updatePrintStateByPaymentId(Long paymentId, boolean printStatus, String idOperation);

    boolean orderHasPayment(BigDecimal orderNumber, String orderCode);
}
