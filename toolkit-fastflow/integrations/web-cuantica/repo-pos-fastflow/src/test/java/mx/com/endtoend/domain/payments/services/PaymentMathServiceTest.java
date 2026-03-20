package mx.com.endtoend.domain.payments.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

/**
 * Tests para PaymentMathService (validación SAT y redondeo en pagos / facturación).
 */
class PaymentMathServiceTest {

	private PaymentMathService service;

	@BeforeEach
	void setUp() {
		service = new PaymentMathService();
	}

	@Test
	void processPayment_nullPendingPayment_throws() {
		PaymentDto dto = new PaymentDto();
		dto.setPendingPayment(null);
		dto.setOrderTotal(new BigDecimal("100.00"));
		PaymentCustomParams params = new PaymentCustomParams(null, null, null, null, null, null, "", "");

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.processPayment(params, dto));
		assertEquals("PENDING PAYMENT CANNOT BE NULL", ex.getMessage());
	}

	@Test
	void processPayment_negativePending_throws() {
		PaymentDto dto = new PaymentDto();
		dto.setPendingPayment(new BigDecimal("-1"));
		dto.setOrderTotal(new BigDecimal("100.00"));
		PaymentCustomParams params = new PaymentCustomParams(null, null, null, null, null, null, "", "");

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.processPayment(params, dto));
		assertEquals("PENDING PAYMENT CANNOT BE NEGATIVE", ex.getMessage());
	}

	@Test
	void processPayment_moreThanTwoDecimalPlaces_throws() {
		PaymentDto dto = new PaymentDto();
		dto.setPendingPayment(new BigDecimal("99.999"));
		dto.setOrderTotal(new BigDecimal("100.00"));
		PaymentCustomParams params = new PaymentCustomParams(null, null, null, null, null, null, "", "");

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.processPayment(params, dto));
		assertEquals("PENDING PAYMENT CANNOT HAVE MORE THAN 2 DECIMAL PLACES", ex.getMessage());
	}

	@Test
	void processPayment_pendingMoreThanOrderTotal_throws() {
		PaymentDto dto = new PaymentDto();
		dto.setPendingPayment(new BigDecimal("200.00"));
		dto.setOrderTotal(new BigDecimal("100.00"));
		PaymentCustomParams params = new PaymentCustomParams(null, null, null, null, null, null, "", "");

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.processPayment(params, dto));
		assertEquals("PENDING PAYMENT CANNOT BE GREATER THAN ORDER TOTAL", ex.getMessage());
	}

	@Test
	void processPayment_validMinimal_returnsWithTwoDecimals() {
		PaymentDto dto = new PaymentDto();
		dto.setPendingPayment(new BigDecimal("99.99"));
		dto.setOrderTotal(new BigDecimal("150.55"));
		PaymentCustomParams params = new PaymentCustomParams(null, null, null, null, null, null, "", "");

		PaymentDto result = service.processPayment(params, dto);

		assertNotNull(result);
		assertEquals(new BigDecimal("99.99"), result.getPendingPayment());
		assertEquals(new BigDecimal("150.55"), result.getOrderTotal());
	}

	@Test
	void processPayment_validExactTwoDecimals_unchanged() {
		PaymentDto dto = new PaymentDto();
		dto.setPendingPayment(new BigDecimal("50.00"));
		dto.setOrderTotal(new BigDecimal("100.00"));
		PaymentCustomParams params = new PaymentCustomParams(null, null, null, null, null, null, "", "");

		PaymentDto result = service.processPayment(params, dto);

		assertNotNull(result);
		assertEquals(new BigDecimal("50.00"), result.getPendingPayment());
		assertEquals(new BigDecimal("100.00"), result.getOrderTotal());
	}
}
