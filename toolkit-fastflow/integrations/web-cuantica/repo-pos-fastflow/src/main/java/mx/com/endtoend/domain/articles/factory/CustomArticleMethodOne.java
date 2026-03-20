package mx.com.endtoend.domain.articles.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.domain.articles.factory.validations.CustomArticleValidation;
import mx.com.endtoend.domain.articles.ports.spi.CustomArticlePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author ddcasas
 *
 */

public class CustomArticleMethodOne implements CustomArticleInterface {

	private final Logger LOG = LoggerFactory.getLogger(CustomArticleMethodOne.class);

	private CustomArticleValidation customArticleValidation = new CustomArticleValidation();

	@Override
	public ResponseModel createCustomArticle(CustomArticlePersistencePort customArticlePersistencePort,
			CustomArticleDto customArticleDto, String companyCode, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT createCustomArticle()", idOperation));

		String validations = customArticleValidation.validOperativeDataOnCreate(customArticlePersistencePort,
				customArticleDto, companyCode, branchCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s ", idOperation, validations));
			throw new ValidationError(validations);
		}

		ResponseModel responseFromPersistencePort = customArticlePersistencePort.createCustomArticle(customArticleDto,
				companyCode, branchCode, idOperation);

		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel updateCustomArticle(CustomArticlePersistencePort customArticlePersistencePort,
			CustomArticleDto customArticleDto, String companyCode, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT updateCustomArticle", idOperation));

		String validations = customArticleValidation.validOperativeDataOnUpdate(customArticlePersistencePort,
				customArticleDto, companyCode, branchCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s ", idOperation, validations));
			throw new ValidationError(validations);
		}

		ResponseModel responseFromPersistencePort = customArticlePersistencePort.updateCustomArticle(customArticleDto,
				companyCode, branchCode, idOperation);

		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel enabledCustomArticleById(CustomArticlePersistencePort customArticlePersistencePort,
			boolean enable, Long id, String companyCode, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT enabledCustomArticleById()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s companyCode: %s branchCode: %s]", idOperation, id.toString(),
				companyCode, branchCode));

		CustomArticleDto customArticleDto = (CustomArticleDto) customArticlePersistencePort
				.enableById(id, enable, companyCode, branchCode, idOperation).getData();

		if (customArticleDto == null) {
			LOG.error(String.format("%s ERROR IN OPERATION: enabledCustomArticleById", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s CHANGE STATUS TO CUSTOM ARTICLE OK", idOperation));
		return new ResponseModel(customArticleDto);
	}

	@Override
	public ResponseModel getCustomArticleListByEnable(CustomArticlePersistencePort customArticlePersistencePort,
			boolean enable, String companyCode, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT getCustomArticleListByEnable()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s branchCode: %s]", idOperation, companyCode, branchCode));

		ResponseModel responseFromPersistencePort = new ResponseModel();

		responseFromPersistencePort = customArticlePersistencePort.findAllByEnable(enable, companyCode, branchCode,
				idOperation);

		if (responseFromPersistencePort.getData() == null) {
			LOG.error(String.format("%s ERROR IN OPERATION: getCustomArticleListByEnable", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s RESPONSE FROM PERSISTENCE PORT %d ", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel getCustomArticleById(CustomArticlePersistencePort customArticlePersistencePort, Long id,
			String companyCode, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT getCustomArticleById()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s companyCode: %s branchCode: %s]", idOperation, id.toString(),
				companyCode, branchCode));

		ResponseModel responseFromPersistencePort = new ResponseModel();

		responseFromPersistencePort = customArticlePersistencePort.findById(id, companyCode, branchCode, idOperation);

		if (responseFromPersistencePort.getData() == null) {
			LOG.error(String.format("%s ERROR IN OPERATION: getCustomArticleById", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s RESPONSE FROM PERSISTENCE PORT %d ", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel findAllActiveBySaleType(CustomArticlePersistencePort customArticlePersistencePort,
			String companyCode, String saleType, String idOperation) {
		LOG.info(String.format("%s INIT findAllActiveBySaleType()", idOperation));

		ResponseModel responseFromPersistencePort = customArticlePersistencePort
				.findAllActiveBySaleTypeAndCompanyCode(saleType, companyCode, idOperation);

		if (responseFromPersistencePort.getData() == null) {
			LOG.error(String.format("%s ERROR IN OPERATION: getCustomArticleListByEnable", idOperation));
			throw new GlobalError();
		}

		return responseFromPersistencePort;
	}

}
