package mx.com.endtoend.infrastructure.services.jde.payments.common.repository;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.creditNote.dto.InvoiceRecordDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;

public interface GenericPaymentJdeRepository {

	boolean saveOrder(OrderDto orderDto, String idOperation);

	void sendOrderToSave(PaymentOrderJDE paymentOrderJDE);

	void sendCreditNoteToSaveByCompanyCoode(PaymentOrderJDE paymentOrderJDE);

    void resendNotesToQueue(PaymentOrderJDE paymentOrderJDE);

    void sendOrderToSaveInvoice(PaymentOrderJDE paymentOrderJDE);

	Long getBathcFolioByExternalServiceByNNSY(String nnsy, String idOperation);

	BigDecimal getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(String orderType, String companyNumber,
			String idOperation);

	List<InvoiceRecordDto> searchInvoiceRecordsByOrden(OrderDto orderDto, OrderConfigurationDto orderConfigurationDto,
			String idOperation);

	InvoiceReferenceDto getInvoiceRecordByBranchCodeAndCompanyCode(String branchCode, String companyNumber,
			String idOperation);
}
