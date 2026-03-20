package mx.com.endtoend.infrastructure.orders.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.orders.common.entities.OrderDetailEntity;

@Component
public class OrderDetailConverter {

	public OrderDetailDto orderDetailEntityToOrderDetailDto(OrderDetailEntity orderDetailEntity) {

		OrderDetailDto orderDetailDto = new OrderDetailDto();

		orderDetailDto.setId(orderDetailEntity.getId());
		orderDetailDto.setLineNumber(orderDetailEntity.getLineNumber());
		orderDetailDto.setLineType(orderDetailEntity.getLineType());
		orderDetailDto.setStorageType(orderDetailEntity.getStorageType());
		orderDetailDto.setWarehouseCode(orderDetailEntity.getWarehouseCode());
		orderDetailDto.setArticleNumber(orderDetailEntity.getArticleNumber());
		orderDetailDto.setSupplierNumber(orderDetailEntity.getSupplierNumber());
		orderDetailDto.setDescriptionOne(orderDetailEntity.getDescriptionOne());
		orderDetailDto.setDescriptionTwo(orderDetailEntity.getDescriptionTwo());
		orderDetailDto.setUnitMeasurement(orderDetailEntity.getUnitMeasurement());
		orderDetailDto.setPrimaryUnitMeasure(orderDetailEntity.getPrimaryUnitMeasure());
		orderDetailDto.setRequestAmount(orderDetailEntity.getRequestAmount());
		orderDetailDto.setModifiedUnitPrice(orderDetailEntity.getModifiedUnitPrice());
		orderDetailDto.setUnitPrice(orderDetailEntity.getUnitPrice());
		orderDetailDto.setFinalUnitPrice(orderDetailEntity.getFinalUnitPrice());
		orderDetailDto.setSubTotal(orderDetailEntity.getSubTotal());
		orderDetailDto.setUnitPriceTax(orderDetailEntity.getUnitPriceTax());
		orderDetailDto.setSubTotalTax(orderDetailEntity.getSubTotalTax());
		orderDetailDto.setArticleTax(orderDetailEntity.getArticleTax());
		orderDetailDto.setLineCodeOne(orderDetailEntity.getLineCodeOne());
		orderDetailDto.setLineCodeTwo(orderDetailEntity.getLineCodeTwo());
		orderDetailDto.setDiscountSeller(orderDetailEntity.getDiscountSeller());
		orderDetailDto.setFinalDiscountSeller(
				orderDetailEntity.getFinalDiscountSeller() == null ? orderDetailEntity.getDiscountSeller()
						: orderDetailEntity.getFinalDiscountSeller());
		orderDetailDto.setUerNumberSeller(orderDetailEntity.getUerNumberSeller());
		orderDetailDto.setArticleCode(orderDetailEntity.getArticleCode());
		orderDetailDto.setPriceType(orderDetailEntity.getPriceType());
		orderDetailDto.setIsRetentionArticle(orderDetailEntity.isRetentionArticle());
		orderDetailDto.setRetentionCode(orderDetailEntity.getRetentionCode());
		orderDetailDto.setAlternateDescription(orderDetailEntity.getAlternateDescription());
		orderDetailDto.setApplyTax(orderDetailEntity.getApplyTax());
		orderDetailDto.setTaxValueOne(orderDetailEntity.getTaxValueOne());
		orderDetailDto.setTaxValueTwo(orderDetailEntity.getTaxValueTwo());
		orderDetailDto.setTaxValueThree(orderDetailEntity.getTaxValueThree());
		orderDetailDto.setTaxValueFour(orderDetailEntity.getTaxValueFour());
		orderDetailDto.setTaxValueFive(orderDetailEntity.getTaxValueFive());
		orderDetailDto.setTaxValueByDefault(orderDetailEntity.getTaxValueByDefault());
		orderDetailDto.setConversionFactor(orderDetailEntity.getConversionFactor());
		orderDetailDto.setIsCustumArticle(orderDetailEntity.isCustumArticle());

		return orderDetailDto;
	}

