package mx.com.endtoend.infrastructure.catalogue.article.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleCategoryDto;
import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleCategoryEntity;

@Component
public class ArticleCategoryConverter {

	public ArticleCategoryDto articleCategoryEntityToArticleCategoryDto(ArticleCategoryEntity articleCategoryEntity) {

		ArticleCategoryDto articleCategoryDto = new ArticleCategoryDto();

		articleCategoryDto.setId(articleCategoryEntity.getId());
		articleCategoryDto.setIsEnable(articleCategoryEntity.isEnable());
		articleCategoryDto.setCode(articleCategoryEntity.getCode());
		articleCategoryDto.setValue(articleCategoryEntity.getValue());

		return articleCategoryDto;
	}

	public ArticleCategoryEntity articleCategoryDtoToArticleCategoryEntity(ArticleCategoryDto articleCategoryDto) {

		ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();

		articleCategoryEntity.setId(articleCategoryDto.getId());
		articleCategoryEntity.setEnable(articleCategoryDto.getIsEnable());
		articleCategoryEntity.setCode(articleCategoryDto.getCode());
		articleCategoryEntity.setValue(articleCategoryDto.getValue());

		return articleCategoryEntity;
	}

	public List<ArticleCategoryDto> articleCategoryEntityListToArticleCategoryDtoList(
			List<ArticleCategoryEntity> articleCategoryEntityList) {

		List<ArticleCategoryDto> articleCategoryDtoList = new ArrayList<ArticleCategoryDto>();

		for (ArticleCategoryEntity articleCategoryEntity : articleCategoryEntityList) {
			articleCategoryDtoList.add(articleCategoryEntityToArticleCategoryDto(articleCategoryEntity));
		}

		return articleCategoryDtoList;
	}
	
	public List<ArticleCategoryEntity> articleCategoryDtoListToArticleCategoryEntityList(
			List<ArticleCategoryDto> articleCategoryDtoList) {

		List<ArticleCategoryEntity> articleCategoryEntityList = new ArrayList<ArticleCategoryEntity>();

		for (ArticleCategoryDto articleCategoryDto : articleCategoryDtoList) {
			articleCategoryEntityList.add(articleCategoryDtoToArticleCategoryEntity(articleCategoryDto));
		}

		return articleCategoryEntityList;
	}

}
