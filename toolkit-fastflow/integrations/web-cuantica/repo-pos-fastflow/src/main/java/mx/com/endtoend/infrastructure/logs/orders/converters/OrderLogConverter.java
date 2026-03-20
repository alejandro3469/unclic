package mx.com.endtoend.infrastructure.logs.orders.converters;

import java.util.List;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.logs.orders.dto.OrderSummaryLogDto;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailPreviousStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderLogEntity;
import mx.com.endtoend.infrastructure.logs.orders.entities.OrderPreviousStateEntity;
import mx.com.endtoend.smart.bussiness.model.orders.dto.AddressDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

@Component
public class OrderLogConverter {

	public OrderLogEntity orderSummaryToEntiy(OrderSummaryLogDto orderSummaryLogDto) {

		OrderLogEntity orderLogEntity = new OrderLogEntity();

		orderLogEntity.setUser(orderSummaryLogDto.getUser());
		orderLogEntity.setUpdatedDate(orderSummaryLogDto.getUpdatedDate());
		orderLogEntity.setOrderNumber(orderSummaryLogDto.getOrderNumber());
		orderLogEntity.setOrderCode(orderSummaryLogDto.getOrderCode());

		return orderLogEntity;
	}

	public OrderPreviousStateEntity orderSavedToEntity(OrderDto orderSaved, Long orderSummaryId) {

		OrderPreviousStateEntity orderPreviousStateEntity = new OrderPreviousStateEntity();

		orderPreviousStateEntity.setOrderSummaryId(orderSummaryId);
		orderPreviousStateEntity.setOrderNumber(orderSaved.getOrderNumber());
		orderPreviousStateEntity.setBatchFolio(orderSaved.getBatchFolio());
		orderPreviousStateEntity.setOrderCode(orderSaved.getOrderCode());
		orderPreviousStateEntity.setBranchCode(orderSaved.getBranchCode());
		orderPreviousStateEntity.setCompanyNumber(orderSaved.getCompanyNumber());
		orderPreviousStateEntity.setCurrency(orderSaved.getCurrency());
		orderPreviousStateEntity.setExchangeRate(orderSaved.getExchangeRate().doubleValue());
		orderPreviousStateEntity.setCreationDate(orderSaved.getCreationDate());
		orderPreviousStateEntity.setRequestDate(orderSaved.getRequestDate());
		orderPreviousStateEntity.setValidityDate(orderSaved.getValidityDate());
		orderPreviousStateEntity
				.setClientSummary(orderSaved.getClient().getBusinessName() + orderSaved.getClient().getNoClient());
		orderPreviousStateEntity.setClientTax(orderSaved.getClientTax().doubleValue());
		orderPreviousStateEntity.setClientReference(orderSaved.getClientReference());
		orderPreviousStateEntity.setUserNumber(orderSaved.getUserNumber());
		orderPreviousStateEntity.setIdUser(orderSaved.getIdUser());
		orderPreviousStateEntity.setEmployeeEmail(orderSaved.getEmployeeEmail());
		orderPreviousStateEntity.setSubTotal(orderSaved.getSubTotal().doubleValue());
		orderPreviousStateEntity.setIvaTotal(orderSaved.getIvaTotal().doubleValue());
		orderPreviousStateEntity.setOrderTotal(orderSaved.getOrderTotal().doubleValue());
		orderPreviousStateEntity.setPendingPayment(orderSaved.getPendingPayment().doubleValue());
		orderPreviousStateEntity.setDiscountTotal(orderSaved.getDiscountTotal().doubleValue());
		orderPreviousStateEntity
				.setStatus(orderSaved.getStatus().getCode() + "-" + orderSaved.getStatus().getDescription());
		orderPreviousStateEntity.setRetentionCode(orderSaved.getRetentionCode());
		orderPreviousStateEntity.setOrderType(orderSaved.getOrderType());
		orderPreviousStateEntity.setTempMigStatus(orderSaved.getTempMigStatus());
		orderPreviousStateEntity.setIsUpdated(orderSaved.getIsUpdated());
		orderPreviousStateEntity.setObservations(orderSaved.getObservations());
		orderPreviousStateEntity.setIsRetentionOrder(orderSaved.getIsRetentionOrder());
		orderPreviousStateEntity.setCfdiType(orderSaved.getCfdiType());
		orderPreviousStateEntity.setTimeActive(orderSaved.getTimeActive());
		orderPreviousStateEntity.setIsConverted(orderSaved.getIsConverted());
		orderPreviousStateEntity.setInvoiceTop(orderSaved.getIsInvoiceTop());
		orderPreviousStateEntity.setInvoiceAmount(orderSaved.getInvoiceAmount().doubleValue());

		List<AddressDto> addressList = orderSaved.getAddresses();
		for (AddressDto addressDto : addressList) {
			if (addressDto.getAddressType().contains("F")) {
				orderPreviousStateEntity.setFiscalAddresse(addressDto.toString());
			}
			if (addressDto.getAddressType().contains("E")) {
				orderPreviousStateEntity.setShipingAddresse(addressDto.toString());
			}
		}

		orderPreviousStateEntity.setTaxes(orderSaved.getTaxes().toString());

		return orderPreviousStateEntity;
	}

