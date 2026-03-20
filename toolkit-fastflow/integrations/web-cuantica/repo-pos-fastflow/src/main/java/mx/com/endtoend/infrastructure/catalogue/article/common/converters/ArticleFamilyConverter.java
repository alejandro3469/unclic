package mx.com.endtoend.infrastructure.catalogue.article.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleFamilyDto;
import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleFamilyEntity;

@Component
public class ArticleFamilyConverter {

	public ArticleFamilyDto articleFamilyEntityToArticleFamilyDto(ArticleFamilyEntity articleFamilyEntity) {

		ArticleFamilyDto articleFamilyDto = new ArticleFamilyDto();

		articleFamilyDto.setId(articleFamilyEntity.getId());
		articleFamilyDto.setIsEnable(articleFamilyEntity.isEnable());
		articleFamilyDto.setCode(articleFamilyEntity.getCode());
		articleFamilyDto.setValue(articleFamilyEntity.getValue());

		return articleFamilyDto;
	}

	public ArticleFamilyEntity articleFamilyEntityToArticleFamilyDto(ArticleFamilyDto articleFamilyDto) {

		ArticleFamilyEntity articleFamilyEntity = new ArticleFamilyEntity();

		articleFamilyEntity.setId(articleFamilyDto.getId());
		articleFamilyEntity.setEnable(articleFamilyDto.getIsEnable());
		articleFamilyEntity.setCode(articleFamilyDto.getCode());
		articleFamilyEntity.setValue(articleFamilyDto.getValue());

		return articleFamilyEntity;
	}

	public List<ArticleFamilyDto> articleFamilyEntityListToArticleFamilyDtoList(
			List<ArticleFamilyEntity> articleFamilyEntityList) {

		List<ArticleFamilyDto> articleFamilyDtoList = new ArrayList<ArticleFamilyDto>();

		for (ArticleFamilyEntity articleFamilyEntity : articleFamilyEntityList) {
			articleFamilyDtoList.add(articleFamilyEntityToArticleFamilyDto(articleFamilyEntity));
		}

		return articleFamilyDtoList;
	}

	public List<ArticleFamilyEntity> articleFamilyDtoListToArticleFamilyEntityList(
			List<ArticleFamilyDto> articleFamilyDtoList) {

		List<ArticleFamilyEntity> articleFamilyEntityList = new ArrayList<ArticleFamilyEntity>();

		for (ArticleFamilyDto articleFamilyDto : articleFamilyDtoList) {
			articleFamilyEntityList.add(articleFamilyEntityToArticleFamilyDto(articleFamilyDto));
		}

		return articleFamilyEntityList;
	}

}
