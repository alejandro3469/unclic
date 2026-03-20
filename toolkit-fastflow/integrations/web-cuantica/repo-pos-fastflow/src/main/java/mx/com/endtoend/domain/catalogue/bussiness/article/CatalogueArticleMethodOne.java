package mx.com.endtoend.domain.catalogue.bussiness.article;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.dto.CatalogueUpdateResult;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleBrandDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleCategoryDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleDivisionDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleFamilyDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueArticlePersistencePort;
import mx.com.endtoend.domain.commons.constants.CatalogueArticleTypeEnum;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueArticleMethodOne implements CatalogueArticleInterface {

	private CatalogueJdeServicePort catalogueOracleServicePort;

	public CatalogueArticleMethodOne(CatalogueJdeServicePort catalogueOracleServicePort) {
		this.catalogueOracleServicePort = catalogueOracleServicePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueArticleMethodOne.class);

	@Override
	public ResponseModel getBrandByCompanyCode(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getBrandByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueArticlePersistencePort.getBrandByCompanyCode(companyCode, idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getCategoryByCompanyCode(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCategoryByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueArticlePersistencePort.getCategoryByCompanyCode(companyCode,
				idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getDivisionByCompanyCode(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getDivisionByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueArticlePersistencePort.getDivisionByCompanyCode(companyCode,
				idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getFamilyByCompanyCode(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getFamilyByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueArticlePersistencePort.getFamilyByCompanyCode(companyCode, idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueByCompanyCodeAndCatalogueType(
			CatalogueArticlePersistencePort catalogueArticlePersistencePort, String companyCode, String catalogueType,
			String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogueByCompanyCodeAndCatalogueType() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , catalogueType: %s ]", idOperation, companyCode,
				catalogueType));

		boolean validCataloguetype = CatalogueArticleTypeEnum.isValid(catalogueType);

		if (!validCataloguetype) {
			LOG.warn(String.format("%s INVALID ARTICLE-TYPE", idOperation));
			throw new ValidationError("WRONG CATALOGUE TYPE SELECTED");
		}

		ResponseModel responseModel = new ResponseModel();

		CatalogueArticleTypeEnum catalogueTypeValue = CatalogueArticleTypeEnum.valueOf(catalogueType);

		switch (catalogueTypeValue) {

		case BRAND:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE BRAND", idOperation));
			responseModel = this.updateCatalogueBrand(catalogueArticlePersistencePort, companyCode, idOperation);
			break;

		case CATEGORY:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE CATEGORY", idOperation));
			responseModel = this.updateCatalogueCategory(catalogueArticlePersistencePort, companyCode, idOperation);
			break;

		case DIVISION:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE DIVISION", idOperation));
			responseModel = this.updateCatalogueDivision(catalogueArticlePersistencePort, companyCode, idOperation);
			break;

		case FAMILY:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE FAMILY", idOperation));
			responseModel = this.updateCatalogueFamily(catalogueArticlePersistencePort, companyCode, idOperation);
			break;

		default:
			LOG.error(String.format("%s ERROR IN UPDATE CATALOGUE-ARTICLE", idOperation));
			throw new GlobalError();
		}

		return responseModel;
	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueBrand(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<ArticleBrandDto> updateArticleBrandList = new ArrayList<ArticleBrandDto>();
		List<ArticleBrandDto> disableArticleBrandList = new ArrayList<ArticleBrandDto>();
		List<ArticleBrandDto> newRecordArticleBrandList = new ArrayList<ArticleBrandDto>();

		List<ArticleBrandDto> localArticleBrandList = (List<ArticleBrandDto>) catalogueArticlePersistencePort
				.getBrandByCompanyCode(companyCode, idOperation).getData();

		List<ArticleBrandDto> remoteArticleBrandList = (List<ArticleBrandDto>) catalogueOracleServicePort
				.getArticleBrandByCompanyCode(companyCode, idOperation).getData();

		if (localArticleBrandList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE ARTICLE-BRAND EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueArticlePersistencePort
					.updateCatalogueBrandByCompanyCodeAndList(companyCode, remoteArticleBrandList, idOperation)
					.getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueArticleTypeEnum.BRAND.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (ArticleBrandDto localArticleBrand : localArticleBrandList) {
				for (ArticleBrandDto remoteArticleBrand : remoteArticleBrandList) {
					if (localArticleBrand.getCode().trim().equals(remoteArticleBrand.getCode().trim())) {
						exists = true;
						localArticleBrand.setValue(remoteArticleBrand.getValue());
						updateArticleBrandList.add(localArticleBrand);
					}
				}
				if (!exists) {
					localArticleBrand.setIsEnable(false);
					disableArticleBrandList.add(localArticleBrand);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTENAL SOURCE", idOperation));
			for (ArticleBrandDto remoteArticleBrand : remoteArticleBrandList) {
				exists = false;
				for (ArticleBrandDto localArticleBrand : localArticleBrandList) {
					if (remoteArticleBrand.getCode().trim().equals(localArticleBrand.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordArticleBrandList.add(remoteArticleBrand);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableArticleBrandList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort
							.updateCatalogueBrandByCompanyCodeAndList(companyCode, disableArticleBrandList, idOperation)
							.getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateArticleBrandList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort
							.updateCatalogueBrandByCompanyCodeAndList(companyCode, updateArticleBrandList, idOperation)
							.getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordArticleBrandList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort
							.updateCatalogueBrandByCompanyCodeAndList(companyCode, remoteArticleBrandList, idOperation)
							.getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueArticleTypeEnum.BRAND.toString());

			return new ResponseModel(catalogueUpdateResult);
		}

	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueCategory(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<ArticleCategoryDto> updateArticleCategoryList = new ArrayList<ArticleCategoryDto>();
		List<ArticleCategoryDto> disableArticleCategoryList = new ArrayList<ArticleCategoryDto>();
		List<ArticleCategoryDto> newRecordArticleCategoryList = new ArrayList<ArticleCategoryDto>();

		List<ArticleCategoryDto> localArticleCategoryList = (List<ArticleCategoryDto>) catalogueArticlePersistencePort
				.getCategoryByCompanyCode(companyCode, idOperation).getData();

		List<ArticleCategoryDto> remoteArticleCategoryList = (List<ArticleCategoryDto>) catalogueOracleServicePort
				.getArticleCategoryByCompanyCode(companyCode, idOperation).getData();

		if (localArticleCategoryList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE ARTICLE-CATEGORY EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueArticlePersistencePort
					.updateCatalogueCategoryByCompanyCodeAndList(companyCode, remoteArticleCategoryList, idOperation)
					.getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueArticleTypeEnum.CATEGORY.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (ArticleCategoryDto localArticleCategory : localArticleCategoryList) {
				for (ArticleCategoryDto remoteArticleCategory : remoteArticleCategoryList) {
					if (localArticleCategory.getCode().trim().equals(remoteArticleCategory.getCode().trim())) {
						exists = true;
						localArticleCategory.setValue(remoteArticleCategory.getValue());
						updateArticleCategoryList.add(localArticleCategory);
					}
				}
				if (!exists) {
					localArticleCategory.setIsEnable(false);
					disableArticleCategoryList.add(localArticleCategory);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTENAL SOURCE", idOperation));
			for (ArticleCategoryDto remoteArticleCategory : remoteArticleCategoryList) {
				exists = false;
				for (ArticleCategoryDto localArticleCategory : localArticleCategoryList) {
					if (remoteArticleCategory.getCode().trim().equals(localArticleCategory.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordArticleCategoryList.add(remoteArticleCategory);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableArticleCategoryList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueCategoryByCompanyCodeAndList(companyCode,
							disableArticleCategoryList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateArticleCategoryList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueCategoryByCompanyCodeAndList(companyCode,
							updateArticleCategoryList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordArticleCategoryList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueCategoryByCompanyCodeAndList(companyCode,
							remoteArticleCategoryList, idOperation).getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueArticleTypeEnum.CATEGORY.toString());

			return new ResponseModel(catalogueUpdateResult);
		}

	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueDivision(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<ArticleDivisionDto> updateArticleDivisionList = new ArrayList<ArticleDivisionDto>();
		List<ArticleDivisionDto> disableArticleDivisionList = new ArrayList<ArticleDivisionDto>();
		List<ArticleDivisionDto> newRecordArticleDivisionList = new ArrayList<ArticleDivisionDto>();

		List<ArticleDivisionDto> localArticleDivisionList = (List<ArticleDivisionDto>) catalogueArticlePersistencePort
				.getDivisionByCompanyCode(companyCode, idOperation).getData();

		List<ArticleDivisionDto> remoteArticleDivisionList = (List<ArticleDivisionDto>) catalogueOracleServicePort
				.getArticleDivisionByCompanyCode(companyCode, idOperation).getData();

		if (localArticleDivisionList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE ARTICLE-DIVISION EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueArticlePersistencePort
					.updateCatalogueDivisionByCompanyCodeAndList(companyCode, remoteArticleDivisionList, idOperation)
					.getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueArticleTypeEnum.DIVISION.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (ArticleDivisionDto localArticleDivision : localArticleDivisionList) {
				for (ArticleDivisionDto remoteArticleDivision : remoteArticleDivisionList) {
					if (localArticleDivision.getCode().trim().equals(remoteArticleDivision.getCode().trim())) {
						exists = true;
						localArticleDivision.setValue(remoteArticleDivision.getValue());
						updateArticleDivisionList.add(localArticleDivision);
					}
				}
				if (!exists) {
					localArticleDivision.setIsEnable(false);
					disableArticleDivisionList.add(localArticleDivision);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTENAL SOURCE", idOperation));
			for (ArticleDivisionDto remoteArticleDivision : remoteArticleDivisionList) {
				exists = false;
				for (ArticleDivisionDto localArticleDivision : localArticleDivisionList) {
					if (remoteArticleDivision.getCode().trim().equals(localArticleDivision.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordArticleDivisionList.add(remoteArticleDivision);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableArticleDivisionList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueDivisionByCompanyCodeAndList(companyCode,
							disableArticleDivisionList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateArticleDivisionList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueDivisionByCompanyCodeAndList(companyCode,
							updateArticleDivisionList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordArticleDivisionList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueDivisionByCompanyCodeAndList(companyCode,
							remoteArticleDivisionList, idOperation).getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueArticleTypeEnum.DIVISION.toString());

			return new ResponseModel(catalogueUpdateResult);
		}
	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueFamily(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<ArticleFamilyDto> updateArticleFamilyList = new ArrayList<ArticleFamilyDto>();
		List<ArticleFamilyDto> disableArticleFamilyList = new ArrayList<ArticleFamilyDto>();
		List<ArticleFamilyDto> newRecordArticleFamilyList = new ArrayList<ArticleFamilyDto>();

		List<ArticleFamilyDto> localArticleFamilyList = (List<ArticleFamilyDto>) catalogueArticlePersistencePort
				.getFamilyByCompanyCode(companyCode, idOperation).getData();

		List<ArticleFamilyDto> remoteArticleFamilyList = (List<ArticleFamilyDto>) catalogueOracleServicePort
				.getArticleFamilyByCompanyCode(companyCode, idOperation).getData();

		if (localArticleFamilyList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE ARTICLE-FAMILY EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueArticlePersistencePort
					.updateCatalogueFamilyByCompanyCodeAndList(companyCode, remoteArticleFamilyList, idOperation)
					.getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueArticleTypeEnum.FAMILY.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (ArticleFamilyDto localArticleFamily : localArticleFamilyList) {
				for (ArticleFamilyDto remoteArticleFamily : remoteArticleFamilyList) {
					if (localArticleFamily.getCode().trim().equals(remoteArticleFamily.getCode().trim())) {
						exists = true;
						localArticleFamily.setValue(remoteArticleFamily.getValue());
						updateArticleFamilyList.add(localArticleFamily);
					}
				}
				if (!exists) {
					localArticleFamily.setIsEnable(false);
					disableArticleFamilyList.add(localArticleFamily);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTENAL SOURCE", idOperation));
			for (ArticleFamilyDto remoteArticleFamily : remoteArticleFamilyList) {
				exists = false;
				for (ArticleFamilyDto localArticleFamily : localArticleFamilyList) {
					if (remoteArticleFamily.getCode().trim().equals(localArticleFamily.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordArticleFamilyList.add(remoteArticleFamily);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableArticleFamilyList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueFamilyByCompanyCodeAndList(companyCode,
							disableArticleFamilyList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateArticleFamilyList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueFamilyByCompanyCodeAndList(companyCode,
							updateArticleFamilyList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordArticleFamilyList.size() == 0 ? 0
					: (int) catalogueArticlePersistencePort.updateCatalogueFamilyByCompanyCodeAndList(companyCode,
							remoteArticleFamilyList, idOperation).getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueArticleTypeEnum.FAMILY.toString());

			return new ResponseModel(catalogueUpdateResult);
		}
	}
}