	public OrderDetailPreviousStateEntity orderDetailSavedToEntity(OrderDetailDto orderDetailDto, Long orderPrevId) {

		OrderDetailPreviousStateEntity orderDetailPreviousStateEntity = new OrderDetailPreviousStateEntity();

		orderDetailPreviousStateEntity.setOrderPrevStateId(orderPrevId);
		orderDetailPreviousStateEntity.setLineNumber(orderDetailDto.getLineNumber());
		orderDetailPreviousStateEntity.setLineCodeOne(orderDetailDto.getLineCodeOne());
		orderDetailPreviousStateEntity.setLineType(orderDetailDto.getLineType());
		orderDetailPreviousStateEntity.setStorageType(orderDetailDto.getStorageType());
		orderDetailPreviousStateEntity.setWarehouseCode(orderDetailDto.getWarehouseCode());
		orderDetailPreviousStateEntity.setArticleNumber(orderDetailDto.getArticleNumber());
		orderDetailPreviousStateEntity.setSupplierNumber(orderDetailDto.getSupplierNumber());
		orderDetailPreviousStateEntity.setDescriptionOne(orderDetailDto.getDescriptionOne());
		orderDetailPreviousStateEntity.setDescriptionTwo(orderDetailDto.getDescriptionTwo());
		orderDetailPreviousStateEntity.setUnitMeasurement(orderDetailDto.getUnitMeasurement());
		orderDetailPreviousStateEntity.setPrimaryUnitMeasure(orderDetailDto.getPrimaryUnitMeasure());
		orderDetailPreviousStateEntity.setRequestAmount(orderDetailDto.getRequestAmount());
		orderDetailPreviousStateEntity.setModifiedUnitPrice(orderDetailDto.getModifiedUnitPrice());
		orderDetailPreviousStateEntity.setUnitPrice(orderDetailDto.getUnitPrice());
		orderDetailPreviousStateEntity.setFinalUnitPrice(orderDetailDto.getFinalUnitPrice());
		orderDetailPreviousStateEntity.setSubTotal(orderDetailDto.getSubTotal());
		orderDetailPreviousStateEntity.setUnitPriceTax(orderDetailDto.getUnitPriceTax());
		orderDetailPreviousStateEntity.setSubTotalTax(orderDetailDto.getSubTotalTax());
		orderDetailPreviousStateEntity.setArticleTax(orderDetailDto.getArticleTax());
		orderDetailPreviousStateEntity.setLineCodeOne(orderDetailDto.getLineCodeOne());
		orderDetailPreviousStateEntity.setLineCodeTwo(orderDetailDto.getLineCodeTwo());
		orderDetailPreviousStateEntity.setDiscountSeller(orderDetailDto.getDiscountSeller());
		orderDetailPreviousStateEntity.setUerNumberSeller(orderDetailDto.getUerNumberSeller());
		orderDetailPreviousStateEntity.setArticleCode(orderDetailDto.getArticleCode());
		orderDetailPreviousStateEntity.setPriceType(orderDetailDto.getPriceType());
		orderDetailPreviousStateEntity.setIsRetentionArticle(orderDetailDto.getIsRetentionArticle());
		orderDetailPreviousStateEntity.setRetentionCode(orderDetailDto.getRetentionCode());
		orderDetailPreviousStateEntity.setAlternateDescription(orderDetailDto.getAlternateDescription());
		orderDetailPreviousStateEntity.setApplyTax(orderDetailDto.getApplyTax());
		orderDetailPreviousStateEntity.setTaxValueOne(orderDetailDto.getTaxValueOne());
		orderDetailPreviousStateEntity.setTaxValueTwo(orderDetailDto.getTaxValueTwo());
		orderDetailPreviousStateEntity.setTaxValueThree(orderDetailDto.getTaxValueThree());
		orderDetailPreviousStateEntity.setTaxValueFour(orderDetailDto.getTaxValueFour());
		orderDetailPreviousStateEntity.setTaxValueFive(orderDetailDto.getTaxValueFive());
		orderDetailPreviousStateEntity.setTaxValueByDefault(orderDetailDto.getTaxValueByDefault());
		orderDetailPreviousStateEntity.setConversionFactor(orderDetailDto.getConversionFactor());
		orderDetailPreviousStateEntity.setIsCustumArticle(orderDetailDto.getIsCustumArticle());

		return orderDetailPreviousStateEntity;
	}

