package mx.com.endtoend.infrastructure.articles.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.infrastructure.articles.common.entities.CustomArticleEntity;

@Component
public class CustomArticleConverter {

	public CustomArticleDto customArticleEntityToCustomArticleDto(CustomArticleEntity customArticleEntity) {

		CustomArticleDto customArticleDto = new CustomArticleDto();

		customArticleDto.setId(customArticleEntity.getId());
		customArticleDto.setName(customArticleEntity.getName());
		customArticleDto.setPrice(customArticleEntity.getPrice());
		customArticleDto.setIsEnable(customArticleEntity.isEnable());
		customArticleDto.setArticleNumber(customArticleEntity.getArticleNumber());
		customArticleDto.setSaleType(customArticleEntity.getSaleType());

		return customArticleDto;
	}

	public CustomArticleEntity customArticleDtoToCustomArticleEntity(CustomArticleDto customArticleDto) {

		CustomArticleEntity customArticleEntity = new CustomArticleEntity();

		customArticleEntity.setId(customArticleDto.getId());
		customArticleEntity.setName(customArticleDto.getName());
		customArticleEntity.setPrice(customArticleDto.getPrice());
		customArticleEntity.setEnable(customArticleDto.getIsEnable());
		customArticleEntity.setArticleNumber(customArticleDto.getArticleNumber());
		customArticleEntity.setSaleType(customArticleDto.getSaleType());

		return customArticleEntity;
	}

	public List<CustomArticleDto> customArticleEntityListToCustomArticleDtoList(
			List<CustomArticleEntity> customArticleEntityList) {
		List<CustomArticleDto> customArticleDtoList = new ArrayList<CustomArticleDto>();
		for (CustomArticleEntity customArticleEntity : customArticleEntityList) {
			customArticleDtoList.add(customArticleEntityToCustomArticleDto(customArticleEntity));
		}
		return customArticleDtoList;
	}

	public List<CustomArticleEntity> customArticleDtoListToCustomArticleEntrityList(
			List<CustomArticleDto> customArticleDtoList) {
		List<CustomArticleEntity> customArticleEntityList = new ArrayList<CustomArticleEntity>();
		for (CustomArticleDto customArticleDto : customArticleDtoList) {
			customArticleEntityList.add(customArticleDtoToCustomArticleEntity(customArticleDto));
		}
		return customArticleEntityList;

	}
}
