package mx.com.endtoend.infrastructure.orderConfigurations.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.orderConfigurations.common.entities.OrderConfigurationEntity;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

@Component
public class OrderConfigurationConverter {

	@Autowired
	private SaleTypeConverter saleTypeConverter;

	@Autowired
	private DocumentConverter documentConverter;

	public OrderConfigurationDto orderConfigurationEntityToOrderConfigurationDto(
			OrderConfigurationEntity orderConfigurationEntity) {

		OrderConfigurationDto orderConfigurationDto = new OrderConfigurationDto();

		orderConfigurationDto.setId(orderConfigurationEntity.getId());
		orderConfigurationDto.setLineCodeOne(orderConfigurationEntity.getLineCodeOne());
		orderConfigurationDto.setLineCodeTwo(orderConfigurationEntity.getLineCodeTwo());
		orderConfigurationDto.setRetentionCode(orderConfigurationEntity.getRetentionCode());
		orderConfigurationDto.setIsApplyConversion(orderConfigurationEntity.isApplyConversion());
		orderConfigurationDto.setIsApplyTopInvoice(orderConfigurationEntity.isApplyTopInvoice());
		
		orderConfigurationDto.setIsApplyCreditNote(orderConfigurationEntity.isApplyCreditNote());
		orderConfigurationDto.setCreditNoteCode(orderConfigurationEntity.getCreditNoteCode());
		orderConfigurationDto.setStateOneCreditNote(orderConfigurationEntity.getStateOneCreditNote());
		orderConfigurationDto.setStateTwoCreditNote(orderConfigurationEntity.getStateTwoCreditNote());
		orderConfigurationDto.setStateOneValidCreditNote(orderConfigurationEntity.getStateOneValidCreditNote());
		orderConfigurationDto.setStateTwoValidCreditNote(orderConfigurationEntity.getStateTwoValidCreditNote());

		if (orderConfigurationEntity.getSaleType() != null) {
			orderConfigurationDto
					.setSaleType(saleTypeConverter.saleTypeEntityToSaleTypeDto(orderConfigurationEntity.getSaleType()));
		}

		if (orderConfigurationEntity.getDocuments() != null) {
			orderConfigurationDto.setDocuments(
					documentConverter.documentEntityListToDocumentDtoList(orderConfigurationEntity.getDocuments()));

		}

		return orderConfigurationDto;
	}

	public OrderConfigurationEntity orderConfigurationDtoToOrderConfigurationEntity(
			OrderConfigurationDto orderConfigurationDto) {

		OrderConfigurationEntity orderConfigurationEntity = new OrderConfigurationEntity();

		orderConfigurationEntity.setId(orderConfigurationDto.getId());
		orderConfigurationEntity.setLineCodeOne(orderConfigurationDto.getLineCodeOne());
		orderConfigurationEntity.setLineCodeTwo(orderConfigurationDto.getLineCodeTwo());
		orderConfigurationEntity.setRetentionCode(orderConfigurationDto.getRetentionCode());
		orderConfigurationEntity.setApplyConversion(orderConfigurationDto.getIsApplyConversion());
		orderConfigurationEntity.setApplyTopInvoice(orderConfigurationDto.getIsApplyTopInvoice());
		
		orderConfigurationEntity.setApplyCreditNote(orderConfigurationDto.getIsApplyCreditNote());
		orderConfigurationEntity.setCreditNoteCode(orderConfigurationDto.getCreditNoteCode());
		orderConfigurationEntity.setStateOneCreditNote(orderConfigurationDto.getStateOneCreditNote());
		orderConfigurationEntity.setStateTwoCreditNote(orderConfigurationDto.getStateTwoCreditNote());
		orderConfigurationEntity.setStateOneValidCreditNote(orderConfigurationDto.getStateOneValidCreditNote());
		orderConfigurationEntity.setStateTwoValidCreditNote(orderConfigurationDto.getStateTwoValidCreditNote());

		return orderConfigurationEntity;
	}

	public List<OrderConfigurationDto> orderConfigurationEntityListToOrderConfigurationDtoList(
			List<OrderConfigurationEntity> orderConfigurationEntityList) {

		List<OrderConfigurationDto> orderConfigurationDtoList = new ArrayList<OrderConfigurationDto>();

		for (OrderConfigurationEntity orderConfigurationEntity : orderConfigurationEntityList) {
			orderConfigurationDtoList.add(orderConfigurationEntityToOrderConfigurationDto(orderConfigurationEntity));
		}

		return orderConfigurationDtoList;
	}

	public List<OrderConfigurationEntity> orderConfigurationDtoListToOrderConfigurationEntityList(
			List<OrderConfigurationDto> orderConfigurationDtoList) {

		List<OrderConfigurationEntity> orderConfigurationEntityList = new ArrayList<OrderConfigurationEntity>();

		for (OrderConfigurationDto orderConfigurationDto : orderConfigurationDtoList) {
			orderConfigurationEntityList.add(orderConfigurationDtoToOrderConfigurationEntity(orderConfigurationDto));
		}

		return orderConfigurationEntityList;
	}
}
