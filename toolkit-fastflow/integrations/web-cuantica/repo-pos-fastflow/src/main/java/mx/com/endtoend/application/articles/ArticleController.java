package mx.com.endtoend.application.articles;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.domain.articles.ports.api.ArticleServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.debug.ports.api.DebugServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Controlador para la búsqued de artículos de las compañías
 * 
 * @author ddcasas
 *
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private ArticleServicePort articleServicePort;

	@Autowired
	private DebugServicePort debugServicePort;

	private static String module = "ARTICLES";
	public String idOperation;

	private final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

	/**
	 * EndPoint para la consulta de artículos por filtros y compañia.
	 * 
	 * @param companyCode                  código de la compañía
	 * @param genericSerchParamsArticleDto parámetros de búsqueda
	 * @param branchCode                   código de sucursal
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@PostMapping("/find-by-params/{companyCode}/{branchCode}")
	public ResponseEntity<?> findArticlesByParams(@PathVariable String companyCode, @PathVariable String branchCode,
			@RequestBody GenericSerchParamsArticleDto genericSerchParamsArticleDto) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT findArticlesByParams()", idOperation));

		boolean debug = (boolean) debugServicePort.getDebugStatusByCompanyCodeAndModule(companyCode, module).getData();
		LOG.info(String.format("%s DEBUG: %b", idOperation, debug));

		debugServicePort.saveLog(module, companyCode, "A-INIT findArticlesByParams()", idOperation, debug);

		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s , genericSerchParamsArticleDto: %s ]",
				idOperation, companyCode, branchCode, genericSerchParamsArticleDto.toString()));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}

		ResponseModel responseModel = articleServicePort.findAticlesByParamsAndCompanyCode(debugServicePort, debug,
				genericSerchParamsArticleDto, companyCode, branchCode, method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la búsqueda de artículos por codigo de barras y compañia
	 * 
	 * @param companyCode                  código de compañía
	 * @param branchCode                   código de sucursal
	 * @param genericSerchParamsArticleDto parámetros de búsqueda
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@PostMapping("/find-by-scanner/{companyCode}/{branchCode}")
	public ResponseEntity<?> findArticleByScannerBarcode(@PathVariable String companyCode,
			@PathVariable String branchCode, @RequestBody GenericSerchParamsArticleDto genericSerchParamsArticleDto) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT findArticleByScannerBarcode()", idOperation));

		boolean debug = (boolean) debugServicePort.getDebugStatusByCompanyCodeAndModule(companyCode, module).getData();
		LOG.info(String.format("%s DEBUG: %b", idOperation, debug));

		debugServicePort.saveLog(module, companyCode, "A-INIT findArticleByScannerBarcode()", idOperation, debug);

		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s , genericSerchParamsArticleDto: %s ]",
				idOperation, companyCode, branchCode, genericSerchParamsArticleDto.toString()));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}

		ResponseModel responseModel = articleServicePort.findArticleByScannerBarcode(debugServicePort, debug,
				genericSerchParamsArticleDto, companyCode, branchCode, method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	/**
	 * EndPoint para la recuperación de la lista de unidades de conversión
	 * disponibles por numero de artículo
	 * 
	 * @param articleNumber número de artículo
	 * @param companyCode   código de compañia
	 * @param branchCode    código de sucursal
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/convertion-factor/{articleNumber}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getConversionFactorByArticleNumber(@PathVariable BigDecimal articleNumber,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getConversionFactorByArticleNumber()", idOperation));

		boolean debug = (boolean) debugServicePort.getDebugStatusByCompanyCodeAndModule(companyCode, module).getData();
		LOG.info(String.format("%s DEBUG: %b", idOperation, debug));

		debugServicePort.saveLog(module, companyCode, "A-INIT getConversionFactorByArticleNumber()", idOperation,
				debug);
		LOG.info(String.format("%s PARAMS: [articleNumber: %s companyCode: %s , branchCode: %s ]", idOperation,
				articleNumber.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}

		ResponseModel responseModel = articleServicePort.findConvertionFactorListByArticleNumberAndCompanyCode(
				debugServicePort, debug, articleNumber, companyCode, method.getCode(), branchCode);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
