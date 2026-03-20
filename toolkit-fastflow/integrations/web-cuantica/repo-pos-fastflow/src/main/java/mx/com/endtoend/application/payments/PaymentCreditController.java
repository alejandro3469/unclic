package mx.com.endtoend.application.payments;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditPaymentInterfaceService;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.ports.PaymentCreditServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

/**
 * Controlador para los registros de transacciones de cobros con crédito del
 * servicio BDS
 * 
 * @author ddcasas
 */
@RestController
@RequestMapping("/payment-credit")
public class PaymentCreditController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private PaymentCreditServicePort paymentCreditServicePort;

	@Autowired
	private PaymentPersistencePort paymentPersistencePort;

	public String idOperation = "";
	private String module = "PAYMENT_CREDIT";
	private final Logger LOG = LoggerFactory.getLogger(PaymentCreditController.class);

	/**
	 * Valida la precisión decimal de CreditSaleRequestDto según estándares SAT
	 */
	private void validateCreditSaleRequestPrecision(CreditSaleRequestDto creditSaleRequestDto, String idOperation) {
		LOG.info(String.format("%s INIT validateCreditSaleRequestPrecision()", idOperation));
		
		// Validar venta_total
		if (creditSaleRequestDto.getVenta_total() != null) {
			if (!PrecisionValidator.isValidMonetaryRange(creditSaleRequestDto.getVenta_total())) {
				LOG.error(String.format("%s ERROR: venta_total fuera de rango válido: %s", idOperation, creditSaleRequestDto.getVenta_total()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(creditSaleRequestDto.getVenta_total(), 2)) {
				LOG.error(String.format("%s ERROR: venta_total con escala incorrecta: %s", idOperation, creditSaleRequestDto.getVenta_total()));
				throw new GlobalError();
			}
		}
		
		// Validar campos monetarios en detail
		if (creditSaleRequestDto.getDetail() != null) {
			for (var detail : creditSaleRequestDto.getDetail()) {
				if (detail.getAmount() != null) {
					if (!PrecisionValidator.isValidMonetaryRange(detail.getAmount())) {
						LOG.error(String.format("%s ERROR: amount fuera de rango válido: %s", idOperation, detail.getAmount()));
						throw new GlobalError();
					}
					if (!PrecisionValidator.isValidScale(detail.getAmount(), 2)) {
						LOG.error(String.format("%s ERROR: amount con escala incorrecta: %s", idOperation, detail.getAmount()));
						throw new GlobalError();
					}
				}
				if (detail.getTotal_operacion() != null) {
					if (!PrecisionValidator.isValidMonetaryRange(detail.getTotal_operacion())) {
						LOG.error(String.format("%s ERROR: total_operacion fuera de rango válido: %s", idOperation, detail.getTotal_operacion()));
						throw new GlobalError();
					}
					if (!PrecisionValidator.isValidScale(detail.getTotal_operacion(), 2)) {
						LOG.error(String.format("%s ERROR: total_operacion con escala incorrecta: %s", idOperation, detail.getTotal_operacion()));
						throw new GlobalError();
					}
				}
			}
		}
		
		LOG.info(String.format("%s SUCCESS: CreditSaleRequestDto validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a CreditSaleRequestDto
	 */
	private CreditSaleRequestDto applySATRounding(CreditSaleRequestDto creditSaleRequestDto, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		// Aplicar redondeo SAT a venta_total
		if (creditSaleRequestDto.getVenta_total() != null) {
			BigDecimal roundedVentaTotal = DecimalPrecisionUtils.roundToTwoDecimals(creditSaleRequestDto.getVenta_total());
			creditSaleRequestDto.setVenta_total(roundedVentaTotal);
			LOG.info(String.format("%s venta_total redondeado: %s", idOperation, roundedVentaTotal));
		}
		
		// Aplicar redondeo SAT a campos monetarios en detail
		if (creditSaleRequestDto.getDetail() != null) {
			for (var detail : creditSaleRequestDto.getDetail()) {
				if (detail.getAmount() != null) {
					BigDecimal roundedAmount = DecimalPrecisionUtils.roundToTwoDecimals(detail.getAmount());
					detail.setAmount(roundedAmount);
					LOG.info(String.format("%s amount redondeado: %s", idOperation, roundedAmount));
				}
				if (detail.getTotal_operacion() != null) {
					BigDecimal roundedTotalOperacion = DecimalPrecisionUtils.roundToTwoDecimals(detail.getTotal_operacion());
					detail.setTotal_operacion(roundedTotalOperacion);
					LOG.info(String.format("%s total_operacion redondeado: %s", idOperation, roundedTotalOperacion));
				}
			}
		}
		
		LOG.info(String.format("%s SUCCESS: CreditSaleRequestDto redondeado según SAT", idOperation));
		return creditSaleRequestDto;
	}

	@PostMapping("/sale/request/{companyCode}/{branchCode}")
	public ResponseEntity<?> saveCreditSaleRequest(@RequestBody CreditSaleRequestDto creditSaleRequestDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT saveCreditSaleRequest() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ creditSaleRequestDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, creditSaleRequestDto.toString(), branchCode, companyCode));

		// Validar precisión decimal del CreditSaleRequestDto
		validateCreditSaleRequestPrecision(creditSaleRequestDto, idOperation);

		// Aplicar redondeo SAT
		creditSaleRequestDto = applySATRounding(creditSaleRequestDto, idOperation);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		CreditPaymentInterfaceService creditPaymentInterfaceService = new CreditPaymentInterfaceService(
				paymentPersistencePort, companyCode, idOperation);
		ResponseModel response = paymentCreditServicePort.saveCreditSaleRequestByMethod(creditPaymentInterfaceService,
				creditSaleRequestDto, method.getCode());
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/sale/response/{companyCode}/{branchCode}")
	public ResponseEntity<?> saveCreditSaleResponse(@RequestBody CreditSaleResponseDto creditSaleResponseDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT saveCreditSaleResponse() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ creditSaleResponseDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, creditSaleResponseDto.toString(), branchCode, companyCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		CreditPaymentInterfaceService creditPaymentInterfaceService = new CreditPaymentInterfaceService(
				paymentPersistencePort, companyCode, idOperation);
		ResponseModel response = paymentCreditServicePort.saveCreditSaleResponseByMethod(creditPaymentInterfaceService,
				creditSaleResponseDto, method.getCode());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/payment-status/request/{companyCode}/{branchCode}")
	public ResponseEntity<?> savePaymentStateRequest(@RequestBody StatusSaleRequestDto statusSaleRequestDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT savePaymentStateRequest() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ statusSaleRequestDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, statusSaleRequestDto.toString(), branchCode, companyCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		CreditPaymentInterfaceService creditPaymentInterfaceService = new CreditPaymentInterfaceService(
				paymentPersistencePort, companyCode, idOperation);
		ResponseModel response = paymentCreditServicePort.savePaymentStateRequestByMethod(creditPaymentInterfaceService,
				statusSaleRequestDto, method.getCode());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/payment-status/response/{companyCode}/{branchCode}")
	public ResponseEntity<?> savePaymentStateResponse(@RequestBody StatusSaleResponseDto statusSaleResponseDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT savePaymentStateResponse() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ statusSaleResponseDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, statusSaleResponseDto.toString(), branchCode, companyCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		CreditPaymentInterfaceService creditPaymentInterfaceService = new CreditPaymentInterfaceService(
				paymentPersistencePort, companyCode, idOperation);
		ResponseModel response = paymentCreditServicePort.savePaymentStateResponseByMethod(
				creditPaymentInterfaceService, statusSaleResponseDto, method.getCode());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/payment-status/search/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getPaymentStateByOrderNumberAndCode(@PathVariable BigDecimal orderNumber,
			@PathVariable String orderCode, @PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getPaymentStateByOrderNumberAndCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderNumber: %s , orderCode: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, orderNumber.toString(), orderCode, branchCode, companyCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		CreditPaymentInterfaceService creditPaymentInterfaceService = new CreditPaymentInterfaceService(
				paymentPersistencePort, companyCode, idOperation);
		ResponseModel response = paymentCreditServicePort.getPaymentStateByOrderNumberAndCodeByMethod(
				creditPaymentInterfaceService, orderNumber, orderCode, method.getCode());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateCreditPaymentByOrderNumberAndCode(@PathVariable BigDecimal orderNumber,
			@PathVariable String orderCode, @PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT generateCreditPaymentByOrderNumberAndCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderNumber: %s , orderCode: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, orderNumber.toString(), orderCode, branchCode, companyCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		CreditPaymentInterfaceService creditPaymentInterfaceService = new CreditPaymentInterfaceService(
				paymentPersistencePort, companyCode, idOperation);
		ResponseModel response = paymentCreditServicePort.generateCreditPaymentTicketByOrderNumberAndCodeByMethod(
				creditPaymentInterfaceService, orderNumber, orderCode, method.getCode());

		byte[] reporte = (byte[]) response.getData();

		ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
				.filename(orderCode + "-" + orderNumber + ".pdf").build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);

		return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
				.headers(headers).body(new ByteArrayResource(reporte));
	}

	@GetMapping("/cancel/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> cancelCreditPaymentProcessByOrderNumberAndCode(@PathVariable BigDecimal orderNumber,
			@PathVariable String orderCode, @PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getPaymentStateByOrderNumberAndCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderNumber: %s , orderCode: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, orderNumber.toString(), orderCode, branchCode, companyCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		CreditPaymentInterfaceService creditPaymentInterfaceService = new CreditPaymentInterfaceService(
				paymentPersistencePort, companyCode, idOperation);
		ResponseModel response = paymentCreditServicePort.cancelCreditPaymentByOrderNumberAndCodeByMethod(
				creditPaymentInterfaceService, orderNumber, orderCode, method.getCode());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
