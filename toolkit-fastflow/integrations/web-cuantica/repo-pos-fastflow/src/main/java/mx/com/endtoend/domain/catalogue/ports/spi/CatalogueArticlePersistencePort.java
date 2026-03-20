package mx.com.endtoend.domain.catalogue.ports.spi;

import java.util.List;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleBrandDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleCategoryDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleDivisionDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleFamilyDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueArticlePersistencePort {

	ResponseModel getBrandByCompanyCode(String companyCode, String idOperation);

	ResponseModel getCategoryByCompanyCode(String companyCode, String idOperation);

	ResponseModel getDivisionByCompanyCode(String companyCode, String idOperation);

	ResponseModel getFamilyByCompanyCode(String companyCode, String idOperation);

	ResponseModel updateCatalogueBrandByCompanyCodeAndList(String companyCode,
			List<ArticleBrandDto> articleBrandDtoList, String idOperation);

	ResponseModel updateCatalogueCategoryByCompanyCodeAndList(String companyCode,
			List<ArticleCategoryDto> articleCategoryDtoList, String idOperation);

	ResponseModel updateCatalogueDivisionByCompanyCodeAndList(String companyCode,
			List<ArticleDivisionDto> articleDivisionDtoList, String idOperation);

	ResponseModel updateCatalogueFamilyByCompanyCodeAndList(String companyCode,
			List<ArticleFamilyDto> articleFamilyDtoList, String idOperation);

}
