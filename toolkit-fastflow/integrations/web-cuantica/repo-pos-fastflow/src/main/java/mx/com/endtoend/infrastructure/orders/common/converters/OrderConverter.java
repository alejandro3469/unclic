package mx.com.endtoend.infrastructure.orders.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.orders.common.entities.OrderEntity;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

@Component
public class OrderConverter {

	@Autowired
	private StatusConverter statusConverter;

	public OrderDto orderEntityToOrderDto(OrderEntity orderEntity, List<String> params) {
        return orderEntityToOrderDto(orderEntity, params, null);
    }

	public OrderDto orderEntityToOrderDto(OrderEntity orderEntity, List<String> params, ClientDto clientDto) {

		OrderDto orderDto = new OrderDto();

		orderDto.setOrderId(orderEntity.getOrderId());
		orderDto.setOrderNumber(orderEntity.getOrderNumber());
		orderDto.setBatchFolio(orderEntity.getBatchFolio());
		orderDto.setOrderCode(orderEntity.getOrderCode());
		orderDto.setBranchCode(orderEntity.getBranchCode());
		orderDto.setCompanyNumber(orderEntity.getCompanyNumber());
		orderDto.setCurrency(orderEntity.getCurrency());
		orderDto.setExchangeRate(orderEntity.getExchangeRate());
		orderDto.setCreationDate(orderEntity.getCreationDate());
		orderDto.setRequestDate(orderEntity.getRequestDate());
		orderDto.setValidityDate(orderEntity.getValidityDate());
		orderDto.setClientTax(orderEntity.getClientTax());
		if (clientDto != null) {
            orderDto.setClient(clientDto);
        }
		orderDto.setClientReference(orderEntity.getClientReference());
		orderDto.setUserNumber(orderEntity.getUserNumber());
		orderDto.setEmployeeEmail(orderEntity.getEmployeeEmail());
		orderDto.setSubTotal(orderEntity.getSubTotal());
		orderDto.setIvaTotal(orderEntity.getIvaTotal());
		orderDto.setOrderTotal(orderEntity.getOrderTotal());
		orderDto.setPendingPayment(orderEntity.getPendingPayment());
		orderDto.setDiscountTotal(orderEntity.getDiscountTotal());
		orderDto.setRetentionCode(orderEntity.getRetentionCode());
		orderDto.setOrderType(orderEntity.getOrderType());
		orderDto.setTempMigStatus(orderEntity.getTempMigStatus());
		orderDto.setIsUpdated(orderEntity.isUpdated());
		orderDto.setObservations(orderEntity.getObservations());
		orderDto.setIsRetentionOrder(orderEntity.isRetentionOrder());
		orderDto.setCfdiType(orderEntity.getCfdiType());
		orderDto.setTimeActive(orderEntity.getTimeActive());
		orderDto.setIsConverted(orderEntity.isConverted());
		orderDto.setIdUser(orderEntity.getIdUser());
		orderDto.setIsInvoiceTop(orderEntity.isInvoiceTop());
		orderDto.setInvoiceAmount(orderEntity.getInvoiceAmount() != null ? orderEntity.getInvoiceAmount() : BigDecimal.ZERO);

		if (params.contains("status")) {
			orderDto.setStatus(statusConverter.statusEntityToStatusDto(orderEntity.getStatus()));
		}

		return orderDto;
	}

	public OrderEntity orderDtoToOrderEntity(OrderDto orderDto) {

		OrderEntity orderEntity = new OrderEntity();

		orderEntity.setOrderId(orderDto.getOrderId());
		orderEntity.setOrderNumber(orderDto.getOrderNumber());
		orderEntity.setBatchFolio(orderDto.getBatchFolio());
		orderEntity.setOrderCode(orderDto.getOrderCode());
		orderEntity.setBranchCode(orderDto.getBranchCode());
		orderEntity.setCompanyNumber(orderDto.getCompanyNumber());
		orderEntity.setCurrency(orderDto.getCurrency());
		orderEntity.setExchangeRate(orderDto.getExchangeRate());
		orderEntity.setCreationDate(orderDto.getCreationDate());
		orderEntity.setRequestDate(orderDto.getRequestDate());
		orderEntity.setValidityDate(orderDto.getValidityDate());
		orderEntity.setClientTax(orderDto.getClientTax());
		orderEntity.setClientReference(orderDto.getClientReference());
		orderEntity.setUserNumber(orderDto.getUserNumber());
		orderEntity.setEmployeeEmail(orderDto.getEmployeeEmail());
		orderEntity.setSubTotal(orderDto.getSubTotal());
		orderEntity.setIvaTotal(orderDto.getIvaTotal());
		orderEntity.setOrderTotal(orderDto.getOrderTotal());
		orderEntity.setPendingPayment(orderDto.getPendingPayment());
		orderEntity.setDiscountTotal(orderDto.getDiscountTotal());
		orderEntity.setRetentionCode(orderDto.getRetentionCode());
		orderEntity.setOrderType(orderDto.getOrderType());
		orderEntity.setTempMigStatus(orderDto.getTempMigStatus());
		orderEntity.setUpdated(orderDto.getIsUpdated());
		orderEntity.setObservations(orderDto.getObservations());
		orderEntity.setRetentionOrder(orderDto.getIsRetentionOrder());
		orderEntity.setCfdiType(orderDto.getCfdiType());
		orderEntity.setTimeActive(orderDto.getTimeActive());
		orderEntity.setConverted(orderDto.getIsConverted());
		orderEntity.setIdUser(orderDto.getIdUser());
		orderEntity.setInvoiceTop(orderDto.getIsInvoiceTop());
		orderEntity.setInvoiceAmount(orderDto.getInvoiceAmount());

		return orderEntity;
	}

	public List<OrderDto> orderEntityListToOrderDtoList(List<OrderEntity> orderEntityList, List<String> params) {
		List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
		for (OrderEntity orderEntity : orderEntityList) {
			orderDtoList.add(orderEntityToOrderDto(orderEntity, params));
		}
		return orderDtoList;
	}

	public List<OrderEntity> orderDtoListToOrderEntityList(List<OrderDto> orderDtoList) {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		for (OrderDto orderDto : orderDtoList) {
			orderEntityList.add(orderDtoToOrderEntity(orderDto));
		}
		return orderEntityList;
	}
}
