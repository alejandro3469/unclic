package mx.com.endtoend.domain.creditNote.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteDetailDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

/**
 * Clase para la genración de datos operativos de las ordenes de cobro y notas
 * de crédito
 * 
 * @author ddcasas
 *
 */
public class OrderSummaryGenerator {

	/**
	 * Método que actualiza la cantidad la cantidad disponible de artículos de una
	 * orden de venta con los datos de las notas de crédito asociadas a esta
	 * 
	 * @param orderDto      datos operativos de la orden
	 * @param creditNoteDto datos operativos de las notas de credito de la orden
	 * @return OrderDto
	 */
	public OrderDto generateOrderSummary(OrderDto orderDto, CreditNoteDto creditNoteDto) {

		List<CreditNoteHeaderDto> creditNoteHeaderList = creditNoteDto.getCreditNoteHeaderList();
		List<CreditNoteDetailDto> creditNoteDetailList = new ArrayList<>();
		for (CreditNoteHeaderDto creditNoteHeader : creditNoteHeaderList) {
			creditNoteDetailList.addAll(creditNoteHeader.getCreditNoteDetail());
		}

		List<OrderDetailDto> orderDetailSummary = orderDto.getOrderDetail();
		for (OrderDetailDto orderDetail : orderDetailSummary) {
			BigDecimal amount = BigDecimal.ZERO;
			for (CreditNoteDetailDto creditNoteDetail : creditNoteDetailList) {
				if (orderDetail.getArticleCode().equals(creditNoteDetail.getArticleCode())) {
					amount = amount.add(creditNoteDetail.getRequestAmount());	
				}
			}
			orderDetail.setRequestAmount(orderDetail.getRequestAmount().subtract(amount));
		}
		orderDto.setOrderDetail(orderDetailSummary);
		return orderDto;
	}

}