	public OrderFinalStateEntity orderUpdatedToEntity(OrderDto orderUpdated, Long orderSummaryId) {

		OrderFinalStateEntity orderFinalStateEntity = new OrderFinalStateEntity();

		orderFinalStateEntity.setOrderSummaryId(orderSummaryId);
		orderFinalStateEntity.setOrderNumber(orderUpdated.getOrderNumber());
		orderFinalStateEntity.setBatchFolio(orderUpdated.getBatchFolio());
		orderFinalStateEntity.setOrderCode(orderUpdated.getOrderCode());
		orderFinalStateEntity.setBranchCode(orderUpdated.getBranchCode());
		orderFinalStateEntity.setCompanyNumber(orderUpdated.getCompanyNumber());
		orderFinalStateEntity.setCurrency(orderUpdated.getCurrency());
		orderFinalStateEntity.setExchangeRate(orderUpdated.getExchangeRate());
		orderFinalStateEntity.setCreationDate(orderUpdated.getCreationDate());
		orderFinalStateEntity.setRequestDate(orderUpdated.getRequestDate());
		orderFinalStateEntity.setValidityDate(orderUpdated.getValidityDate());
		orderFinalStateEntity
				.setClientSummary(orderUpdated.getClient().getBusinessName() + orderUpdated.getClient().getNoClient());
		orderFinalStateEntity.setClientTax(orderUpdated.getClientTax());
		orderFinalStateEntity.setClientReference(orderUpdated.getClientReference());
		orderFinalStateEntity.setUserNumber(orderUpdated.getUserNumber());
		orderFinalStateEntity.setIdUser(orderUpdated.getIdUser());
		orderFinalStateEntity.setEmployeeEmail(orderUpdated.getEmployeeEmail());
		orderFinalStateEntity.setSubTotal(orderUpdated.getSubTotal());
		orderFinalStateEntity.setIvaTotal(orderUpdated.getIvaTotal());
		orderFinalStateEntity.setOrderTotal(orderUpdated.getOrderTotal());
		orderFinalStateEntity.setPendingPayment(orderUpdated.getPendingPayment());
		orderFinalStateEntity.setDiscountTotal(orderUpdated.getDiscountTotal());
		orderFinalStateEntity
				.setStatus(orderUpdated.getStatus().getCode() + "-" + orderUpdated.getStatus().getDescription());
		orderFinalStateEntity.setRetentionCode(orderUpdated.getRetentionCode());
		orderFinalStateEntity.setOrderType(orderUpdated.getOrderType());
		orderFinalStateEntity.setTempMigStatus(orderUpdated.getTempMigStatus());
		orderFinalStateEntity.setIsUpdated(orderUpdated.getIsUpdated());
		orderFinalStateEntity.setObservations(orderUpdated.getObservations());
		orderFinalStateEntity.setIsRetentionOrder(orderUpdated.getIsRetentionOrder());
		orderFinalStateEntity.setCfdiType(orderUpdated.getCfdiType());
		orderFinalStateEntity.setTimeActive(orderUpdated.getTimeActive());
		orderFinalStateEntity.setIsConverted(orderUpdated.getIsConverted());
		orderFinalStateEntity.setInvoiceTop(orderUpdated.getIsInvoiceTop());
		orderFinalStateEntity.setInvoiceAmount(orderUpdated.getInvoiceAmount());
		List<AddressDto> addressList = orderUpdated.getAddresses();
		for (AddressDto addressDto : addressList) {
			if (addressDto.getAddressType().contains("F")) {
				orderFinalStateEntity.setFiscalAddresse(addressDto.toString());
			}
			if (addressDto.getAddressType().contains("E")) {
				orderFinalStateEntity.setShipingAddresse(addressDto.toString());
			}
		}
		orderFinalStateEntity.setTaxes(orderUpdated.getTaxes().toString());

		return orderFinalStateEntity;
	}

