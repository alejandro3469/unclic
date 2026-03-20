package mx.com.endtoend.infrastructure.advertising.common.converters;

import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.infrastructure.advertising.common.entities.SaleAdvertisingEntity;
import org.springframework.stereotype.Component;

import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SaleAdvertisingDto;

@Component
public class AdvertisingConverter {

	public SaleAdvertisingEntity saleAdvertisingDtoToSaleAdvertisingEntity(SaleAdvertisingDto saleAdvertisingDto) {

		SaleAdvertisingEntity saleAdvertisingEntity = new SaleAdvertisingEntity();

		saleAdvertisingEntity.setId(saleAdvertisingDto.getId());
		saleAdvertisingEntity.setCreationDate(saleAdvertisingDto.getCreationDate());
		saleAdvertisingEntity.setOrderCode(saleAdvertisingDto.getOrderCode());
		saleAdvertisingEntity.setOrderNumber(saleAdvertisingDto.getOrderNumber());
		saleAdvertisingEntity.setBranchCode(saleAdvertisingDto.getBranchCode());
		saleAdvertisingEntity.setEmployeeId(saleAdvertisingDto.getEmployeeId());
		saleAdvertisingEntity.setClientNumber(saleAdvertisingDto.getClientNumber());
		saleAdvertisingEntity.setMessage(saleAdvertisingDto.getMessage());
		saleAdvertisingEntity.setPricePerWord(saleAdvertisingDto.getProcePerWord());
		saleAdvertisingEntity.setPublishedDay(saleAdvertisingDto.getPublishedDay());
		saleAdvertisingEntity.setArticleNumber(saleAdvertisingDto.getArticleNumber());
		saleAdvertisingEntity.setArticleCode(saleAdvertisingDto.getArticleCode());
		saleAdvertisingEntity.setDescription(saleAdvertisingDto.getDescription());
		saleAdvertisingEntity.setNumberWord(saleAdvertisingDto.getNumberWord());
		saleAdvertisingEntity.setAppliedIva(saleAdvertisingDto.getAppliedIva());
		saleAdvertisingEntity.setIva(saleAdvertisingDto.getIva());
		saleAdvertisingEntity.setSubTotal(saleAdvertisingDto.getSubTotal());
		saleAdvertisingEntity.setAmountTotal(saleAdvertisingDto.getAmountTotal());
		saleAdvertisingEntity.setStatusCode(saleAdvertisingDto.getStatusCode());

		return saleAdvertisingEntity;
	}

	public SaleAdvertisingDto saleAdvertisingEntityToSaleAdvertisingDto(SaleAdvertisingEntity saleAdvertisingEntity) {

		SaleAdvertisingDto saleAdvertisingDto = new SaleAdvertisingDto();

		saleAdvertisingDto.setId(saleAdvertisingEntity.getId());
		saleAdvertisingDto.setCreationDate(saleAdvertisingEntity.getCreationDate());
		saleAdvertisingDto.setOrderCode(saleAdvertisingEntity.getOrderCode());
		saleAdvertisingDto.setOrderNumber(saleAdvertisingEntity.getOrderNumber());
		saleAdvertisingDto.setBranchCode(saleAdvertisingEntity.getBranchCode());
		saleAdvertisingDto.setEmployeeId(saleAdvertisingEntity.getEmployeeId());
		saleAdvertisingDto.setClientNumber(saleAdvertisingEntity.getClientNumber());
		saleAdvertisingDto.setMessage(saleAdvertisingEntity.getMessage());
		saleAdvertisingDto.setProcePerWord(saleAdvertisingEntity.getPricePerWord());
		saleAdvertisingDto.setPublishedDay(saleAdvertisingEntity.getPublishedDay());
		saleAdvertisingDto.setArticleNumber(saleAdvertisingEntity.getArticleNumber());
		saleAdvertisingDto.setArticleCode(saleAdvertisingEntity.getArticleCode());
		saleAdvertisingDto.setDescription(saleAdvertisingEntity.getDescription());
		saleAdvertisingDto.setNumberWord(saleAdvertisingEntity.getNumberWord());
		saleAdvertisingDto.setAppliedIva(saleAdvertisingEntity.getAppliedIva());
		saleAdvertisingDto.setIva(saleAdvertisingEntity.getIva());
		saleAdvertisingDto.setSubTotal(saleAdvertisingEntity.getSubTotal());
		saleAdvertisingDto.setAmountTotal(saleAdvertisingEntity.getAmountTotal());
		saleAdvertisingDto.setStatusCode(saleAdvertisingEntity.getStatusCode());

		return saleAdvertisingDto;
	}

	public List<SaleAdvertisingDto> saleAdvertisingEntityListToSaleAdvertisingDtoList(
			List<SaleAdvertisingEntity> saleAdvertisingEntiyList) {
		List<SaleAdvertisingDto> saleAdvertisingDtoList = new ArrayList<>();
		for (SaleAdvertisingEntity saleAdvertisingEntity : saleAdvertisingEntiyList) {
			saleAdvertisingDtoList.add(saleAdvertisingEntityToSaleAdvertisingDto(saleAdvertisingEntity));
		}
		return saleAdvertisingDtoList;
	}
	
	public List<SaleAdvertisingEntity> saleAdvertisingDtoListToSaleAdvertisingEntityList(
			List<SaleAdvertisingDto> saleAdvertisingDtoList) {
		List<SaleAdvertisingEntity> saleAdvertisingEntityList = new ArrayList<>();
		for (SaleAdvertisingDto saleAdvertisingDto : saleAdvertisingDtoList) {
			saleAdvertisingEntityList.add(saleAdvertisingDtoToSaleAdvertisingEntity(saleAdvertisingDto));
		}
		return saleAdvertisingEntityList;
	}
}
