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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.domain.articles.ports.api.CustomArticleServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

/**
 * Controlador para la administración de los artículos personalizados de cada
 * compañía
 * 
 * @author ddcasas
 *
 */

@RestController
@RequestMapping("/custom-article")
public class CustomArticleController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private CustomArticleServicePort customArticleServicePort;

	private static String module = "CUSTOM_ARTICLES";
	public String idOperation;

	private final Logger LOG = LoggerFactory.getLogger(CustomArticleController.class);

	/**
	 * Valida la precisión decimal de CustomArticleDto según estándares SAT
	 */
	private void validateCustomArticlePrecision(CustomArticleDto customArticleDto, String idOperation) {
		LOG.info(String.format("%s INIT validateCustomArticlePrecision()", idOperation));
		
		// Validar price
		if (customArticleDto.getPrice().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(customArticleDto.getPrice())) {
				LOG.error(String.format("%s ERROR: price fuera de rango válido: %s", idOperation, customArticleDto.getPrice()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(customArticleDto.getPrice(), 2)) {
				LOG.error(String.format("%s ERROR: price con escala incorrecta: %s", idOperation, customArticleDto.getPrice()));
				throw new GlobalError();
			}
		}
		
		LOG.info(String.format("%s SUCCESS: CustomArticleDto validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a CustomArticleDto
	 */
	private CustomArticleDto applySATRounding(CustomArticleDto customArticleDto, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		// Aplicar redondeo SAT a price
		if (customArticleDto.getPrice().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedPrice = DecimalPrecisionUtils.roundToTwoDecimals(customArticleDto.getPrice());
			customArticleDto.setPrice(roundedPrice);
			LOG.info(String.format("%s price redondeado: %s", idOperation, roundedPrice));
		}
		
		LOG.info(String.format("%s SUCCESS: CustomArticleDto redondeado según SAT", idOperation));
		return customArticleDto;
	}

	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createCustomArticle(@PathVariable String companyCode, @PathVariable String branchCode,
			@RequestBody CustomArticleDto customArticleDto) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s CREATE CUSTOM-ARTICLE", idOperation));
		LOG.info(String.format("%s PARAMS: customArticleDto: %s companyCode: %s branchCode: %s ", idOperation,
				customArticleDto.toString(), companyCode, branchCode));

		// Validar precisión decimal del CustomArticleDto
		validateCustomArticlePrecision(customArticleDto, idOperation);

		// Aplicar redondeo SAT
		customArticleDto = applySATRounding(customArticleDto, idOperation);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = customArticleServicePort.createCustomArticle(customArticleDto, companyCode,
				branchCode, method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	@PutMapping("/update/{companyCode}/{branchCode}/{id}")
	public ResponseEntity<?> updateCustomArticleById(@PathVariable String companyCode, @PathVariable String branchCode,
			@RequestBody CustomArticleDto customArticleDto, @PathVariable Long id) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s UPDATE CUSTOM-ARTICLE", idOperation));
		LOG.info(String.format("%s PARAMS: %s", idOperation, customArticleDto.toString()));

		// Validar precisión decimal del CustomArticleDto
		validateCustomArticlePrecision(customArticleDto, idOperation);

		// Aplicar redondeo SAT
		customArticleDto = applySATRounding(customArticleDto, idOperation);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		customArticleDto.setId(id);
		ResponseModel responseModel = customArticleServicePort.updateCustomArticleById(customArticleDto, companyCode,
				branchCode, method.getCode(), branchCode);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/enabled/{id}/{companyCode}/{branchCode}/{status:enable|disable}")
	public ResponseEntity<?> enableById(@PathVariable String companyCode, @PathVariable String branchCode,
			@PathVariable(name = "status", required = true) String status,
			@PathVariable(name = "id", required = true) Long id) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s ENABLE BY ID", idOperation));
		LOG.info(String.format("%s ENABLE: %s CUSTOM-ARTICLE BY ID: %d ", idOperation, status, id));
		boolean enabled = status.equals("enable") ? true : false;

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = customArticleServicePort.enableCustomArticleById(companyCode, branchCode,
				idOperation, method.getCode(), enabled, id);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	@GetMapping("/view/active/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewActiveCustomArticles(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s FIND ALL ACTIVE CUSTOM-ARTICLES", idOperation));
		LOG.info(String.format("%s PARAMS: companyCode: %s branchCode: %s", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = customArticleServicePort.findAllCustomArticleByEnable(companyCode, branchCode,
				method.getCode(), idOperation, true);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/inactive/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewInactiveCustomArticles(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s FIND ALL INACTIVE CUSTOM-ARTICLES", idOperation));
		LOG.info(String.format("%s PARAMS: companyCode: %s branchCode: %s ", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = customArticleServicePort.findAllCustomArticleByEnable(companyCode, branchCode,
				method.getCode(), idOperation, false);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewCustomArticleById(@PathVariable String companyCode, @PathVariable String branchCode,
			@PathVariable Long id) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s FIND CUSTOM-ARTICLES BY ID", idOperation));
		LOG.info(String.format("%s PARAMS: companyCode: %s branchCode: %s id: %s ", idOperation, companyCode,
				branchCode, id));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = customArticleServicePort.findCustomArticleById(id, companyCode, branchCode,
				method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/viwe/sale-type/{saleType}/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewAllByActiveAndSaleType(@PathVariable String companyCode,
			@PathVariable String branchCode, @PathVariable String saleType) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewAllByActiveAndSaleType()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s , saleType: %s]", idOperation, companyCode,
				branchCode, saleType));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = customArticleServicePort.findAllActiveBySaleType(companyCode, saleType,
				method.getCode(), idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}