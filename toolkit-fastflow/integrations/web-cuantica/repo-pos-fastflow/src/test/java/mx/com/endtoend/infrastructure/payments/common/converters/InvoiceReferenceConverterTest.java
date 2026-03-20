package mx.com.endtoend.infrastructure.payments.common.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mx.com.endtoend.infrastructure.payments.common.entities.InvoiceRerefenceEntity;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

/**
 * Tests para InvoiceReferenceConverter (facturación / invoice reference).
 */
class InvoiceReferenceConverterTest {

	private InvoiceReferenceConverter converter;

	@BeforeEach
	void setUp() {
		converter = new InvoiceReferenceConverter();
	}

	@Test
	void entityToDto_copyFields() {
		InvoiceRerefenceEntity entity = new InvoiceRerefenceEntity();
		entity.setInvoiceNumber(new BigDecimal("12345"));
		entity.setInvoiceCode("FAC-001");

		InvoiceReferenceDto dto = converter.invoiceReferenceEntityToInvoiceReferenceDto(entity);

		assertNotNull(dto);
		assertEquals(new BigDecimal("12345"), dto.getInvoiceNumber());
		assertEquals("FAC-001", dto.getInvoiceCode());
	}

	@Test
	void entityToDto_nullEntity_returnsNull() {
		assertNull(converter.invoiceReferenceEntityToInvoiceReferenceDto(null));
	}

	@Test
	void generateByPayment_copyFields() {
		InvoiceReferenceDto refDto = new InvoiceReferenceDto(new BigDecimal("999"), "DCT-01");
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setPaymentId(100L);
		paymentDto.setOrderNumber(new BigDecimal("2862820"));
		paymentDto.setOrderCode("ORD-001");
		String branchCode = "BR01";

		InvoiceRerefenceEntity entity = converter.generateByPayment(refDto, paymentDto, branchCode);

		assertNotNull(entity);
		assertNull(entity.getId());
		assertEquals(false, entity.isMigrated());
		assertEquals("BR01", entity.getBranchCode());
		assertEquals(new BigDecimal("999"), entity.getInvoiceNumber());
		assertEquals("DCT-01", entity.getInvoiceCode());
		assertEquals(100L, entity.getPaymentId());
		assertEquals(new BigDecimal("2862820"), entity.getOrderNumber());
		assertEquals("ORD-001", entity.getOrderCode());
	}

	@Test
	void generateByPayment_nullDto_returnsNull() {
		PaymentDto paymentDto = new PaymentDto();
		assertNull(converter.generateByPayment(null, paymentDto, "BR01"));
		assertNull(converter.generateByPayment(new InvoiceReferenceDto(), null, "BR01"));
	}

	@Test
	void generateByPayment_nullBranchCode_usesEmptyString() {
		InvoiceReferenceDto refDto = new InvoiceReferenceDto(BigDecimal.ONE, "X");
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setPaymentId(1L);
		paymentDto.setOrderNumber(BigDecimal.ONE);
		paymentDto.setOrderCode("C");

		InvoiceRerefenceEntity entity = converter.generateByPayment(refDto, paymentDto, null);

		assertNotNull(entity);
		assertEquals("", entity.getBranchCode());
	}
}
