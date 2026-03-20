package mx.com.endtoend.infrastructure.orders.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.orders.common.entities.OrderHistoryEntity;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;

@Component
public class OrderHistoryConverter {

	public OrderHistoryDto orderHistoryEntityToOrderHistoryDto(OrderHistoryEntity orderHistoryEntity) {

		OrderHistoryDto orderHistoryDto = new OrderHistoryDto();

		orderHistoryDto.setId(orderHistoryEntity.getId());
		orderHistoryDto.setBranchCode(orderHistoryEntity.getBranchCode());
		orderHistoryDto.setOrderType(orderHistoryEntity.getOrderType());
		orderHistoryDto.setOrderNumber(orderHistoryEntity.getOrderNumber());
		orderHistoryDto.setModificationDate(orderHistoryEntity.getModificationDate());
		orderHistoryDto.setAction(orderHistoryEntity.getAction());
		orderHistoryDto.setUsername(orderHistoryEntity.getUsername());
		orderHistoryDto.setUserNumber(orderHistoryEntity.getUserNumber());
		orderHistoryDto.setParams(orderHistoryEntity.getParams());
		orderHistoryDto.setIsRetentionOrder(orderHistoryEntity.isRetentionOrder());

		return orderHistoryDto;
	}

	public OrderHistoryEntity orderHistoryDtoToOrderHistoryEntity(OrderHistoryDto orderHistoryDto) {

		OrderHistoryEntity orderHistoryEntity = new OrderHistoryEntity();

		orderHistoryEntity.setId(orderHistoryDto.getId());
		orderHistoryEntity.setBranchCode(orderHistoryDto.getBranchCode());
		orderHistoryEntity.setOrderType(orderHistoryDto.getOrderType());
		orderHistoryEntity.setOrderNumber(orderHistoryDto.getOrderNumber());
		orderHistoryEntity.setModificationDate(orderHistoryDto.getModificationDate());
		orderHistoryEntity.setAction(orderHistoryDto.getAction());
		orderHistoryEntity.setUsername(orderHistoryDto.getUsername());
		orderHistoryEntity.setUserNumber(orderHistoryDto.getUserNumber());
		orderHistoryEntity.setParams(orderHistoryDto.getParams());
		orderHistoryEntity.setRetentionOrder(orderHistoryDto.getIsRetentionOrder());

		return orderHistoryEntity;
	}

	public List<OrderHistoryEntity> orderHistoryDtoListToOrderHistoryEntityList(
			List<OrderHistoryDto> orderHistoryDtoList) {
		List<OrderHistoryEntity> orderHistoryEntityList = new ArrayList<OrderHistoryEntity>();
		for (OrderHistoryDto orderHistoryDto : orderHistoryDtoList) {
			orderHistoryEntityList.add(orderHistoryDtoToOrderHistoryEntity(orderHistoryDto));
		}
		return orderHistoryEntityList;
	}

	public List<OrderHistoryDto> orderHistoryEntityListToOrderHistoryDtoList(
			List<OrderHistoryEntity> orderHistoryEntityList) {
		List<OrderHistoryDto> orderHistoryDtoList = new ArrayList<OrderHistoryDto>();
		for (OrderHistoryEntity orderHistoryEntity : orderHistoryEntityList) {
			orderHistoryDtoList.add(orderHistoryEntityToOrderHistoryDto(orderHistoryEntity));
		}
		return orderHistoryDtoList;
	}
}
