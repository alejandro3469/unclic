package mx.com.endtoend.application.recharges;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import java.math.BigDecimal;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.recharges.dto.CustomRechargeSaleParams;
import mx.com.endtoend.domain.recharges.dto.RechargeSaleDto;
import mx.com.endtoend.domain.recharges.ports.RechargeSaleServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

@RestController
@RequestMapping("/sale-recharge")
public class RechargeSaleController {

	@Autowired
	private ClientPersistencePort clientPersistencePort;

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private RechargeSaleServicePort rechargeSaleServicePort;

	@Autowired
	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	@Autowired
	private PaymentJDEServicePort paymentJDEServicePort;

	@Autowired
	private OrderJdeServicePort orderJdeServicePort;

	@Autowired
	private PaymentPersistencePort paymentPersistencePort;

	@Autowired
	private OrderPersistencePort orderPersistencePort;

	@Autowired
	private CompanyPersistencePort companyPersistencePort;

	@Autowired
	private CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort;

	private String module = "RECHARGE";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(RechargeSaleController.class);

	/**
	 * Valida la precisión decimal de un RechargeSaleDto según estándares SAT
	 * 
	 * @param rechargeSaleDto DTO de recarga a validar
	 * @param idOperation ID de operación para logging
	 */
	private void validateRechargeSalePrecision(RechargeSaleDto rechargeSaleDto, String idOperation) {
		LOG.info(String.format("%s INIT validateRechargeSalePrecision()", idOperation));
		
		// Validar PaymentSummaryDto si existe
		if (rechargeSaleDto.getPayments() != null) {
			// Validar PaymentCashDto
			if (rechargeSaleDto.getPayments().getPaymentCashList() != null) {
				for (var paymentCash : rechargeSaleDto.getPayments().getPaymentCashList()) {
					if (paymentCash.getAmountApplied() != null) {
						if (!PrecisionValidator.isValidMonetaryRange(paymentCash.getAmountApplied())) {
							LOG.error(String.format("%s ERROR: PaymentCash amountApplied fuera de rango válido: %s", idOperation, paymentCash.getAmountApplied()));
							throw new ValidationError("El monto aplicado está fuera del rango válido");
						}
						if (!PrecisionValidator.isValidScale(paymentCash.getAmountApplied(), 2)) {
							LOG.error(String.format("%s ERROR: PaymentCash amountApplied con escala incorrecta: %s", idOperation, paymentCash.getAmountApplied()));
							throw new ValidationError("El monto aplicado debe tener exactamente 2 decimales");
						}
					}
				}
			}
			
			// Validar CreditCardPaymentDto
			if (rechargeSaleDto.getPayments().getCreditCardPaymentList() != null) {
				for (var creditCardPayment : rechargeSaleDto.getPayments().getCreditCardPaymentList()) {
					if (creditCardPayment.getAmountApplied() != null) {
						if (!PrecisionValidator.isValidMonetaryRange(creditCardPayment.getAmountApplied())) {
							LOG.error(String.format("%s ERROR: CreditCard amountApplied fuera de rango válido: %s", idOperation, creditCardPayment.getAmountApplied()));
							throw new ValidationError("El monto aplicado está fuera del rango válido");
						}
						if (!PrecisionValidator.isValidScale(creditCardPayment.getAmountApplied(), 2)) {
							LOG.error(String.format("%s ERROR: CreditCard amountApplied con escala incorrecta: %s", idOperation, creditCardPayment.getAmountApplied()));
							throw new ValidationError("El monto aplicado debe tener exactamente 2 decimales");
						}
					}
				}
			}
		}
		
		LOG.info(String.format("%s SUCCESS: RechargeSaleDto validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a un RechargeSaleDto
	 * 
	 * @param rechargeSaleDto DTO de recarga a redondear
	 * @param idOperation ID de operación para logging
	 * @return RechargeSaleDto con valores redondeados según SAT
	 */
	private RechargeSaleDto applySATRounding(RechargeSaleDto rechargeSaleDto, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		// Aplicar redondeo SAT a PaymentSummaryDto si existe
		if (rechargeSaleDto.getPayments() != null) {
			// Redondear PaymentCashDto
			if (rechargeSaleDto.getPayments().getPaymentCashList() != null) {
				for (var paymentCash : rechargeSaleDto.getPayments().getPaymentCashList()) {
					if (paymentCash.getAmountApplied() != null) {
						BigDecimal roundedAmount = DecimalPrecisionUtils.roundToTwoDecimals(paymentCash.getAmountApplied());
						paymentCash.setAmountApplied(roundedAmount);
						LOG.info(String.format("%s PaymentCash amountApplied redondeado: %s", idOperation, roundedAmount));
					}
				}
			}
			
			// Redondear CreditCardPaymentDto
			if (rechargeSaleDto.getPayments().getCreditCardPaymentList() != null) {
				for (var creditCardPayment : rechargeSaleDto.getPayments().getCreditCardPaymentList()) {
					if (creditCardPayment.getAmountApplied() != null) {
						BigDecimal roundedAmount = DecimalPrecisionUtils.roundToTwoDecimals(creditCardPayment.getAmountApplied());
						creditCardPayment.setAmountApplied(roundedAmount);
						LOG.info(String.format("%s CreditCard amountApplied redondeado: %s", idOperation, roundedAmount));
					}
				}
			}
		}
		
		LOG.info(String.format("%s SUCCESS: RechargeSaleDto redondeado según SAT", idOperation));
		return rechargeSaleDto;
	}

	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createRechargeSaleByCompanyCode(@RequestBody RechargeSaleDto rechargeSaleDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createRechargeSaleByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [rechargeSaleDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				rechargeSaleDto.toString(), companyCode, branchCode));

		// Validar precisión decimal del RechargeSaleDto
		validateRechargeSalePrecision(rechargeSaleDto, idOperation);
		
		// Aplicar redondeo SAT
		rechargeSaleDto = applySATRounding(rechargeSaleDto, idOperation);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("CONTACT YOUR ADMINISTRATOR");
		}

		CustomRechargeSaleParams customRechargeSaleParams = new CustomRechargeSaleParams(companyCode, idOperation,
				method.getCode(), accountingRecordPersistencePort, paymentJDEServicePort, orderJdeServicePort,
				paymentPersistencePort, orderPersistencePort, companyPersistencePort, clientPersistencePort,
				creditCardConfigurationPersistencePort);
		ResponseModel response = rechargeSaleServicePort.createRechargeSaleByCompanyCode(rechargeSaleDto,
				customRechargeSaleParams);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateTicketRechargeSaleByCompanyCode(@PathVariable BigDecimal orderNumber,
			@PathVariable String orderCode, @PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT generateTicketRechargeSaleByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderNumber: %s , orderCode: %s , companyCode: %s , branchCode: %s ]",
				idOperation, orderNumber.toString(), orderCode, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("CONTACT YOUR ADMINISTRATOR");
		}

		CustomRechargeSaleParams customRechargeSaleParams = new CustomRechargeSaleParams(companyCode, idOperation,
				method.getCode(), orderCode, orderNumber, accountingRecordPersistencePort, paymentJDEServicePort,
				orderJdeServicePort, paymentPersistencePort, orderPersistencePort, companyPersistencePort,
				clientPersistencePort, creditCardConfigurationPersistencePort);
		ResponseModel response = rechargeSaleServicePort.generateTicketRechargeByCompanyCode(customRechargeSaleParams);
		byte[] reporte = (byte[]) response.getData();

		ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
				.filename(orderCode + "-" + orderNumber + ".pdf").build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);

		return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
				.headers(headers).body(new ByteArrayResource(reporte));

	}

	@GetMapping("/company-phone/{companyCode}/{branchCode}")
	public ResponseEntity<?> getCompanyPhoneListByComapnyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT generateTicketRechargeSaleByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("CONTACT YOUR ADMINISTRATOR");
		}

		CustomRechargeSaleParams customRechargeSaleParams = new CustomRechargeSaleParams(companyCode, idOperation,
				method.getCode(), accountingRecordPersistencePort, paymentJDEServicePort, orderJdeServicePort,
				paymentPersistencePort, orderPersistencePort, companyPersistencePort, clientPersistencePort,
				creditCardConfigurationPersistencePort);
		ResponseModel response = rechargeSaleServicePort.getCompanyPhoneList(customRechargeSaleParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
