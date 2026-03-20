package mx.com.endtoend.infrastructure.articles.common.adapter;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.infrastructure.articles.common.factory.ArticleRepositoryFactory;
import mx.com.endtoend.infrastructure.articles.common.repository.GenericArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.articles.dto.ArticleConvertionFactor;
import mx.com.endtoend.domain.articles.dto.ArticleDto;
import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.domain.articles.ports.spi.ArticlePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase que obtiene el repositorio concreto para la administración de artículos
 * por código de compañía.
 * 
 * @author ddcasas
 *
 */
@Service
public class ArticleJpaAdapter implements ArticlePersistencePort {

	@Autowired
	private ArticleRepositoryFactory articleRepositoryFactory;
	
	private final Logger LOG = LoggerFactory.getLogger(ArticleJpaAdapter.class);

	/**
	 * Método que recupera la implementación concreta de la interfaz
	 * GenericArticleRepository por códig de compañía para la recuperación de la
	 * lista de artículos por los filtros de entrada.
	 * 
	 * @param genericSerchParamsArticleDto objeto con los filtros de búsqueda de
	 *                                     artículos
	 * @param companyCode                  código de compañía
	 * @param branchCode                   código de sucursal
	 * @param idOperation                  identificador de traza
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 *
	 */
	@Override
	public ResponseModel findArticlesByParamnsAndCompanyCode(GenericSerchParamsArticleDto genericSerchParamsArticleDto,
			String companyCode, String branchCode, String idOperation, boolean debug) {

		LOG.info(String.format("%s INIT findArticlesByParamnsAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [genericSerchParamsArticleDto: %s , companyCode: %s , branchCode: %s ]",
				idOperation, genericSerchParamsArticleDto.toString(), companyCode, branchCode));

		GenericArticleRepository repository = articleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		List<ArticleDto> articles = repository.findArticlesByParams(genericSerchParamsArticleDto, branchCode,
				idOperation, debug);

		LOG.info(String.format("%s LIST SIZE RETURN: %d", idOperation, articles.size()));
		return new ResponseModel(articles);
	}

	/**
	 * Método que recupera la implementación concreta de la interfaz
	 * GenericArticleRepository por códig de compañía para la obtención del número
	 * de artículo por codigo de barras y almacén introducido.
	 * 
	 * @param barcode       código de barras escaneado
	 * @param warehouseCode código de almacen para consulta
	 * @param companyCode   código de compañía
	 * @param idOperation   traza de identificación de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 * 
	 */
	@Override
	public ResponseModel findArticleByScannerAndCompanyCode(GenericSerchParamsArticleDto genericSerchParamsArticleDto,
			String barcode, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT findArticleByScannerAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [barcode: %s, genericSerchParamsArticleDto: %s , companyCode: %s ]",
				idOperation, barcode, genericSerchParamsArticleDto.toString(), companyCode));

		GenericArticleRepository repository = articleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		GenericSerchParamsArticleDto articleInformation = repository
				.findArticleCodeByBarcodeAndWarehouseCode(genericSerchParamsArticleDto, barcode, idOperation);

		return new ResponseModel(articleInformation);
	}

	/**
	 * Método que recupera la implementación concreta de la interfaz
	 * GenericArticleRepository por códig de compañía para la obtención de la lista
	 * de factores de converción relacionadas a un artículo por su número.
	 * 
	 * @param articleNumber número de artículo
	 * @param companyCode   código de compañía
	 * @param idOperation   identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel findConvertionFactorListByArticleNumberAndCompanyCode(BigDecimal articleNumber,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT findConvertionFactorListByArticleNumberAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ articleNumber: %s , companyCode: %s ]", idOperation,
				articleNumber.toString(), companyCode));

		GenericArticleRepository repository = articleRepositoryFactory.getRepository(companyCode);

		if (repository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		List<ArticleConvertionFactor> convertionFactoList = repository
				.findConvertionFactorByArticleNumber(articleNumber, idOperation);

		return new ResponseModel(convertionFactoList);
	}
}