package mx.com.endtoend.infrastructure.orders.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.orders.common.entities.SaleOrderEntity;
import mx.com.endtoend.smart.bussiness.model.orders.dto.SaleOrderDto;

@Component
public class SaleOrderConverter {

	public SaleOrderDto saleOrderEntityToSaleOrderDto(SaleOrderEntity saleOrderEntity) {

		SaleOrderDto saleOrderDto = new SaleOrderDto();

		saleOrderDto.setOrderId(saleOrderEntity.getOrderId());
		saleOrderDto.setOrderCode(saleOrderEntity.getOrderCode());
		saleOrderDto.setOrderType(saleOrderEntity.getOrderType());
		saleOrderDto.setOrderNumber(saleOrderEntity.getOrderNumber());
		saleOrderDto.setBranchCode(saleOrderEntity.getBranchCode());
		saleOrderDto.setCompanyNumber(saleOrderEntity.getCompanyNumber());
		saleOrderDto.setCurrency(saleOrderEntity.getCurrency());
		saleOrderDto.setExchangeRate(saleOrderEntity.getExchangeRate());
		saleOrderDto.setCreationDate(saleOrderEntity.getCreationDate());
		saleOrderDto.setRequestDate(saleOrderEntity.getRequestDate());
		saleOrderDto.setUserNumber(saleOrderEntity.getUserNumber());
		saleOrderDto.setUsername(saleOrderEntity.getUsername());
		saleOrderDto.setClientTax(saleOrderEntity.getClientTax());
		saleOrderDto.setSubTotal(saleOrderEntity.getSubTotal());
		saleOrderDto.setIvaTotal(saleOrderEntity.getIvaTotal());
		saleOrderDto.setOrderTotal(saleOrderEntity.getOrderTotal());

		return saleOrderDto;
	}

	public SaleOrderEntity saleOrderDtoToSaleOrderEntity(SaleOrderDto saleOrderDto) {

		SaleOrderEntity saleOrderEntity = new SaleOrderEntity();

		saleOrderEntity.setOrderId(saleOrderDto.getOrderId());
		saleOrderEntity.setOrderCode(saleOrderDto.getOrderCode());
		saleOrderEntity.setOrderType(saleOrderDto.getOrderType());
		saleOrderEntity.setOrderNumber(saleOrderDto.getOrderNumber());
		saleOrderEntity.setBranchCode(saleOrderDto.getBranchCode());
		saleOrderEntity.setCompanyNumber(saleOrderDto.getCompanyNumber());
		saleOrderEntity.setCurrency(saleOrderDto.getCurrency());
		saleOrderEntity.setExchangeRate(saleOrderDto.getExchangeRate());
		saleOrderEntity.setCreationDate(saleOrderDto.getCreationDate());
		saleOrderEntity.setRequestDate(saleOrderDto.getRequestDate());
		saleOrderEntity.setUserNumber(saleOrderDto.getUserNumber());
		saleOrderEntity.setUsername(saleOrderDto.getUsername());
		saleOrderEntity.setClientTax(saleOrderDto.getClientTax());
		saleOrderEntity.setSubTotal(saleOrderDto.getSubTotal());
		saleOrderEntity.setIvaTotal(saleOrderDto.getIvaTotal());
		saleOrderEntity.setOrderTotal(saleOrderDto.getOrderTotal());

		return saleOrderEntity;
	}

	public List<SaleOrderDto> saleOrderEntityListToSaleOrderDtoList(List<SaleOrderEntity> saleOrderEntityList) {

		List<SaleOrderDto> saleOrderDtoList = new ArrayList<SaleOrderDto>();

		for (SaleOrderEntity saleOrderEntity : saleOrderEntityList) {
			saleOrderDtoList.add(saleOrderEntityToSaleOrderDto(saleOrderEntity));
		}

		return saleOrderDtoList;
	}

	public List<SaleOrderEntity> saleOrderDtoListToSaleOrderEntityList(List<SaleOrderDto> saleOrderDtoList) {

		List<SaleOrderEntity> saleOrderEntityList = new ArrayList<SaleOrderEntity>();

		for (SaleOrderDto saleOrderDto : saleOrderDtoList) {
			saleOrderEntityList.add(saleOrderDtoToSaleOrderEntity(saleOrderDto));
		}

		return saleOrderEntityList;
	}
}
