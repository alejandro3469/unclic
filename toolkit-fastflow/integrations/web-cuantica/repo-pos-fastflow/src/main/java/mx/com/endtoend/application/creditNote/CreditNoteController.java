package mx.com.endtoend.application.creditNote;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteCustomParams;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.ports.CreditNoteServicePort;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

/**
 * Controlador para la gestion de notas de crédito en el sistema
 * 
 * @author ddcasas
 *
 */

@RestController
@RequestMapping("/credit-note")
public class CreditNoteController {

	@Autowired
	private OrderConfigurationPersistencePort orderConfigurationPersistencePort;

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private PaymentJDEServicePort paymentJDEServicePort;

	@Autowired
	private CreditNoteServicePort creditNoteServicePort;

	@Autowired
	private OrderPersistencePort orderPersistencePort;


    @Autowired
	private PaymentPersistencePort paymentPersistencePort;

	private String idOperation;

	private String module = "CREDIT_NOTES";

	private final Logger LOG = LoggerFactory.getLogger(CreditNoteController.class);

	private final ConcurrentMap<String, Boolean> executionMap = new ConcurrentHashMap<>();

	/**
	 * Valida la precisión decimal de OrderDto según estándares SAT
	 */
	private void validateOrderPrecision(OrderDto orderDto, String idOperation) {
		LOG.info(String.format("%s INIT validateOrderPrecision()", idOperation));
		
		// Validar orderTotal
		if (orderDto.getOrderTotal() != null && orderDto.getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(orderDto.getOrderTotal())) {
				LOG.error(String.format("%s ERROR: orderTotal fuera de rango válido: %s", idOperation, orderDto.getOrderTotal()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderDto.getOrderTotal(), 2)) {
				LOG.error(String.format("%s ERROR: orderTotal con escala incorrecta: %s", idOperation, orderDto.getOrderTotal()));
				throw new GlobalError();
			}
		}
		
		// Validar pendingPayment
		if (orderDto.getPendingPayment() != null && orderDto.getPendingPayment().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(orderDto.getPendingPayment())) {
				LOG.error(String.format("%s ERROR: pendingPayment fuera de rango válido: %s", idOperation, orderDto.getPendingPayment()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderDto.getPendingPayment(), 2)) {
				LOG.error(String.format("%s ERROR: pendingPayment con escala incorrecta: %s", idOperation, orderDto.getPendingPayment()));
				throw new GlobalError();
			}
		}
		
		// Validar subTotal
		if (orderDto.getSubTotal() != null && orderDto.getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(orderDto.getSubTotal())) {
				LOG.error(String.format("%s ERROR: subTotal fuera de rango válido: %s", idOperation, orderDto.getSubTotal()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderDto.getSubTotal(), 2)) {
				LOG.error(String.format("%s ERROR: subTotal con escala incorrecta: %s", idOperation, orderDto.getSubTotal()));
				throw new GlobalError();
			}
		}
		
		// Validar ivaTotal
		if (orderDto.getIvaTotal() != null && orderDto.getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(orderDto.getIvaTotal())) {
				LOG.error(String.format("%s ERROR: ivaTotal fuera de rango válido: %s", idOperation, orderDto.getIvaTotal()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderDto.getIvaTotal(), 2)) {
				LOG.error(String.format("%s ERROR: ivaTotal con escala incorrecta: %s", idOperation, orderDto.getIvaTotal()));
				throw new GlobalError();
			}
		}
		
		LOG.info(String.format("%s SUCCESS: OrderDto validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a OrderDto
	 */
	private OrderDto applySATRounding(OrderDto orderDto, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		// Aplicar redondeo SAT a orderTotal
		if (orderDto.getOrderTotal() != null && orderDto.getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedOrderTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getOrderTotal());
			orderDto.setOrderTotal(roundedOrderTotal);
			LOG.info(String.format("%s orderTotal redondeado: %s", idOperation, roundedOrderTotal));
		}
		
		// Aplicar redondeo SAT a pendingPayment
		if (orderDto.getPendingPayment() != null && orderDto.getPendingPayment().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedPendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getPendingPayment());
			orderDto.setPendingPayment(roundedPendingPayment);
			LOG.info(String.format("%s pendingPayment redondeado: %s", idOperation, roundedPendingPayment));
		}
		
		// Aplicar redondeo SAT a subTotal
		if (orderDto.getSubTotal() != null && orderDto.getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedSubTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getSubTotal());
			orderDto.setSubTotal(roundedSubTotal);
			LOG.info(String.format("%s subTotal redondeado: %s", idOperation, roundedSubTotal));
		}
		
		// Aplicar redondeo SAT a ivaTotal
		if (orderDto.getIvaTotal() != null && orderDto.getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedIvaTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getIvaTotal());
			orderDto.setIvaTotal(roundedIvaTotal);
			LOG.info(String.format("%s ivaTotal redondeado: %s", idOperation, roundedIvaTotal));
		}
		
