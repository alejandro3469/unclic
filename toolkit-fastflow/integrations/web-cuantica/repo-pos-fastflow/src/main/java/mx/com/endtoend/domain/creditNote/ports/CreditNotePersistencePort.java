package mx.com.endtoend.domain.creditNote.ports;

import java.math.BigDecimal;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.dto.ticket.CreditNoteTickteDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CreditNotePersistencePort {

	ResponseModel createCreditNoteByCompanyCode(CreditNoteDto creditNoteDto, String companyCode, String idOperation);

	ResponseModel searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(BigDecimal folio, String creditNoteCode,
			String companyCode, String idOperation);

	ResponseModel getOrderByCompanyCodeAndParams(BigDecimal orderNumber, String orderCode, String companyCode,
                                                 String idOperation);

    ResponseModel searchCreditNoteByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
                                                                     String companyCode, String idOperation);

	ResponseModel searchCreditNoteListByParamsAndCompanyCode(CreditNoteSearchParamsDto noteSearchParamsDto,
			String companyCode, String idOperation);

	ResponseModel generateTicketByCompanyCode(CreditNoteTickteDto creditNoteTickteDto, String companyCode,
			String idOperation);

	ResponseModel searchCreditNoteByIdAndCompanyCode(Long creditNoteId, String companyCode, String idOperation);

	void updateCreditNoteBalanceByParamsAndCompanyCode(CreditNoteDto creditNoteDto, String companyCode,
			String idOperation);

	void updteCreditNoteHeaderBalanceByParamsAndCompanyCode(CreditNoteHeaderDto creditNoteHeaderDto, String companyCode,
			String idOperation);

	void updatePrintStatusByIdAndCompanyCode(Long id, boolean printStatus, String companyCode, String idOperation);

}
