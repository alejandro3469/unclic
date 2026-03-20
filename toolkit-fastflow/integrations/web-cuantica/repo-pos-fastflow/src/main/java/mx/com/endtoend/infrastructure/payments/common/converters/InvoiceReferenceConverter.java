package mx.com.endtoend.infrastructure.payments.common.converters;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.payments.common.entities.InvoiceRerefenceEntity;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

@Component
public class InvoiceReferenceConverter {

	/**
	 * Convierte entidad de referencia de factura a DTO (generic model).
	 */
	public InvoiceReferenceDto invoiceReferenceEntityToInvoiceReferenceDto(
			InvoiceRerefenceEntity invoiceRerefenceEntity) {
		if (invoiceRerefenceEntity == null) {
			return null;
		}
		InvoiceReferenceDto dto = new InvoiceReferenceDto();
		dto.setInvoiceNumber(invoiceRerefenceEntity.getInvoiceNumber());
		dto.setInvoiceCode(invoiceRerefenceEntity.getInvoiceCode());
		return dto;
	}

	/**
	 * Genera entidad de referencia de factura a partir del DTO de factura y pago (generic model).
	 */
	public InvoiceRerefenceEntity generateByPayment(InvoiceReferenceDto invoiceReferenceDto, PaymentDto paymentDto,
			String branchCode) {
		if (invoiceReferenceDto == null || paymentDto == null) {
			return null;
		}
		InvoiceRerefenceEntity entity = new InvoiceRerefenceEntity();
		entity.setId(null);
		entity.setMigrated(false);
		entity.setBranchCode(branchCode != null ? branchCode : "");
		entity.setInvoiceNumber(invoiceReferenceDto.getInvoiceNumber());
		entity.setInvoiceCode(invoiceReferenceDto.getInvoiceCode());
		entity.setPaymentId(paymentDto.getPaymentId());
		entity.setOrderNumber(paymentDto.getOrderNumber());
		entity.setOrderCode(paymentDto.getOrderCode());
		return entity;
	}
}