		LOG.info(String.format("%s SUCCESS: OrderDto redondeado según SAT", idOperation));
		return orderDto;
	}

	@PostMapping("/create/{companyCode}/{branchCode}")
    public ResponseEntity<?> creteCreditNoteByOrderAndCompanyCode(@RequestBody OrderDto orderDto,
                                                                  @PathVariable String companyCode, @PathVariable String branchCode) {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        String orderNumber = String.valueOf(orderDto.getOrderNumber());
        String key = orderNumber + "-" + username;

        if (executionMap.putIfAbsent(key, Boolean.TRUE) != null) {
            return new ResponseEntity<>("Credit note creation is already in progress for this order number in your session.", HttpStatus.CONFLICT);
        }

        try {
            String idOperation = generateIdOperation(companyCode, branchCode, module);
            LOG.info(String.format("%s INIT creteCreditNoteByOrderAndCompanyCode() ", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderDto: %s , branchCode: %s , companyCode: %s ] ", idOperation,
                    orderDto.toString(), branchCode, companyCode));

            // Validar precisión decimal del OrderDto
            validateOrderPrecision(orderDto, idOperation);

            // Aplicar redondeo SAT
            orderDto = applySATRounding(orderDto, idOperation);

            CreditNoteCustomParams customParams = new CreditNoteCustomParams(paymentPersistencePort, orderPersistencePort,
                    paymentJDEServicePort, orderConfigurationPersistencePort, orderDto, username);

            MethodDto method = (MethodDto) companyServicePort
                    .findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
            if (method == null) {
                LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
                throw new GlobalError();
            }

            // Validación: La orden debe contener al menos un artículo
            if (orderDto.getOrderDetail() == null || orderDto.getOrderDetail().isEmpty()) {
                LOG.error(String.format("%s ERROR: orderDetail está vacío o es null", idOperation));
                throw new ValidationError("La orden debe contener al menos un artículo para crear la nota de crédito.");
            }

            ResponseModel responseModel = creditNoteServicePort.creteCreditNoteByOrder(customParams, method.getCode(),
                    companyCode, idOperation);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        } finally {
            executionMap.remove(key);
        }
    }


	@PostMapping("/send-to-queue/{orderNumber}/{orderCode}/{folio}")
	public ResponseEntity<?> resedOrdersToQueue(@PathVariable String orderNumber, @PathVariable String orderCode, @PathVariable BigDecimal folio) {

		idOperation = generateIdOperation("FCAL", "0", module);
		LOG.info(String.format("%s INIT resedOrdersToQueue() ", idOperation));

		OrderDto order = creditNoteServicePort.getOrder(orderNumber, orderCode, idOperation);


		LOG.info(String.format("%s PARAMS: [ orderDto: %s , branchCode: %s , companyCode: %s ] ", idOperation,
				"orderDto.toString()", "0", "FCAL"));

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();


		CreditNoteCustomParams customParams = new CreditNoteCustomParams(paymentPersistencePort, orderPersistencePort,
				paymentJDEServicePort, orderConfigurationPersistencePort, order, username);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule("FCAL", module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, "FCAL"));
			throw new GlobalError();
		}

		ResponseModel responseModel = creditNoteServicePort.resendNotesToQueue(customParams, method.getCode(), "FCAL", idOperation, folio);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping("/aprove/{companyCode}/{branchCode}")
	public ResponseEntity<?> aproveCreditNoteByParamsAndCompanyCode(
			@RequestBody AuthorizationParmasDto authorizationParmasDto, @PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT creteCreditNoteByOrderAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ authorizationParmasDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, authorizationParmasDto.toString(), branchCode, companyCode));

		CreditNoteCustomParams customParams = new CreditNoteCustomParams(paymentJDEServicePort, orderPersistencePort,
				orderConfigurationPersistencePort, paymentPersistencePort);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = creditNoteServicePort.aproveCreditNoteByParamsAndCompanyCode(customParams,
				authorizationParmasDto, method.getCode(), companyCode, branchCode);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/search-order/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> searchOrderByNumberAndCodeAndCompanyCode(@PathVariable BigDecimal orderNumber,
			@PathVariable String orderCode, @PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT searchOrderByNumberAndCodeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderNumber: %s , orderCode: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, orderNumber.toString(), orderCode, branchCode, companyCode));

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();

		CreditNoteCustomParams customParams = new CreditNoteCustomParams(paymentPersistencePort, orderPersistencePort,
				paymentJDEServicePort, orderConfigurationPersistencePort, orderNumber, orderCode, username);
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = creditNoteServicePort.searchOrderSummaryByNumberAndCode(customParams,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PostMapping("/search/{companyCode}/{branchCode}")
	public ResponseEntity<?> searchCreditNoteByParams(@RequestBody CreditNoteSearchParamsDto noteSearchParamsDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT searchCreditNoteByParams() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ noteSearchParamsDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, noteSearchParamsDto.toString(), branchCode, companyCode));

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();

		CreditNoteCustomParams customParams = new CreditNoteCustomParams(paymentPersistencePort, orderPersistencePort,
				paymentJDEServicePort, orderConfigurationPersistencePort, null, null, username);

		noteSearchParamsDto.setBranchCode(branchCode);
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = creditNoteServicePort.searchByParamsAndCompanyCode(customParams,
				noteSearchParamsDto, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/obtain/{folio}/{creditNoteCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> obtainCreditNoteSummaryByNumberAndCodeAndCompanyCode(@PathVariable BigDecimal folio,
			@PathVariable String creditNoteCode, @PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT obtainCreditNoteSummaryByNumberAndCodeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ folio: %s , creditNoteCode: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, folio.toString(), creditNoteCode, branchCode, companyCode));

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();

		CreditNoteCustomParams customParams = new CreditNoteCustomParams(orderPersistencePort, paymentJDEServicePort,
				orderConfigurationPersistencePort, null, null, folio, creditNoteCode, username);
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = creditNoteServicePort.obtainCreditNoteSummaryByNumberAndCode(customParams,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/{folio}/{creditNoteCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> findCreditNoteHeaderDetailByCompanyCode(@PathVariable BigDecimal folio,
			@PathVariable String creditNoteCode, @PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT obtainCreditNoteSummaryByNumberAndCodeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ folio: %s , creditNoteCode: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, folio.toString(), creditNoteCode, branchCode, companyCode));
		CreditNoteCustomParams customParams = new CreditNoteCustomParams(paymentJDEServicePort, orderPersistencePort,
				orderConfigurationPersistencePort, paymentPersistencePort);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = creditNoteServicePort.viewDetailByCodeAndFolioAndCompanyCode(customParams, folio,
				creditNoteCode, method.getCode(), companyCode, idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	@PostMapping("/ticket/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateTicketByNumberAndCodeAndCompanyCode(
			@RequestBody CreditNoteSearchParamsDto creditNoteSearchParamsDto, @PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT generateTicketByNumberAndCodeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ creditNoteSearchParamsDto: %s , branchCode: %s , companyCode: %s ] ",
				idOperation, creditNoteSearchParamsDto.toString(), branchCode, companyCode));

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();

		CreditNoteCustomParams customParams = new CreditNoteCustomParams(paymentPersistencePort, orderPersistencePort,
				paymentJDEServicePort, orderConfigurationPersistencePort, null, null, username);
		creditNoteSearchParamsDto.setBranchCode(branchCode);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = creditNoteServicePort.generateTicketByFolioAndCompanyCode(customParams,
				creditNoteSearchParamsDto, method.getCode(), companyCode, idOperation);

		byte[] reporte = (byte[]) responseModel.getData();

		ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
				.filename(creditNoteSearchParamsDto.getCreditNoteCode() + "-"
						+ creditNoteSearchParamsDto.getFolio().toString() + ".pdf")
				.build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);

		return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
				.headers(headers).body(new ByteArrayResource(reporte));
	}
}
