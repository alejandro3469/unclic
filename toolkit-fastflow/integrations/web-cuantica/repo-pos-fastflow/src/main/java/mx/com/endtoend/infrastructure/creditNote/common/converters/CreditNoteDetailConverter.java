package mx.com.endtoend.infrastructure.creditNote.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteDetailDto;
import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteDetailEntity;

@Component
public class CreditNoteDetailConverter {

	public CreditNoteDetailEntity creditNoteDetailDtoToCreditNoteDetailEntity(CreditNoteDetailDto creditNoteDetailDto) {

		CreditNoteDetailEntity creditNoteDetailEntity = new CreditNoteDetailEntity();

		creditNoteDetailEntity.setId(creditNoteDetailDto.getId());
		creditNoteDetailEntity.setArticleNumber(creditNoteDetailDto.getArticleNumber());
		creditNoteDetailEntity.setArticleCode(creditNoteDetailDto.getArticleCode());
		creditNoteDetailEntity.setDescriptionOne(creditNoteDetailDto.getDescriptionOne());
		creditNoteDetailEntity.setDescriptionTwo(creditNoteDetailDto.getDescriptionTwo());
		creditNoteDetailEntity.setUnitMeasure(creditNoteDetailDto.getUnitMeasure());
		creditNoteDetailEntity.setFinalUnitPrice(creditNoteDetailDto.getFinalUnitPrice().doubleValue());
		creditNoteDetailEntity.setTaxOne(creditNoteDetailDto.getTaxOne().doubleValue());
		creditNoteDetailEntity.setTaxTwo(creditNoteDetailDto.getTaxTwo().doubleValue());
		creditNoteDetailEntity.setRequestAmount(creditNoteDetailDto.getRequestAmount().doubleValue());
		creditNoteDetailEntity.setCustomArticle(creditNoteDetailDto.getIsCustomArticle());

		return creditNoteDetailEntity;
	}

	public CreditNoteDetailDto creditNoteEntityToCreditNoteDetailDto(CreditNoteDetailEntity creditNoteDetailEntity) {
		CreditNoteDetailDto creditNoteDetailDto = new CreditNoteDetailDto();

		creditNoteDetailDto.setId(creditNoteDetailEntity.getId());
		creditNoteDetailDto.setArticleNumber(creditNoteDetailEntity.getArticleNumber());
		creditNoteDetailDto.setArticleCode(creditNoteDetailEntity.getArticleCode());
		creditNoteDetailDto.setDescriptionOne(creditNoteDetailEntity.getDescriptionOne());
		creditNoteDetailDto.setDescriptionTwo(creditNoteDetailEntity.getDescriptionTwo());
		creditNoteDetailDto.setUnitMeasure(creditNoteDetailEntity.getUnitMeasure());
		creditNoteDetailDto.setFinalUnitPrice(BigDecimal.valueOf(creditNoteDetailEntity.getFinalUnitPrice()));
		creditNoteDetailDto.setTaxOne(BigDecimal.valueOf(creditNoteDetailEntity.getTaxOne()));
		creditNoteDetailDto.setTaxTwo(BigDecimal.valueOf(creditNoteDetailEntity.getTaxTwo()));
		creditNoteDetailDto.setRequestAmount(BigDecimal.valueOf(creditNoteDetailEntity.getRequestAmount()));
		creditNoteDetailDto.setIsCustomArticle(creditNoteDetailEntity.isCustomArticle());

		return creditNoteDetailDto;
	}

	public List<CreditNoteDetailEntity> creditNoteDetailDtoListToCreditNoteDetailEntityList(
			List<CreditNoteDetailDto> creditNoteDetailDtolist) {
		List<CreditNoteDetailEntity> creditNoteDetailEntities = new ArrayList<>();
		for (CreditNoteDetailDto creditNoteDetailDto : creditNoteDetailDtolist) {
			creditNoteDetailEntities.add(creditNoteDetailDtoToCreditNoteDetailEntity(creditNoteDetailDto));
		}
		return creditNoteDetailEntities;
	}

	public List<CreditNoteDetailDto> creditNoteEntityListToCreditNoteDetailDtoList(
			List<CreditNoteDetailEntity> creditNoteDetailEntityList) {
		List<CreditNoteDetailDto> creditNoteDetailDtos = new ArrayList<>();
		for (CreditNoteDetailEntity creditNoteDetailEntity : creditNoteDetailEntityList) {
			creditNoteDetailDtos.add(creditNoteEntityToCreditNoteDetailDto(creditNoteDetailEntity));
		}
		return creditNoteDetailDtos;
	}
}
