package mx.com.endtoend.infrastructure.catalogue.article.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleBrandDto;
import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleBrandEntity;

@Component
public class ArticleBrandConverter {

	public ArticleBrandDto articleBrandEntotyToArticleBrandDto(ArticleBrandEntity articleBrandEntity) {

		ArticleBrandDto articleBrandDto = new ArticleBrandDto();

		articleBrandDto.setId(articleBrandEntity.getId());
		articleBrandDto.setIsEnable(articleBrandEntity.isEnable());
		articleBrandDto.setCode(articleBrandEntity.getCode());
		articleBrandDto.setValue(articleBrandEntity.getValue());

		return articleBrandDto;
	}

	public ArticleBrandEntity articleBrandDtoToArticleBrandEntity(ArticleBrandDto articleBrandDto) {

		ArticleBrandEntity articleBrandEntity = new ArticleBrandEntity();

		articleBrandEntity.setId(articleBrandDto.getId());
		articleBrandEntity.setEnable(articleBrandDto.getIsEnable());
		articleBrandEntity.setCode(articleBrandDto.getCode());
		articleBrandEntity.setValue(articleBrandDto.getValue());

		return articleBrandEntity;

	}

	public List<ArticleBrandDto> articleBrandEntityListToArticleBrandDtoList(
			List<ArticleBrandEntity> articleBrandEntityList) {

		List<ArticleBrandDto> articleBrandDtoList = new ArrayList<ArticleBrandDto>();
		for (ArticleBrandEntity articleBrandEntity : articleBrandEntityList) {
			articleBrandDtoList.add(articleBrandEntotyToArticleBrandDto(articleBrandEntity));
		}
		
		return articleBrandDtoList;
	}

	public List<ArticleBrandEntity> articleBrandDtoListToArticleBrandEntityList(
			List<ArticleBrandDto> articleBrandDtoList) {

		List<ArticleBrandEntity> articleBrandEntityList = new ArrayList<ArticleBrandEntity>();
		for (ArticleBrandDto articleBrandDto : articleBrandDtoList) {
			articleBrandEntityList.add(articleBrandDtoToArticleBrandEntity(articleBrandDto));
		}
		
		return articleBrandEntityList;
	}

}
