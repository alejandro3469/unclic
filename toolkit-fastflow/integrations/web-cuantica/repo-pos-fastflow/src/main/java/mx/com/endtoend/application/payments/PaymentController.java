package mx.com.endtoend.application.payments;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.domain.payments.ports.PaymentServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.bds.BDSServicePort;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

/**
 * Controlador para la administración de los cobros del sistema y generación de
 * ticktes de cobro
 * 
 * @author ddcasas
 *
 */

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private PaymentJDEServicePort paymentJDEServicePort;

	@Autowired
	private PaymentServicePort paymentServicePort;

	@Autowired
	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	@Autowired
	private CreditNotePersistencePort creditNotePersistencePort;

	@Autowired
	private CompanyPersistencePort companyPersistencePort;

	@Autowired
	private CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort;

	@Autowired
	private BDSServicePort bdsServicePort;

	public String idOperation = "";
	private String module = "PAYMENTS";
	private final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

	/**
	 * Valida la precisión decimal de un PaymentDto según estándares SAT
	 * 
	 * @param paymentDto DTO de pago a validar
	 * @param idOperation ID de operación para logging
	 */
	private void validatePaymentPrecision(PaymentDto paymentDto, String idOperation) {
		LOG.info(String.format("%s INIT validatePaymentPrecision()", idOperation));
		
		// Validar orderTotal
		if (paymentDto.getOrderTotal() != null) {
			if (!PrecisionValidator.isValidMonetaryRange(paymentDto.getOrderTotal())) {
				LOG.error(String.format("%s ERROR: orderTotal fuera de rango válido: %s", idOperation, paymentDto.getOrderTotal()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(paymentDto.getOrderTotal(), 2)) {
				LOG.error(String.format("%s ERROR: orderTotal con escala incorrecta: %s", idOperation, paymentDto.getOrderTotal()));
				throw new GlobalError();
			}
		}
		
		// Validar pendingPayment
		if (paymentDto.getPendingPayment() != null) {
			if (!PrecisionValidator.isValidMonetaryRange(paymentDto.getPendingPayment())) {
				LOG.error(String.format("%s ERROR: pendingPayment fuera de rango válido: %s", idOperation, paymentDto.getPendingPayment()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(paymentDto.getPendingPayment(), 2)) {
				LOG.error(String.format("%s ERROR: pendingPayment con escala incorrecta: %s", idOperation, paymentDto.getPendingPayment()));
				throw new GlobalError();
			}
		}
		
		LOG.info(String.format("%s SUCCESS: PaymentDto validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a un PaymentDto
	 * 
	 * @param paymentDto DTO de pago a redondear
	 * @param idOperation ID de operación para logging
	 * @return PaymentDto con valores redondeados según SAT
	 */
	private PaymentDto applySATRounding(PaymentDto paymentDto, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		// Aplicar redondeo SAT a orderTotal
		if (paymentDto.getOrderTotal() != null) {
			BigDecimal roundedOrderTotal = DecimalPrecisionUtils.roundToTwoDecimals(paymentDto.getOrderTotal());
			paymentDto.setOrderTotal(roundedOrderTotal);
			LOG.info(String.format("%s orderTotal redondeado: %s", idOperation, roundedOrderTotal));
		}
		
		// Aplicar redondeo SAT a pendingPayment
		if (paymentDto.getPendingPayment() != null) {
			BigDecimal roundedPendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(paymentDto.getPendingPayment());
			paymentDto.setPendingPayment(roundedPendingPayment);
			LOG.info(String.format("%s pendingPayment redondeado: %s", idOperation, roundedPendingPayment));
		}
		
		LOG.info(String.format("%s SUCCESS: PaymentDto redondeado según SAT", idOperation));
		return paymentDto;
	}

	/**
	 * Endpoint para la recuperación de los datos operativos de las ordenes de venta
	 * para el proceso de cobro
	 * 
	 * @param genericSerchParamsOrderDto objeto con los parametros de búsqueda
	 * @param companyCode                código de compañía
	 * @param branchCode                 código de sucursal
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 * 
	 */
	@PostMapping("/order-status/{companyCode}/{branchCode}")
	public ResponseEntity<?> getOrderByCodeAndType(@RequestBody GenericSerchParamsOrderDto genericSerchParamsOrderDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getOrderByCodeAndType() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ genericSerchParamsOrderDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, genericSerchParamsOrderDto.toString(), branchCode, companyCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, companyCode, idOperation);
		ResponseModel responseModel = paymentServicePort.getOrderByCompanyCodeAndParams(paymentCustomParams,
				genericSerchParamsOrderDto, method.getCode());

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para el registro de los cobros del sistema
	 * 
	 * @param paymentDto  objeto con los datos operativos para el alta del cobro con
	 *                    sus formnas de pago
	 * @param companyCode código de compañia
	 * @param branchCode  código de suucursal del usuario logeado
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createPayment(@RequestBody PaymentDto paymentDto, @PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createPayment() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ paymentDto: %s , branchCode: %s , companyCode: %s ] ", idOperation,
				paymentDto.toString(), branchCode, companyCode));

		// Validar precisión decimal del PaymentDto
		validatePaymentPrecision(paymentDto, idOperation);
		
		// Aplicar redondeo SAT
		paymentDto = applySATRounding(paymentDto, idOperation);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		// Validación: Debe incluir al menos una forma de pago
		boolean hasPayments = false;
		int totalPaymentCount = 0;

		if (paymentDto.getPaymentCashList() != null && !paymentDto.getPaymentCashList().isEmpty()) {
			hasPayments = true;
			totalPaymentCount += paymentDto.getPaymentCashList().size();
		}
		if (paymentDto.getCreditCardPaymentList() != null && !paymentDto.getCreditCardPaymentList().isEmpty()) {
			hasPayments = true;
			totalPaymentCount += paymentDto.getCreditCardPaymentList().size();
		}
		if (paymentDto.getTransferPaymentList() != null && !paymentDto.getTransferPaymentList().isEmpty()) {
			hasPayments = true;
			totalPaymentCount += paymentDto.getTransferPaymentList().size();
		}
		if (paymentDto.getCheckPaymentList() != null && !paymentDto.getCheckPaymentList().isEmpty()) {
			hasPayments = true;
			totalPaymentCount += paymentDto.getCheckPaymentList().size();
		}
		if (paymentDto.getCreditPaymentList() != null && !paymentDto.getCreditPaymentList().isEmpty()) {
			hasPayments = true;
			totalPaymentCount += paymentDto.getCreditPaymentList().size();
		}
		if (paymentDto.getCreditNotePaymentList() != null && !paymentDto.getCreditNotePaymentList().isEmpty()) {
			hasPayments = true;
			totalPaymentCount += paymentDto.getCreditNotePaymentList().size();
		}

		if (!hasPayments || totalPaymentCount == 0) {
			LOG.error(String.format("%s ERROR: No se encontraron formas de pago en el PaymentDto", idOperation));
			throw new ValidationError("Debe incluir al menos una forma de pago para realizar el cobro.");
		}

		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, companyCode, idOperation);
		ResponseModel responseModel = paymentServicePort.createPayment(paymentCustomParams, method.getCode(),
				paymentDto);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PostMapping("/send-to-queue")
	public ResponseEntity<?> sendOrdersToQueue() {
		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, "FCAL", idOperation);
		paymentServicePort.saveAllOrders(paymentCustomParams);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PutMapping("/cancel/{companyCode}/{branchCode}")
	public ResponseEntity<?> cancelPayment(@RequestBody GenericSerchParamsOrderDto genericSerchParamsOrderDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT cancelPayment() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ genericSerchParamsOrderDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, genericSerchParamsOrderDto.toString(), branchCode, companyCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, companyCode, idOperation);
		ResponseModel responseModel = paymentServicePort.cancelPaymentByCompanyCodeAndParams(paymentCustomParams,
				method.getCode(), genericSerchParamsOrderDto);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la obtención de ticket de cobro para todas las órdenes de venta
	 * que posean un registro de cobro
	 * 
	 * @param orderNumber número de orden de venta
	 * @param orderCode   código del tipo de venta
	 * @param companyCode código de compañía
	 * @param branchCode  código de suucursal del usuario logeado
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> createPaymentTicket(@PathVariable BigDecimal orderNumber, @PathVariable String orderCode,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createPaymentTicket() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderNumber: %s , orderCode: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, orderNumber.toString(), orderCode, branchCode, companyCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, companyCode, idOperation);
		ResponseModel responseModel = paymentServicePort.createPaymentTicketByOrderCodeAndType(paymentCustomParams,
				method.getCode(), orderNumber, orderCode);

		byte[] reporte = (byte[]) responseModel.getData();

		ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
				.filename(orderCode + "-" + orderNumber + ".pdf").build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);

		return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
				.headers(headers).body(new ByteArrayResource(reporte));
	}

	/**
	 * EndPoint para la obtención de reimpresión de ticket de cobro para todas las
	 * órdenes de venta que posean un registro de cobro
	 * 
	 * @param orderNumber número de orden de venta
	 * @param orderCode   código del tipo de venta
	 * @param companyCode código de compañía
	 * @param branchCode  código de suucursal del usuario logeado
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/ticket-reprint/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> reprintPaymentTicket(@PathVariable BigDecimal orderNumber, @PathVariable String orderCode,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createPaymentTicket() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderNumber: %s , orderCode: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, orderNumber.toString(), orderCode, branchCode, companyCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, companyCode, idOperation);
		ResponseModel responseModel = paymentServicePort.createPaymentTicketByOrderCodeAndType(paymentCustomParams,
				method.getCode(), orderNumber, orderCode);

		byte[] reporte = (byte[]) responseModel.getData();

		ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
				.filename(orderCode + "-" + orderNumber + ".pdf").build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);

		return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
				.headers(headers).body(new ByteArrayResource(reporte));
	}

	/**
	 * EndPoint para la búsqueda de ordenes cobradas en el sistema
	 * 
	 * @param searchParamsPayment objeto con datos generales de búsqueda
	 * @param companyCode         código de compañía
	 * @param branchCode          código de sucursal
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@PostMapping("/search-summary/{companyCode}/{branchCode}")
	public ResponseEntity<?> searchPaidOrderSummaryByCompanyCode(
			@RequestBody GenericSearchPaymentDto searchParamsPayment, @PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getOrderByCodeAndType() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ searchParamsPayment: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, searchParamsPayment.toString(), branchCode, companyCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, companyCode, idOperation);

		ResponseModel responseModel = paymentServicePort.searchPaidOrderSummaryByCompanyCode(paymentCustomParams,
				searchParamsPayment, method.getCode());

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la búsqueda del detalle de ordenes cobradas en el sistema
	 * 
	 * @param searchParamsPayment objeto con datos generales de búsqueda
	 * @param companyCode         código de compañía
	 * @param branchCode          código de sucursal
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@PostMapping("/search-detail/{companyCode}/{branchCode}")
	public ResponseEntity<?> searchPaidOrderDetailByCompanyCode(
			@RequestBody GenericSearchPaymentDto searchParamsPayment, @PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT searchPaidOrderDetailByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ searchParamsPayment: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, searchParamsPayment.toString(), branchCode, companyCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, companyCode, idOperation);
		ResponseModel responseModel = paymentServicePort.searchPaidOrderDetailByCompanyCode(paymentCustomParams,
				searchParamsPayment, method.getCode());

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PostMapping("/approve/{companyCode}/{branchCode}")
	public ResponseEntity<?> aprovePaymentByCompanyCode(@RequestBody AuthorizationParmasDto authorizationParmasDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT aprovePaymentByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ authorizationParmasDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, authorizationParmasDto.toString(), branchCode, companyCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, creditNotePersistencePort, companyPersistencePort,
				creditCardConfigurationPersistencePort, bdsServicePort, companyCode, idOperation);

		ResponseModel response = paymentServicePort.approvalPaymentByParamsAndCompanyCode(paymentCustomParams,
				authorizationParmasDto, method.getCode());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
