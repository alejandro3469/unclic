package mx.com.endtoend.application.openings;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

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

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.openings.ports.OpeningOperationServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/opening-operation")
public class OpeningOperationController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	@Autowired
	private OpeningOperationServicePort openingOperationServicePort;

	private String module = "OPENING_OPERATION";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(OpeningOperationController.class);

	/**
	 * EndPoint para la creación de apertura de operación de los empleados
	 * encargados de los ingresos contables
	 * 
	 * @param openingOperationDto detalle de apertura de operación
	 * @param companyCode         código de compañía
	 * @param branchCode          código de la sucursal origen
	 * @return
	 */
	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createOpeningOpereation(@RequestBody OpeningOperationDto openingOperationDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createOpeningOpereation()", idOperation));
		LOG.info(String.format("%s PARAMS: [openingOperationDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				openingOperationDto.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		// Validación: El detalle de apertura de operación no puede estar vacío
		if (openingOperationDto.getOpeningOperationDetail() == null || 
		    openingOperationDto.getOpeningOperationDetail().isEmpty()) {
		    LOG.error(String.format("%s ERROR: openingOperationDetail está vacío o es null", idOperation));
		    throw new ValidationError("El detalle de apertura de operación no puede estar vacío. Debe incluir al menos un instrumento de pago.");
		}
		ResponseModel responseModel = openingOperationServicePort.createOpeningOperationByCompanyCode(
				accountingRecordPersistencePort, openingOperationDto, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/status/{employeeEmail}/{companyCode}/{branchCode}")
	public ResponseEntity<?> validOpeningOperationStatusActiveByEmployeeEmailAndCompanyCode(
			@PathVariable String employeeEmail, @PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(
				String.format("%s INIT validOpeningOperationStatusActiveByEmployeeEmailAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [employeeEmail: %s , companyCode: %s , branchCode: %s ]", idOperation,
				employeeEmail, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = openingOperationServicePort
				.validOpeningOperationStatusByEmployeeEmailAndCompanyCode(accountingRecordPersistencePort,
						method.getCode(), employeeEmail, companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
