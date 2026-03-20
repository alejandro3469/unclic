package mx.com.endtoend.infrastructure.orders.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.orders.common.entities.SaleOrderDetailEntity;
import mx.com.endtoend.smart.bussiness.model.orders.dto.SaleOrderDetailDto;

@Component
public class SaleOrderDetailConverter {

	public SaleOrderDetailDto saleOrderDetailEntityToSaleOrderDetailDto(SaleOrderDetailEntity saleOrderDetailEntity) {
		
		SaleOrderDetailDto saleOrderDetailDto = new SaleOrderDetailDto();
		
		saleOrderDetailDto.setId(saleOrderDetailEntity.getId());
		saleOrderDetailDto.setLineNumber(saleOrderDetailEntity.getLineNumber());
		saleOrderDetailDto.setLineType(saleOrderDetailEntity.getLineType());
		saleOrderDetailDto.setWarehouseCode(saleOrderDetailEntity.getWarehouseCode());
		saleOrderDetailDto.setArticleNumber(saleOrderDetailEntity.getArticleNumber());
		saleOrderDetailDto.setArticleDescription(saleOrderDetailEntity.getArticleDescription());
		saleOrderDetailDto.setUnitMeasurement(saleOrderDetailEntity.getUnitMeasurement());
		saleOrderDetailDto.setRequestAmount(saleOrderDetailEntity.getRequestAmount());
		saleOrderDetailDto.setArticlePrice(saleOrderDetailEntity.getArticlePrice());
		saleOrderDetailDto.setSubTotal(saleOrderDetailEntity.getSubTotal());
		saleOrderDetailDto.setCurrency(saleOrderDetailEntity.getCurrency());
		saleOrderDetailDto.setExchangeRate(saleOrderDetailEntity.getExchangeRate());
		saleOrderDetailDto.setLineCodeOne(saleOrderDetailEntity.getLineCodeOne());
		saleOrderDetailDto.setLineCodeTwo(saleOrderDetailEntity.getLineCodeTwo());
		saleOrderDetailDto.setWeight(saleOrderDetailEntity.getWeight());
		saleOrderDetailDto.setWeightFactor(saleOrderDetailEntity.getWeightFactor());
		
		return saleOrderDetailDto;
	}
	
	
	public SaleOrderDetailEntity saleOrderDetailDtoToSaleOrderDetailEntity(SaleOrderDetailDto saleOrderDetailDto) {
		
		SaleOrderDetailEntity saleOrderDetailEntity = new SaleOrderDetailEntity();
		
		saleOrderDetailEntity.setId(saleOrderDetailDto.getId());
		saleOrderDetailEntity.setLineNumber(saleOrderDetailDto.getLineNumber());
		saleOrderDetailEntity.setLineType(saleOrderDetailDto.getLineType());
		saleOrderDetailEntity.setWarehouseCode(saleOrderDetailDto.getWarehouseCode());
		saleOrderDetailEntity.setArticleNumber(saleOrderDetailDto.getArticleNumber());
		saleOrderDetailEntity.setArticleDescription(saleOrderDetailDto.getArticleDescription());
		saleOrderDetailEntity.setUnitMeasurement(saleOrderDetailDto.getUnitMeasurement());
		saleOrderDetailEntity.setRequestAmount(saleOrderDetailDto.getRequestAmount());
		saleOrderDetailEntity.setArticlePrice(saleOrderDetailDto.getArticlePrice());
		saleOrderDetailEntity.setSubTotal(saleOrderDetailDto.getSubTotal());
		saleOrderDetailEntity.setCurrency(saleOrderDetailDto.getCurrency());
		saleOrderDetailEntity.setExchangeRate(saleOrderDetailDto.getExchangeRate());
		saleOrderDetailEntity.setLineCodeOne(saleOrderDetailDto.getLineCodeOne());
		saleOrderDetailEntity.setLineCodeTwo(saleOrderDetailDto.getLineCodeTwo());
		saleOrderDetailEntity.setWeight(saleOrderDetailDto.getWeight());
		saleOrderDetailEntity.setWeightFactor(saleOrderDetailDto.getWeightFactor());
		
		return saleOrderDetailEntity;
		
	}
	
	public List<SaleOrderDetailDto> saleOrderDetailEntityListToSaleOrderDetailDtoList(List<SaleOrderDetailEntity> saleOrderDetailEntityList){
		
		List<SaleOrderDetailDto> saleOrderDetailDtoLits = new ArrayList<SaleOrderDetailDto>();
		
		for (SaleOrderDetailEntity saleOrderDetailEntity : saleOrderDetailEntityList) {
			saleOrderDetailDtoLits.add(saleOrderDetailEntityToSaleOrderDetailDto(saleOrderDetailEntity));
		}
		
		return saleOrderDetailDtoLits;
	}
	
	public List<SaleOrderDetailEntity> saleOrderDetailDtoListToSaleOrderDetailEntityList(List<SaleOrderDetailDto> saleOrderDetailDtoList){
		
		List<SaleOrderDetailEntity> saleOrderDetailEntityList = new ArrayList<SaleOrderDetailEntity>();
		
		for (SaleOrderDetailDto saleOrderDetailDto : saleOrderDetailDtoList) {
			saleOrderDetailEntityList.add(saleOrderDetailDtoToSaleOrderDetailEntity(saleOrderDetailDto));
		}
		
		return saleOrderDetailEntityList;
	}
}
