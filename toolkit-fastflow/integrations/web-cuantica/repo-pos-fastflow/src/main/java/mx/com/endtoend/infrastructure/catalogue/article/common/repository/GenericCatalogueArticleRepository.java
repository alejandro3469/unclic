package mx.com.endtoend.infrastructure.catalogue.article.common.repository;

import java.util.List;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleBrandDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleCategoryDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleDivisionDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleFamilyDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericCatalogueArticleRepository {

	ResponseModel getBrandByCompanyCode(String idOperation);

	ResponseModel getCategoryByCompanyCode(String idOperation);

	ResponseModel getDivisionByCompanyCode(String idOperation);

	ResponseModel getFamilyByCompanyCode(String idOperation);

	ResponseModel updateCatalogueBrandByCompanyCodeAndList(List<ArticleBrandDto> articleBrandDtoList,
			String idOperation);

	ResponseModel updateCatalogueCategoryByCompanyCodeAndList(List<ArticleCategoryDto> articleCategoryDtoList,
			String idOperation);

	ResponseModel updateCatalogueDivisionByCompanyCodeAndList(List<ArticleDivisionDto> articleDivisionDtoList,
			String idOperation);

	ResponseModel updateCatalogueFamilyByCompanyCodeAndList(List<ArticleFamilyDto> articleFamilyDtoList,
			String idOperation);

}
