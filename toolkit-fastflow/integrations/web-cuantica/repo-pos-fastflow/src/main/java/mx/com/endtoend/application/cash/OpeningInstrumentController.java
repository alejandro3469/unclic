package mx.com.endtoend.application.cash;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

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

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.cash.openingInstruments.ports.api.OpeningInstrumentServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Controlador encargado de la administración de los instrumetos de aperturas
 * 
 * @author ddcasas
 *
 */
@RestController
@RequestMapping("/opening-instrument")
public class OpeningInstrumentController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private OpeningInstrumentServicePort openingInstrumentServicePort;

	private String module = "OPENING_INSTRUMENT";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(OpeningInstrumentController.class);

	/**
	 * Endpoint para la creación de instrumentos de pagos empleados en las aperturas
	 * de operaciones
	 * 
	 * @param openPaymentInstrumentDto
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createOpenPaymentInstrumentByCompanyCode(
			@RequestBody OpenPaymentInstrumentDto openPaymentInstrumentDto, @PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createOpenPaymentInstrumentByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [openPaymentInstrumentDto: %s , companyCode: %s , branchCode: %s ]",
				idOperation, openPaymentInstrumentDto.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = openingInstrumentServicePort.createOpenPaymentInstrumentByCompanyCode(
				openPaymentInstrumentDto, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la visualización del detalle de los instrumentos de pago
	 * empleados en las aperturas de operacion
	 * 
	 * @param id
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/view/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewDetailByIdAndCompanyCode(@PathVariable Long id, @PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewDetailByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , companyCode: %s , branchCode: %s ]", idOperation, id.toString(),
				companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = openingInstrumentServicePort.viewOpenPaymentInstrumentByIAndCompanyCode(id,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la actualización de instrumentos de pago empleados en las
	 * aperturas de operación
	 * 
	 * @param openPaymentInstrumentDto
	 * @param id
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@PutMapping("/update/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> updateOpenPaymentInstrumentByCompanyCodeAndId(
			@RequestBody OpenPaymentInstrumentDto openPaymentInstrumentDto, @PathVariable Long id,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT updateOpenPaymentInstrumentByCompanyCodeAndId()", idOperation));

		LOG.info(String.format("%s PARAMS: [openPaymentInstrumentDto: %s , id: %s , companyCode: %s , branchCode: %s ]",
				idOperation, openPaymentInstrumentDto.toString(), id.toString(), companyCode, branchCode));
		openPaymentInstrumentDto.setId(id);
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = openingInstrumentServicePort.updateOpenPaymentInstrumentByCompanyCodeAndId(
				openPaymentInstrumentDto, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la recuperación de los instrumentos de pago activos en las
	 * aperturas de operacion
	 * 
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/view/active/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewActiveOpenPaymentInstrumentListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewActiveOpenPaymentInstrumentListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = openingInstrumentServicePort
				.viewOpenPaymentInstrumentListByCompanyCodeAndEnable(true, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la recuperación de los instrumentos de pago inactivos en las
	 * aperturas de operacion
	 * 
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/view/inactive/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewInactiveOpenPaymentInstrumentListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewInactiveOpenPaymentInstrumentListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = openingInstrumentServicePort
				.viewOpenPaymentInstrumentListByCompanyCodeAndEnable(false, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
