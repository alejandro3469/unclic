package mx.com.endtoend.domain.creditNote.business.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mx.com.endtoend.domain.commons.constants.OperationalLevelEnum;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.dto.InvoiceRecordDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.dto.RoleJobTypeDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;

/**
 * Tests para CreditNoteValidation (notas de crédito y facturación).
 */
class CreditNoteValidationTest {

	private CreditNoteValidation validation;

	@BeforeEach
	void setUp() {
		validation = new CreditNoteValidation();
	}

	@Test
	void validParamsToSearchData_nullEmployee_returnsUserConfigNotFound() {
		assertEquals("USER-CONFIGURATION NOT FOUND",
				validation.validParamsToSearchData(new CreditNoteSearchParamsDto(), null));
	}

	@Test
	void validParamsToSearchData_nullParams_returnsEmptyDateParameters() {
		EmployeeDto employee = new EmployeeDto();
		assertEquals("EMPTY DATE PARAMETERS", validation.validParamsToSearchData(null, employee));
	}

	@Test
	void validParamsToSearchData_bothDatesNull_returnsEmptyDateParameters() {
		EmployeeDto employee = new EmployeeDto();
		CreditNoteSearchParamsDto params = new CreditNoteSearchParamsDto();
		params.setFrom(null);
		params.setTo(null);
		assertEquals("EMPTY DATE PARAMETERS", validation.validParamsToSearchData(params, employee));
	}

	@Test
	void validParamsToSearchData_hasFrom_returnsEmpty() {
		EmployeeDto employee = new EmployeeDto();
		CreditNoteSearchParamsDto params = new CreditNoteSearchParamsDto();
		params.setFrom(new Date());
		params.setTo(null);
		assertEquals("", validation.validParamsToSearchData(params, employee));
	}

	@Test
	void validOrderStatus_nullOrder_returnsOrderNotFound() {
		assertEquals("ORDER NOT FOUND", validation.validOrderStatus(null));
	}

	@Test
	void validOrderStatus_fullPayment_returnsEmpty() {
		OrderDto order = new OrderDto();
		StatusDto status = new StatusDto();
		status.setCode("50");
		order.setStatus(status);
		assertEquals("", validation.validOrderStatus(order));
	}

	@Test
	void validOrderStatus_invalidStatus_returnsInvalidOrderStatus() {
		OrderDto order = new OrderDto();
		StatusDto status = new StatusDto();
		status.setCode("10");
		order.setStatus(status);
		assertTrue(validation.validOrderStatus(order).contains("INVALID ORDER STATUS"));
	}

	@Test
	void isTotalCreditNote_total_returnsCreditNoteComplete() {
		CreditNoteDto dto = new CreditNoteDto();
		dto.setIsTotal(true);
		assertEquals("CREDIT NOTE COMPLETE", validation.isTotalCreditNote(dto));
	}

	@Test
	void isTotalCreditNote_notTotal_returnsEmpty() {
		CreditNoteDto dto = new CreditNoteDto();
		dto.setIsTotal(false);
		assertEquals("", validation.isTotalCreditNote(dto));
	}

	@Test
	void isTotalCreditNote_null_returnsEmpty() {
		assertEquals("", validation.isTotalCreditNote(null));
	}

	@Test
	void validOrderConfiguration_null_returnsEmptyConfiguration() {
		assertTrue(validation.validOrderConfiguration(null).contains("EMPTY CONFIGURATION"));
	}

	@Test
	void validOperativeRoleJob_nullEmployee_returnsBadUserConfiguration() {
		assertTrue(validation.validOperativeRoleJob(null).contains("USER CONFIGURATION NOT FOUND"));
	}

	@Test
	void validInvoiceRecordAndAvailability_emptyList_returnsItemsWithoutBillingRecords() {
		assertEquals("ITEMS WITHOUT BILLING RECORDS AVAILABLE",
				validation.validInvoiceRecordAndAvailability(new OrderDto(), Collections.emptyList()));
	}

	@Test
	void validInvoiceRecordAndAvailability_nullList_returnsItemsWithoutBillingRecords() {
		assertEquals("ITEMS WITHOUT BILLING RECORDS AVAILABLE",
				validation.validInvoiceRecordAndAvailability(new OrderDto(), null));
	}

	@Test
	void validInvoiceRecordAndAvailability_nullOrder_returnsEmpty() {
		List<InvoiceRecordDto> list = new ArrayList<>();
		list.add(new InvoiceRecordDto());
		assertEquals("", validation.validInvoiceRecordAndAvailability(null, list));
	}
}
