package mx.com.endtoend.domain.creditNote.services;

import java.math.BigDecimal;

import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteCustomParams;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CreditNoteInterface {

	ResponseModel creteCreditNoteByOrder(CreditNoteCustomParams noteCustomParams, String companyCode,
			String idOperation);

	ResponseModel resendNotesToQueue(CreditNoteCustomParams noteCustomParams, String companyCode,
                                     String idOperation); // TODO: Posiblemente quitar sobreescritura

    ResponseModel resendNotesToQueue(CreditNoteCustomParams noteCustomParams, String companyCode,
                                     String idOperation, BigDecimal folio);

    ResponseModel aproveCreditNoteByParamsAndCompanyCode(CreditNoteCustomParams noteCustomParams,
                                                         AuthorizationParmasDto authorizationParmasDto, String companyCode, String idOperation);

	ResponseModel searchOrderSummaryByNumberAndCode(CreditNoteCustomParams noteCustomParams, String companyCode,
			String idOperation);

	ResponseModel obtainCreditNoteSummaryByNumberAndCode(CreditNoteCustomParams noteCustomParams, String companyCode,
			String idOperation);

	ResponseModel viewDetailByCodeAndFolioAndCompanyCode(BigDecimal folio, String creditNoteCode, String companyCode,
			String idOperation);

	ResponseModel searchByParamsAndCompanyCode(CreditNoteSearchParamsDto noteSearchParamsDto, String companyCode,
			String idOperation);

	ResponseModel generateTicketByFolioAndCompanyCode(CreditNoteSearchParamsDto noteSearchParamsDto, String companyCode,
			String idOperation);

}