	public OrderDetailEntity orderDetailDtoToOrderDetailEntity(OrderDetailDto orderDetailDto) {

		OrderDetailEntity orderDetailEntity = new OrderDetailEntity();

		orderDetailEntity.setId(orderDetailDto.getId());
		orderDetailEntity.setLineNumber(orderDetailDto.getLineNumber());
		orderDetailEntity.setLineType(orderDetailDto.getLineType());
		orderDetailEntity.setStorageType(orderDetailDto.getStorageType());
		orderDetailEntity.setWarehouseCode(orderDetailDto.getWarehouseCode());
		orderDetailEntity.setArticleNumber(orderDetailDto.getArticleNumber());
		orderDetailEntity.setSupplierNumber(orderDetailDto.getSupplierNumber());
		orderDetailEntity.setDescriptionOne(orderDetailDto.getDescriptionOne());
		orderDetailEntity.setDescriptionTwo(orderDetailDto.getDescriptionTwo());
		orderDetailEntity.setUnitMeasurement(orderDetailDto.getUnitMeasurement());
		orderDetailEntity.setPrimaryUnitMeasure(orderDetailDto.getPrimaryUnitMeasure());
		orderDetailEntity.setRequestAmount(orderDetailDto.getRequestAmount() != null ? orderDetailDto.getRequestAmount() : BigDecimal.ZERO);
		orderDetailEntity.setModifiedUnitPrice(orderDetailDto.getModifiedUnitPrice() != null ? orderDetailDto.getModifiedUnitPrice() : BigDecimal.ZERO);
		orderDetailEntity.setUnitPrice(orderDetailDto.getUnitPrice() != null ? orderDetailDto.getUnitPrice() : BigDecimal.ZERO);
		orderDetailEntity.setFinalUnitPrice(orderDetailDto.getFinalUnitPrice() != null ? orderDetailDto.getFinalUnitPrice() : BigDecimal.ZERO);
		orderDetailEntity.setSubTotal(orderDetailDto.getSubTotal() != null ? orderDetailDto.getSubTotal() : BigDecimal.ZERO);
		orderDetailEntity.setUnitPriceTax(orderDetailDto.getUnitPriceTax() != null ? orderDetailDto.getUnitPriceTax() : BigDecimal.ZERO);
		orderDetailEntity.setSubTotalTax(orderDetailDto.getSubTotalTax() != null ? orderDetailDto.getSubTotalTax() : BigDecimal.ZERO);
		orderDetailEntity.setArticleTax(orderDetailDto.getArticleTax() != null ? orderDetailDto.getArticleTax() : BigDecimal.ZERO);
		orderDetailEntity.setLineCodeOne(orderDetailDto.getLineCodeOne());
		orderDetailEntity.setLineCodeTwo(orderDetailDto.getLineCodeTwo());
		orderDetailEntity.setDiscountSeller(orderDetailDto.getDiscountSeller() != null ? orderDetailDto.getDiscountSeller() : BigDecimal.ZERO);
		orderDetailEntity.setFinalDiscountSeller(orderDetailDto.getFinalDiscountSeller() != null ? orderDetailDto.getFinalDiscountSeller() : BigDecimal.ZERO);
		orderDetailEntity.setUerNumberSeller(orderDetailDto.getUerNumberSeller());
		orderDetailEntity.setArticleCode(orderDetailDto.getArticleCode());
		orderDetailEntity.setPriceType(orderDetailDto.getPriceType());
		orderDetailEntity.setRetentionArticle(orderDetailDto.getIsRetentionArticle());
		orderDetailEntity.setRetentionCode(orderDetailDto.getRetentionCode());
		orderDetailEntity.setAlternateDescription(orderDetailDto.getAlternateDescription());
		orderDetailEntity.setApplyTax(orderDetailDto.getApplyTax());
		orderDetailEntity.setTaxValueOne(orderDetailDto.getTaxValueOne() != null ? orderDetailDto.getTaxValueOne() : BigDecimal.ZERO);
		orderDetailEntity.setTaxValueTwo(orderDetailDto.getTaxValueTwo() != null ? orderDetailDto.getTaxValueTwo() : BigDecimal.ZERO);
		orderDetailEntity.setTaxValueThree(orderDetailDto.getTaxValueThree() != null ? orderDetailDto.getTaxValueThree() : BigDecimal.ZERO);
		orderDetailEntity.setTaxValueFour(orderDetailDto.getTaxValueFour() != null ? orderDetailDto.getTaxValueFour() : BigDecimal.ZERO);
		orderDetailEntity.setTaxValueFive(orderDetailDto.getTaxValueFive() != null ? orderDetailDto.getTaxValueFive() : BigDecimal.ZERO);
		orderDetailEntity.setTaxValueByDefault(orderDetailDto.getTaxValueByDefault() != null ? orderDetailDto.getTaxValueByDefault() : BigDecimal.ZERO);
		orderDetailEntity.setConversionFactor(orderDetailDto.getConversionFactor() != null ? orderDetailDto.getConversionFactor() : BigDecimal.ZERO);
		orderDetailEntity.setCustumArticle(orderDetailDto.getIsCustumArticle());

		return orderDetailEntity;
	}

	public List<OrderDetailDto> orderDetailEntityToOrderDetailDetoList(List<OrderDetailEntity> orderDetailEntityList) {
		List<OrderDetailDto> orderDetailDtoList = new ArrayList<OrderDetailDto>();
		for (OrderDetailEntity orderDetailEntity : orderDetailEntityList) {
			orderDetailDtoList.add(orderDetailEntityToOrderDetailDto(orderDetailEntity));
		}
		return orderDetailDtoList;
	}

	public List<OrderDetailEntity> orderDetailDtoListToOrderDetailEntityList(List<OrderDetailDto> orderDetailDtoList) {
		List<OrderDetailEntity> orderDetailEntityList = new ArrayList<OrderDetailEntity>();
		for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
			orderDetailEntityList.add(orderDetailDtoToOrderDetailEntity(orderDetailDto));
		}
		return orderDetailEntityList;
	}
}