	public OrderDetailFinalStateEntity orderDetailUpdatedToEntity(OrderDetailDto orderDetailDto, Long orderFinalId) {

		OrderDetailFinalStateEntity orderDetailFinalStateEntity = new OrderDetailFinalStateEntity();

		orderDetailFinalStateEntity.setOrderFinalStateId(orderFinalId);
		orderDetailFinalStateEntity.setLineNumber(orderDetailDto.getLineNumber());
		orderDetailFinalStateEntity.setLineCodeOne(orderDetailDto.getLineCodeOne());
		orderDetailFinalStateEntity.setLineType(orderDetailDto.getLineType());
		orderDetailFinalStateEntity.setStorageType(orderDetailDto.getStorageType());
		orderDetailFinalStateEntity.setWarehouseCode(orderDetailDto.getWarehouseCode());
		orderDetailFinalStateEntity.setArticleNumber(orderDetailDto.getArticleNumber());
		orderDetailFinalStateEntity.setSupplierNumber(orderDetailDto.getSupplierNumber());
		orderDetailFinalStateEntity.setDescriptionOne(orderDetailDto.getDescriptionOne());
		orderDetailFinalStateEntity.setDescriptionTwo(orderDetailDto.getDescriptionTwo());
		orderDetailFinalStateEntity.setUnitMeasurement(orderDetailDto.getUnitMeasurement());
		orderDetailFinalStateEntity.setPrimaryUnitMeasure(orderDetailDto.getPrimaryUnitMeasure());
		orderDetailFinalStateEntity.setRequestAmount(orderDetailDto.getRequestAmount());
		orderDetailFinalStateEntity.setModifiedUnitPrice(orderDetailDto.getModifiedUnitPrice());
		orderDetailFinalStateEntity.setUnitPrice(orderDetailDto.getUnitPrice());
		orderDetailFinalStateEntity.setFinalUnitPrice(orderDetailDto.getFinalUnitPrice());
		orderDetailFinalStateEntity.setSubTotal(orderDetailDto.getSubTotal());
		orderDetailFinalStateEntity.setUnitPriceTax(orderDetailDto.getUnitPriceTax());
		orderDetailFinalStateEntity.setSubTotalTax(orderDetailDto.getSubTotalTax());
		orderDetailFinalStateEntity.setArticleTax(orderDetailDto.getArticleTax());
		orderDetailFinalStateEntity.setLineCodeOne(orderDetailDto.getLineCodeOne());
		orderDetailFinalStateEntity.setLineCodeTwo(orderDetailDto.getLineCodeTwo());
		orderDetailFinalStateEntity.setDiscountSeller(orderDetailDto.getDiscountSeller());
		orderDetailFinalStateEntity.setUerNumberSeller(orderDetailDto.getUerNumberSeller());
		orderDetailFinalStateEntity.setArticleCode(orderDetailDto.getArticleCode());
		orderDetailFinalStateEntity.setPriceType(orderDetailDto.getPriceType());
		orderDetailFinalStateEntity.setIsRetentionArticle(orderDetailDto.getIsRetentionArticle());
		orderDetailFinalStateEntity.setRetentionCode(orderDetailDto.getRetentionCode());
		orderDetailFinalStateEntity.setAlternateDescription(orderDetailDto.getAlternateDescription());
		orderDetailFinalStateEntity.setApplyTax(orderDetailDto.getApplyTax());
		orderDetailFinalStateEntity.setTaxValueOne(orderDetailDto.getTaxValueOne());
		orderDetailFinalStateEntity.setTaxValueTwo(orderDetailDto.getTaxValueTwo());
		orderDetailFinalStateEntity.setTaxValueThree(orderDetailDto.getTaxValueThree());
		orderDetailFinalStateEntity.setTaxValueFour(orderDetailDto.getTaxValueFour());
		orderDetailFinalStateEntity.setTaxValueFive(orderDetailDto.getTaxValueFive());
		orderDetailFinalStateEntity.setTaxValueByDefault(orderDetailDto.getTaxValueByDefault());
		orderDetailFinalStateEntity.setConversionFactor(orderDetailDto.getConversionFactor());
		orderDetailFinalStateEntity.setIsCustumArticle(orderDetailDto.getIsCustumArticle());

		return orderDetailFinalStateEntity;
	}

}
