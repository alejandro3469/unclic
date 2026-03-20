package mx.com.endtoend.infrastructure.services.jde.catalogue.demo.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleBrandDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleCategoryDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleDivisionDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleFamilyDto;
import mx.com.endtoend.infrastructure.services.jde.catalogue.demo.common.entities.F0005;

@Component
public class CatalogueArticleFDemoConverter {

	public ArticleBrandDto f0005ToArticleBrandDto(F0005 f0005) {
		ArticleBrandDto articleBrandDto = new ArticleBrandDto();
		articleBrandDto.setId(null);
		articleBrandDto.setIsEnable(true);
		articleBrandDto.setCode(f0005.getId().getDrky());
		articleBrandDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return articleBrandDto;
	}

	public List<ArticleBrandDto> f005ListToArticleBrandDtoList(List<F0005> f0005List) {
		List<ArticleBrandDto> articleBrandDtoList = new ArrayList<ArticleBrandDto>();
		for (F0005 f0005 : f0005List) {
			articleBrandDtoList.add(f0005ToArticleBrandDto(f0005));
		}
		return articleBrandDtoList;
	}

	public ArticleCategoryDto f0005ToArticleCategoryDto(F0005 f0005) {
		ArticleCategoryDto articleCategoryDto = new ArticleCategoryDto();
		articleCategoryDto.setId(null);
		articleCategoryDto.setIsEnable(true);
		articleCategoryDto.setCode(f0005.getId().getDrky());
		articleCategoryDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return articleCategoryDto;
	}

	public List<ArticleCategoryDto> f005ListToArticleCategoryDtoList(List<F0005> f0005List) {
		List<ArticleCategoryDto> articleCategoryDtoList = new ArrayList<ArticleCategoryDto>();
		for (F0005 f0005 : f0005List) {
			articleCategoryDtoList.add(f0005ToArticleCategoryDto(f0005));
		}
		return articleCategoryDtoList;
	}

	public ArticleFamilyDto f0005ToArticleFamilyDto(F0005 f0005) {
		ArticleFamilyDto articleFamilyDto = new ArticleFamilyDto();
		articleFamilyDto.setId(null);
		articleFamilyDto.setIsEnable(true);
		articleFamilyDto.setCode(f0005.getId().getDrky());
		articleFamilyDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return articleFamilyDto;
	}

	public List<ArticleFamilyDto> f005ListToArticleFamilyDtoList(List<F0005> f0005List) {
		List<ArticleFamilyDto> articleFamilyDtoList = new ArrayList<ArticleFamilyDto>();
		for (F0005 f0005 : f0005List) {
			articleFamilyDtoList.add(f0005ToArticleFamilyDto(f0005));
		}
		return articleFamilyDtoList;
	}

	public ArticleDivisionDto f0005ToArticleDivisionDto(F0005 f0005) {
		ArticleDivisionDto articleDivisionDto = new ArticleDivisionDto();
		articleDivisionDto.setId(null);
		articleDivisionDto.setIsEnable(true);
		articleDivisionDto.setCode(f0005.getId().getDrky());
		articleDivisionDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return articleDivisionDto;
	}

	public List<ArticleDivisionDto> f005ListToArticleDivisionDtoList(List<F0005> f0005List) {
		List<ArticleDivisionDto> articleDivisionDtoList = new ArrayList<ArticleDivisionDto>();
		for (F0005 f0005 : f0005List) {
			articleDivisionDtoList.add(f0005ToArticleDivisionDto(f0005));
		}
		return articleDivisionDtoList;
	}

}
