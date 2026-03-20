package mx.com.endtoend.application.closings;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.closings.ports.ClosingOperationServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Controlador para la creación del cierre de oepración contable en el sistema
 * 
 * @author ddcasas
 *
 */

@RestController
@RequestMapping("/closing-operation")
public class ClosingOperationController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	@Autowired
	private OpeningOperationPersistencePort openingOperationPersistencePort;

	@Autowired
	private ClosingOperationServicePort closingOperationServicePort;

	@Autowired
	private EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort;

	private String module = "CLOSING_OPERATION";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(ClosingOperationController.class);

	/**
	 * EndPoint para el registro de cierre de operación contable
	 * 
	 * @param closingOperationDto datos operaticos del cierre
	 * @param companyCode         código de compañia
	 * @param branchCode          código de sucursal donde se ejecuta el proceso
	 * @return
	 */
	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createClosingOperation(@RequestBody ClosingOperationDto closingOperationDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createClosingOperation()", idOperation));
		LOG.info(String.format("%s PARAMS: [closingOperationDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				closingOperationDto.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		// Validación: El detalle de cierre de operación no puede estar vacío
		if (closingOperationDto.getClosingOperationDetail() == null || 
		    closingOperationDto.getClosingOperationDetail().isEmpty()) {
		    LOG.error(String.format("%s ERROR: closingOperationDetail está vacío o es null", idOperation));
		    throw new ValidationError("El detalle de cierre de operación no puede estar vacío. Debe incluir al menos un instrumento de pago.");
		}
		ResponseModel responseModel = closingOperationServicePort.createClosingOperationByCompanyCode(
				accountingRecordPersistencePort, openingOperationPersistencePort, emailCashConfigurationPersistencePort,
				closingOperationDto, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
