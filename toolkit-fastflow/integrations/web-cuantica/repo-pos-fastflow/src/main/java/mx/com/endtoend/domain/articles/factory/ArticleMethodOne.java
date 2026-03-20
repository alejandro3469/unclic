package mx.com.endtoend.domain.articles.factory;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.articles.dto.ArticleConvertionFactor;
import mx.com.endtoend.domain.articles.dto.ArticleDto;
import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.domain.articles.ports.spi.ArticlePersistencePort;
import mx.com.endtoend.domain.articles.services.ArticleGenericValidations;
import mx.com.endtoend.domain.debug.ports.api.DebugServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase que implemeta la lógica de negocio del método uno del módulo de
 * artículos.
 * 
 * @author ddcasas
 *
 */

public class ArticleMethodOne implements ArticleInterface {

	private DebugServicePort debugServicePort;
	private static String module = "ARTICLES";

	public ArticleMethodOne(DebugServicePort debugServicePort) {
		this.debugServicePort = debugServicePort;
	}

	private final Logger LOG = LoggerFactory.getLogger(ArticleMethodOne.class);

	ArticleGenericValidations articleGenericValidations = new ArticleGenericValidations();

	/**
	 * Método que recupera la lista de artículos por medio de un conjunto de
	 * filtros.
	 * 
	 * @param articlePersistencePort       puerto de comunicación para la capa de
	 *                                     infraestructura
	 * @param genericSerchParamsArticleDto objeto con los filtros de búsqueda de
	 *                                     artículos
	 * @param companyCode                  código de compañia
	 * @param idOperation                  traza de identificador de operacioón
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel findArticlesByParamsAndCompanyCode(ArticlePersistencePort articlePersistencePort,
			GenericSerchParamsArticleDto genericSerchParamsArticleDto, String companyCode, String branchCode,
			String idOperation, boolean debug) {

		LOG.info(String.format("%s INIT findArticlesByParamsAndCompanyCode() ", idOperation));
		LOG.info(String.format(
				"%s PARAMS: [articlePersistencePort,  genericSerchParamsArticleDto: %s , companyCode: %s ]",
				idOperation, genericSerchParamsArticleDto.toString(), companyCode));

		boolean validationOk = true;

		debugServicePort.saveLog(module, companyCode, "D-START VALIDATION PARAMS", idOperation, debug);

		LOG.info(String.format("%s START VALIDATION PARAMS", idOperation));
		validationOk = articleGenericValidations.validateParamsFilterToMethodOne(genericSerchParamsArticleDto);

		LOG.info(String.format("%s VALIDATION RESULT validationOk: %b ", idOperation, validationOk));

		if (!validationOk) {
			LOG.warn(String.format("%s BAD VALIDATIONS IN ArticleMethodOnde", idOperation));
			throw new ValidationError("BAD PARAMS");
		}

		debugServicePort.saveLog(module, companyCode, "D-START SEARCH ARTICLES", idOperation, debug);

		ResponseModel responseFromPersistencePort = articlePersistencePort.findArticlesByParamnsAndCompanyCode(
				genericSerchParamsArticleDto, companyCode, branchCode, idOperation, debug);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		debugServicePort.saveLog(module, companyCode, "D-RETURN DATA", idOperation, debug);
		return responseFromPersistencePort;
	}

	/**
	 * Método que recupera artículos por código de barras escaneado y almacén
	 * seleccionado.
	 * 
	 * @param articlePersistencePort       puerto de comunicación para la capa de
	 *                                     infraestructura
	 * @param genericSerchParamsArticleDto objeto con los filtros de búsqueda de
	 *                                     artículos
	 * @param companyCode                  código de compañia
	 * @param idOperation                  traza de identificador de operacioón
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel findArticleByBarcodeAndCompanyCode(ArticlePersistencePort articlePersistencePort,
			GenericSerchParamsArticleDto genericSerchParamsArticleDto, String companyCode, String branchCode,
			String idOperation, boolean debug) {

		LOG.info(String.format("%s INIT findArticleByBarcodeAndCompanyCode() ", idOperation));
		LOG.info(String.format(
				"%s PARAMS: [articlePersistencePort,  genericSerchParamsArticleDto: %s , companyCode: %s , branchCode: %s ]",
				idOperation, genericSerchParamsArticleDto.toString(), companyCode, branchCode));

		LOG.info(String.format("%s INIT VALIDATION PARAMS", idOperation));
		String validations = articleGenericValidations.validParamsToSerchArticleByBarcode(genericSerchParamsArticleDto);

		if (!validations.isEmpty()) {

			LOG.warn(String.format("%s INCORRECT PARAMS TO SERCH ARTICLE: %s ", idOperation, validations));
			throw new ValidationError(validations);
		}

		LOG.info(String.format("%s INIT SERCH ARTICLE NUMBER TO COMPLETE DATA FILTER", idOperation));
		GenericSerchParamsArticleDto articleParams = (GenericSerchParamsArticleDto) articlePersistencePort
				.findArticleByScannerAndCompanyCode(genericSerchParamsArticleDto,
						genericSerchParamsArticleDto.getBarcode(), companyCode, idOperation)
				.getData();

		if (articleParams.getArticleCode().isEmpty())
			LOG.warn(String.format("%s BARCODE DOES NOT HAVE AN ASSOCIATED ARTICLE, SET BARCODE TO ARTICLE CODE FIELD",
					idOperation));

		List<ArticleDto> articleList = (List<ArticleDto>) articlePersistencePort
				.findArticlesByParamnsAndCompanyCode(articleParams, companyCode, branchCode, idOperation, debug)
				.getData();

		return new ResponseModel(articleList);
	}

	/**
	 * Método que recupera la lista de los factores de conversión relacionados a un
	 * artículo, en caso de que no se tenga una configuración relacionada se
	 * retornara una lista vacia.
	 * 
	 * @param articlePersistencePort puerto de comunicación para la capa de
	 *                               infraestructura
	 * @param articleNumber          número de artículo
	 * @param companyCode            código de compañia
	 * @param idOperation            identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel findConvertionFactorListByArticleNumberAndCompanyCode(
			ArticlePersistencePort articlePersistencePort, BigDecimal articleNumber, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT findConvertionFactorListByArticleNumberAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ articleNumber: %s , companyCode: %s ]", idOperation,
				articleNumber.toString(), companyCode));

		LOG.info(String.format("%s INIT SERCH CONVERTION FACTOR LIST", idOperation));
		List<ArticleConvertionFactor> articleConvertionFactorList = (List<ArticleConvertionFactor>) articlePersistencePort
				.findConvertionFactorListByArticleNumberAndCompanyCode(articleNumber, companyCode, idOperation)
				.getData();

		if (articleConvertionFactorList.size() > 0) {
			LOG.info(String.format("%s SET INVERSE FACTOR", idOperation));
			for (ArticleConvertionFactor articleConvertionFactor : articleConvertionFactorList) {
				articleConvertionFactor.setReverseConvertionFactor(BigDecimal.ONE.divide(articleConvertionFactor.getConvertionFactor()));
			}
		}

		LOG.info(String.format("%s CONVERTION FACTOR LIST SIZE: %d", idOperation, articleConvertionFactorList.size()));

		return new ResponseModel(articleConvertionFactorList);
	}

}
