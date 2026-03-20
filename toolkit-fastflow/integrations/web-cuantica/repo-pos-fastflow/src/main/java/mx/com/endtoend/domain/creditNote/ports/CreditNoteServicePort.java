package mx.com.endtoend.domain.creditNote.ports;

import java.math.BigDecimal;

import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteCustomParams;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public interface CreditNoteServicePort {

	ResponseModel creteCreditNoteByOrder(CreditNoteCustomParams noteCustomParams, String method, String companyCode,
			String idOperation);

	ResponseModel resendNotesToQueue(CreditNoteCustomParams noteCustomParams, String method,
									 String companyCode, String idOperation); //TODO: Posiblemente quitar sobreescritura

	ResponseModel resendNotesToQueue(CreditNoteCustomParams noteCustomParams, String method,
									 String companyCode, String idOperation, BigDecimal folio);

	OrderDto getOrder(String orderNumber, String orderCode, String idOperation);

    ResponseModel aproveCreditNoteByParamsAndCompanyCode(CreditNoteCustomParams noteCustomParams,
                                                         AuthorizationParmasDto authorizationParmasDto, String method, String companyCode, String idOperation);

	ResponseModel searchOrderSummaryByNumberAndCode(CreditNoteCustomParams noteCustomParams, String method,
			String companyCode, String idOperation);

	ResponseModel obtainCreditNoteSummaryByNumberAndCode(CreditNoteCustomParams noteCustomParams, String method,
			String companyCode, String idOperation);

	ResponseModel viewDetailByCodeAndFolioAndCompanyCode(CreditNoteCustomParams noteCustomParams, BigDecimal folio,
			String creditNoteCode, String method, String companyCode, String idOperation);

	ResponseModel searchByParamsAndCompanyCode(CreditNoteCustomParams customParams,
			CreditNoteSearchParamsDto noteSearchParamsDto, String method, String companyCode, String idOperation);

	ResponseModel generateTicketByFolioAndCompanyCode(CreditNoteCustomParams customParams,
			CreditNoteSearchParamsDto noteSearchParamsDto, String method, String companyCode, String idOperation);

}
