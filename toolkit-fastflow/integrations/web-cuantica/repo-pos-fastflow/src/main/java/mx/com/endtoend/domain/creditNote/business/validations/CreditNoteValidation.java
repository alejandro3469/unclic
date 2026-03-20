package mx.com.endtoend.domain.creditNote.business.validations;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.commons.constants.OperationalLevelEnum;
import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.dto.InvoiceRecordDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

/**
 * Clase de validación de datos operativos para la generación de notas de
 * crédito
 * 
 * @author ddcasas
 *
 */
public class CreditNoteValidation {

	private static final String BAD_USER_CONFIGURATION = "-USER CONFIGURATION NOT FOUND BY CURRENT BRANCH";
	private static final String ITEMS_WITHOUT_BILLING_RECORDS = "ITEMS WITHOUT BILLING RECORDS AVAILABLE";

	public String validInvoiceRecordAndAvailability(OrderDto orderDto, List<InvoiceRecordDto> invoiceRecordList) {
		if (invoiceRecordList == null || invoiceRecordList.isEmpty()) {
			return ITEMS_WITHOUT_BILLING_RECORDS;
		}
		if (orderDto == null || orderDto.getOrderDetail() == null) {
			return "";
		}
		String invalidArticles = "";
		for (OrderDetailDto orderDetail : orderDto.getOrderDetail()) {
			for (InvoiceRecordDto invoiceRecordDto : invoiceRecordList) {
				if (orderDetail.getArticleCode().trim().equals(invoiceRecordDto.getArticleCode().trim())) {
					invalidArticles = invoiceRecordDto.getIsValid() ? invalidArticles
							: invalidArticles + orderDetail.getArticleCode().trim() + " BILLING AVAILABLE: "
									+ invoiceRecordDto.getQuantity() + ",";
				}
			}
		}
		return invalidArticles.isEmpty() ? "" : ITEMS_WITHOUT_BILLING_RECORDS + ": " + invalidArticles;
	}

	public String validParamsToSearchData(CreditNoteSearchParamsDto creditNoteSearchParamsDto,
			EmployeeDto employeeDto) {
		if (employeeDto == null) {
			return "USER-CONFIGURATION NOT FOUND";
		}
		if (creditNoteSearchParamsDto == null) {
			return "EMPTY DATE PARAMETERS";
		}
		return isEmptyParams(creditNoteSearchParamsDto) ? "EMPTY DATE PARAMETERS" : "";
	}

	/**
	 * Params are empty when both from and to dates are null.
	 */
	private boolean isEmptyParams(CreditNoteSearchParamsDto creditNoteSearchParamsDto) {
		return creditNoteSearchParamsDto.getFrom() == null && creditNoteSearchParamsDto.getTo() == null;
	}

	public String validOperativeDataToCreate(OrderDto orderDto, CreditNoteDto creditNoteDto, EmployeeDto employeeDto,
			OrderDto orderSummary, OrderDto orderToApply) {
		String validations = "";

		if (employeeDto == null)
			return "USER CONFIGURATION NOT FOUND";

		if (creditNoteDto != null)
			validations = isTotalCreditNote(creditNoteDto);

		if (validations.isEmpty())
			validations = validOrderStatus(orderDto);

		if (validations.isEmpty())
			validations = validRequestAmount(orderSummary, orderToApply);

		return validations;
	}

	public String validOperativeRoleJob(EmployeeDto employeeDto) {
		if (employeeDto == null || employeeDto.getRoleJob() == null) {
			return BAD_USER_CONFIGURATION;
		}
		String roleJob = employeeDto.getRoleJob().getCode();
		boolean isInvalid = OperationalLevelEnum.OPERATIONAL.toString().equals(roleJob)
				|| OperationalLevelEnum.SUPERVISION_III.toString().equals(roleJob);
		return isInvalid ? " -INVALID OPERATIVE ROLE JOB" : "";
	}

	public String validOrderConfiguration(OrderConfigurationDto orderConfigurationDto) {
		String validations = "";
		validations = orderConfigurationDto == null ? validations + "EMPTY CONFIGURATION" : validations;
		validations = orderConfigurationDto != null
				? orderConfigurationDto.getIsApplyCreditNote() ? validations : "ORDER NOT APPLY TO CREDIT NOTE"
				: validations;

		return validations;
	}

	public String validOrderStatus(OrderDto orderDto) {
		if (orderDto == null) {
			return "ORDER NOT FOUND";
		}
		if (orderDto.getStatus() == null) {
			return " INVALID ORDER STATUS ";
		}
		String orderState = orderDto.getStatus().getCode();
		return StatusOrder.FULL_PAYMENT.getValue().equals(orderState) ? "" : " INVALID ORDER STATUS ";
	}

	public String isTotalCreditNote(CreditNoteDto creditNoteDto) {
		return creditNoteDto != null && creditNoteDto.getIsTotal() ? "CREDIT NOTE COMPLETE" : "";
	}

	public String validRequestAmount(OrderDto orderSummary, OrderDto orderToApply) {
		if (orderSummary == null || orderToApply == null
				|| orderSummary.getOrderDetail() == null || orderToApply.getOrderDetail() == null) {
			return "";
		}
		String validations = "";
		for (OrderDetailDto detailSummary : orderSummary.getOrderDetail()) {
			for (OrderDetailDto detailApply : orderToApply.getOrderDetail()) {
				if (detailSummary.getArticleCode().equals(detailApply.getArticleCode())) {
					validations = (detailSummary.getRequestAmount().subtract(detailApply.getRequestAmount())).compareTo(BigDecimal.ZERO) < 0
							? validations + detailSummary.getArticleCode().trim() + ","
							: validations;
				}
			}
		}
		return validations.isEmpty() ? "" : "INSUFFICIENT QUANTITY BY ARTICLES: " + validations;
	}

}
