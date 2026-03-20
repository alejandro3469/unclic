package mx.com.endtoend.infrastructure.catalogue.article.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.catalogue.article.common.factory.CatalogueArticleRepositoryFactory;
import mx.com.endtoend.infrastructure.catalogue.article.common.repository.GenericCatalogueArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleBrandDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleCategoryDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleDivisionDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleFamilyDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueArticlePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class CatalogueArticleJpaAdapter implements CatalogueArticlePersistencePort {

	@Autowired
	private CatalogueArticleRepositoryFactory catalogueArticleRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueArticleJpaAdapter.class);

	@Override
	public ResponseModel getBrandByCompanyCode(String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getBrandByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));

		GenericCatalogueArticleRepository repository = catalogueArticleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.getBrandByCompanyCode(idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getCategoryByCompanyCode(String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getCategoryByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));

		GenericCatalogueArticleRepository repository = catalogueArticleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.getCategoryByCompanyCode(idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getDivisionByCompanyCode(String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getDivisionByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));

		GenericCatalogueArticleRepository repository = catalogueArticleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.getDivisionByCompanyCode(idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getFamilyByCompanyCode(String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getFamilyByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));

		GenericCatalogueArticleRepository repository = catalogueArticleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.getFamilyByCompanyCode(idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueBrandByCompanyCodeAndList(String companyCode,
			List<ArticleBrandDto> articleBrandDtoList, String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogueBrandByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s, articleBrandDtoList: %d]", idOperation, companyCode,
				articleBrandDtoList.size()));

		GenericCatalogueArticleRepository repository = catalogueArticleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.updateCatalogueBrandByCompanyCodeAndList(articleBrandDtoList,
				idOperation);

		return responseModel;

	}

	@Override
	public ResponseModel updateCatalogueCategoryByCompanyCodeAndList(String companyCode,
			List<ArticleCategoryDto> articleCategoryDtoList, String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogueCategoryByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , articleCategoryDtoList: %d ]", idOperation, companyCode,
				articleCategoryDtoList.size()));

		GenericCatalogueArticleRepository repository = catalogueArticleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.updateCatalogueCategoryByCompanyCodeAndList(articleCategoryDtoList,
				idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueDivisionByCompanyCodeAndList(String companyCode,
			List<ArticleDivisionDto> articleDivisionDtoList, String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogueDivisionByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s, articleDivisionDtoList: %d ]", idOperation, companyCode,
				articleDivisionDtoList.size()));

		GenericCatalogueArticleRepository repository = catalogueArticleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.updateCatalogueDivisionByCompanyCodeAndList(articleDivisionDtoList,
				idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueFamilyByCompanyCodeAndList(String companyCode,
			List<ArticleFamilyDto> articleFamilyDtoList, String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogueFamilyByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , articleFamilyDtoList: %d ]", idOperation, companyCode,
				articleFamilyDtoList.size()));
		GenericCatalogueArticleRepository repository = catalogueArticleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.updateCatalogueFamilyByCompanyCodeAndList(articleFamilyDtoList,
				idOperation);

		return responseModel;
	}

}
