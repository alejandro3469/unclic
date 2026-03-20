package mx.com.endtoend.infrastructure.catalogue.article.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleDivisionDto;
import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleDivisionEntity;

@Component
public class ArticleDivisionConverter {

	public ArticleDivisionDto articleDivisionEntityToArticleDivisionDto(ArticleDivisionEntity articleDivisionEntity) {

		ArticleDivisionDto articleDivisionDto = new ArticleDivisionDto();

		articleDivisionDto.setId(articleDivisionEntity.getId());
		articleDivisionDto.setIsEnable(articleDivisionEntity.isEnable());
		articleDivisionDto.setCode(articleDivisionEntity.getCode());
		articleDivisionDto.setValue(articleDivisionEntity.getValue());

		return articleDivisionDto;
	}

	public ArticleDivisionEntity articleDivisionEntityToArticleDivisionDto(ArticleDivisionDto articleDivisionDto) {

		ArticleDivisionEntity articleDivisionEntity = new ArticleDivisionEntity();

		articleDivisionEntity.setId(articleDivisionDto.getId());
		articleDivisionEntity.setEnable(articleDivisionDto.getIsEnable());
		articleDivisionEntity.setCode(articleDivisionDto.getCode());
		articleDivisionEntity.setValue(articleDivisionDto.getValue());

		return articleDivisionEntity;
	}

	public List<ArticleDivisionDto> articleDivisionEntityListToArticleDivisionDtoList(
			List<ArticleDivisionEntity> articleDivisionEntityList) {

		List<ArticleDivisionDto> articleDivisionDtoList = new ArrayList<ArticleDivisionDto>();

		for (ArticleDivisionEntity articleDivisionEntity : articleDivisionEntityList) {
			articleDivisionDtoList.add(articleDivisionEntityToArticleDivisionDto(articleDivisionEntity));
		}
		return articleDivisionDtoList;
	}

	public List<ArticleDivisionEntity> articleDivisionDtoListToArticleDivisionEntityList(
			List<ArticleDivisionDto> articleDivisionDtoList) {

		List<ArticleDivisionEntity> articleDivisionEntityList = new ArrayList<ArticleDivisionEntity>();

		for (ArticleDivisionDto articleDivisionDto : articleDivisionDtoList) {
			articleDivisionEntityList.add(articleDivisionEntityToArticleDivisionDto(articleDivisionDto));
		}
		return articleDivisionEntityList;
	}

}
