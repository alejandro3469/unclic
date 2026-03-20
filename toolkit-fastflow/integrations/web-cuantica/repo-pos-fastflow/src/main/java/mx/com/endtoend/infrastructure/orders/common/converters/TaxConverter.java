package mx.com.endtoend.infrastructure.orders.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.orders.dto.TaxDto;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.orders.common.entities.TaxEntity;

@Component
public class TaxConverter {

	public TaxDto taxEntityToTaxDto(TaxEntity taxEntity) {

		TaxDto taxDto = new TaxDto();

		taxDto.setId(taxEntity.getId());
		taxDto.setTaxValue(taxEntity.getTaxValue());
		taxDto.setValue(BigDecimal.valueOf(taxEntity.getValue()));

		return taxDto;
	}

	public TaxEntity taxDtoToTaxEntity(TaxDto taxDto) {

		TaxEntity taxEntity = new TaxEntity();

		taxEntity.setId(taxDto.getId());
		taxEntity.setTaxValue(taxDto.getTaxValue());
		taxEntity.setValue(taxDto.getValue().doubleValue());

		return taxEntity;
	}

	public List<TaxDto> taxEntityListToTaxDtoList(List<TaxEntity> taxEntityList) {
		List<TaxDto> taxDtoList = new ArrayList<TaxDto>();
		for (TaxEntity taxEntity : taxEntityList) {
			taxDtoList.add(taxEntityToTaxDto(taxEntity));
		}
		return taxDtoList;
	}

	public List<TaxEntity> taxDtoListToTaxEntityList(List<TaxDto> taxDtoList) {
		List<TaxEntity> taxEntityList = new ArrayList<TaxEntity>();
		for (TaxDto taxDto : taxDtoList) {
			taxEntityList.add(taxDtoToTaxEntity(taxDto));
		}
		return taxEntityList;
	}
}
