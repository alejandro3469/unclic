package mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;

import java.util.List;

public interface PaymentJDEServicePort {

	ResponseModel saveOrder(OrderDto orderDto, String companyCode, String idOperation);

	void sendOrderToSave(PaymentOrderJDE paymentOrderJDE, String companyCode, String idOperation);

    void resendNotesToQueue(PaymentOrderJDE paymentOrderJDE, String companyCode,
                            String idOperation);

    void sendOrderToSaveInvoiceByCompanyCoode(PaymentOrderJDE paymentOrderJDE, String companyCode, String idOperation);

	void sendCreditNoteToSaveByCompanyCoode(PaymentOrderJDE paymentOrderJDE, String companyCode, String idOperation);

	ResponseModel searchInvoiceRecordsByOrderAndCompanyCode(OrderDto orderDto,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation);

	ResponseModel getBathcFolioByCompanyCode(String nnsy, String companyCode, String idOperation);

    void sendOrdersToSaveToQueue(List<PaymentOrderJDE> paymentOrderJDE, String companyCode, String idOperation);

    ResponseModel getConsecutiveOrderNumberByCompanyCode(String orderType, String companyCode, String companyNumber,
                                                         String idOperation);

	ResponseModel getInvoiceRecordByBranchCodeAndCompanyCode(String branchCode, String companyCode,
			String companyNumber, String idOperation);
}
