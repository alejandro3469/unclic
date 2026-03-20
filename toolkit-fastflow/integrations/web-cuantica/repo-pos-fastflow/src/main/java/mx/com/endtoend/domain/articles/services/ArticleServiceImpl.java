package mx.com.endtoend.domain.articles.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.domain.articles.factory.ArticleFactory;
import mx.com.endtoend.domain.articles.factory.ArticleInterface;
import mx.com.endtoend.domain.articles.ports.api.ArticleServicePort;
import mx.com.endtoend.domain.articles.ports.spi.ArticlePersistencePort;
import mx.com.endtoend.domain.debug.ports.api.DebugServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase que gestiona la recuperación del método asignado a cada compañia para
 * la gestión de los artículos del sistema
 * 
 * @author ddcasas
 *
 */

public class ArticleServiceImpl implements ArticleServicePort {

	private ArticlePersistencePort articlePersistencePort;

	public ArticleServiceImpl(ArticlePersistencePort articlePersistencePort) {
		this.articlePersistencePort = articlePersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);

	ArticleFactory articleFactory = new ArticleFactory();

	/**
	 * Método que recupera la implementación concreta de la interfaz
	 * ArticleInterface por código del método configurado a la compañía para la
	 * búsqueda de artículos por filtro de datos.
	 * 
	 * @param genericSerchParamsArticleDto objeto que contine los filtros de
	 *                                     búsqueda de artículos
	 * @param companyCode                  código de compañía
	 * @param branchCode                   código de sucursal
	 * @param method                       código del método configurado a la
	 *                                     compañía
	 * @param idOperation                  traza de identificador de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel findAticlesByParamsAndCompanyCode(DebugServicePort debugServicePort, boolean debug,
			GenericSerchParamsArticleDto genericSerchParamsArticleDto, String companyCode, String brabchCode,
			String method, String idOperation) {

		LOG.info(String.format("%s INIT findAticlesByParamsAndCompanyCode() ", idOperation));
		LOG.info(String.format(
				"%s PARAMS: [ genericSerchParamsArticleDto: %s , companyCode: %s , brabchCode: %s , method: %s ]",
				idOperation, genericSerchParamsArticleDto.toString(), companyCode, brabchCode, method));

		ArticleInterface article = articleFactory.getImplementation(method, debugServicePort);

		if (article == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = article.findArticlesByParamsAndCompanyCode(articlePersistencePort,
				genericSerchParamsArticleDto, companyCode, brabchCode, idOperation, debug);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;
	}

	/**
	 * Método que recupera la implementación concreta de la interfaz
	 * ArticleInterface por código del método configurado a la compañía para la
	 * búsqueda de artículos por filtro de datos por medio del código de barras
	 * escaneado.
	 * 
	 * @param genericSerchParamsArticleDto objeto que contine los filtros de
	 *                                     búsqueda de artículos
	 * @param companyCode                  código de compañía
	 * @param branchCode                   código de sucursal
	 * @param method                       código del método configurado a la
	 *                                     compañía
	 * @param idOperation                  traza de identificador de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */

	@Override
	public ResponseModel findArticleByScannerBarcode(DebugServicePort debugServicePort, boolean debug,
			GenericSerchParamsArticleDto genericSerchParamsArticleDto, String companyCode, String branchCode,
			String method, String idOperation) {

		LOG.info(String.format("%s INIT findArticleByScannerBarcode() ", idOperation));
		LOG.info(String.format(
				"%s PARAMS: [ genericSerchParamsArticleDto: %s , companyCode: %s , branchCode: %s , method: %s ]",
				idOperation, genericSerchParamsArticleDto.toString(), companyCode, branchCode, method));

		ArticleInterface article = articleFactory.getImplementation(method, debugServicePort);

		if (article == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = article.findArticleByBarcodeAndCompanyCode(articlePersistencePort,
				genericSerchParamsArticleDto, companyCode, branchCode, idOperation, debug);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;
	}

	/**
	 * Método que recupera la implementación concreta de la interfaz
	 * ArticleInterface por código del método configurado a la compañía para la
	 * recuperación de la lista de converciones de unidades asociadas al número de
	 * artículo ingresado.
	 * 
	 * @param articleNumber número de artículo
	 * @param companyCode   código de compañía
	 * @param method        método configurado a la compañía
	 * @param idOperation   identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel findConvertionFactorListByArticleNumberAndCompanyCode(DebugServicePort debugServicePort,
			boolean debug, BigDecimal articleNumber, String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT findConvertionFactorListByArticleNumberAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ articleNumber: %s , companyCode: %s , method: %s ]", idOperation,
				articleNumber.toString(), companyCode, method));

		ArticleInterface article = articleFactory.getImplementation(method, debugServicePort);

		if (article == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = article.findConvertionFactorListByArticleNumberAndCompanyCode(
				articlePersistencePort, articleNumber, companyCode, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

}