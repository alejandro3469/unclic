package mx.com.endtoend.infrastructure.services.jde.payments.calzada.fragua.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.endtoend.domain.creditNote.dto.InvoiceRecordDto;
import mx.com.endtoend.genericCommonsFileds.utilities.DateUtil;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.F41021Repository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.orders.calzada.business.OrderCalzadaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.common.repository.GenericPaymentJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.converters.OrderJDEConverter;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F4211;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F42119;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F47011;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F47012;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F4706;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F4714;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository.F42119Repository;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository.F4211Repository;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository.F47011Repository;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository.F47012Repository;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository.F4706Repository;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository.F4714Repository;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

@Service
@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
public class PaymentFraguaJdeRepository implements GenericPaymentJdeRepository {

	@Autowired
	private OrderJDEConverter orderJDEConverter;

	@Autowired
	private OrderCalzadaJdeRepository orderCalzadaJdeRepository;

	@Autowired
	private F47011Repository f47011Repository;

	@Autowired
	private F47012Repository f47012Repository;

	@Autowired
	private F4706Repository f4706Repository;

	@Autowired
	private F4714Repository f4714Repository;

	@Autowired
	private F41021Repository f41021Repository;

	@Autowired
	private F4211Repository f4211Repository;

	@Autowired
	private F42119Repository f42119Repository;

	@Autowired(required = false)
	private RabbitTemplate rabbitTemplate;

	private DateUtil dateUtil = new DateUtil();

	@Value("${app.calzada.jde.batchFolio.generator.url}")
	String urlbatchFolio;

	@Value("${app.fragua.queue.jde.order.name}")
	private String queueCreateOrder;

	@Value("${app.calzada.jde.consecutive.generator.url}")
	String urlNextOrderNumber;
	
	@Value("${app.calzada.jde.company.code}")
	String companyCode;

	@Value("${app.fragua.queue.jde.credit.note.name}")
	private String queueCreateCreditNote;
	
	@Value("${app.fragua.queue.jde.invoice.name}")
	private String queueCreateInvoiceRecord;

	private final Logger LOG = LoggerFactory.getLogger(PaymentFraguaJdeRepository.class);

	/**
	 * Valida la precisión decimal de datos monetarios antes de enviar a servicios externos
	 * @param amount valor monetario a validar
	 * @throws IllegalArgumentException si la validación falla
	 */
	private void validateMonetaryPrecision(java.math.BigDecimal amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount cannot be null");
		}
		
		if (!PrecisionValidator.isValidMonetaryRange(amount)) {
			throw new IllegalArgumentException("Amount out of SAT range: " + amount);
		}
		
		if (!PrecisionValidator.isValidScale(amount, 2)) {
			throw new IllegalArgumentException("Amount must have exactly 2 decimal places: " + amount);
		}
		
		LOG.debug("Monetary precision validation passed for amount: " + amount);
	}

	/**
	 * Aplica redondeo SAT a valores monetarios antes de enviar a servicios externos
	 * @param amount valor monetario a procesar
	 * @return valor con redondeo SAT aplicado
	 */
	private java.math.BigDecimal applySATRounding(java.math.BigDecimal amount) {
		if (amount == null) {
			return amount;
		}
		
		java.math.BigDecimal roundedAmount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
		LOG.debug("SAT rounding applied to amount: " + amount + " -> " + roundedAmount);
		return roundedAmount;
	}

	/**
	 * Método que alamacena los datos de las órdenes que ya han sido cobradas y
	 * actualiza la disponibilidad de los artículos
	 * 
	 * @param orderDto    datos operativos de las órdenes de venta
	 * @param idOperation identificador de traza de operación
	 * 
	 * @return valor booleano indicando el resultado de la operación, true en caso
	 *         de un proceso exitoso y false en caso contrario
	 */
	@Override
	public boolean saveOrder(OrderDto orderDto, String idOperation) {

		try {

			LOG.info(String.format("%s INIT saveOrder()", idOperation));

			BigDecimal genericClientNumber = orderCalzadaJdeRepository.getGenericClientNumberByWarehouseCode(
					orderDto.getCompanyNumber(), orderDto.getBranchCode(), idOperation);

			if (orderDto.getClient().getId() == 1) {
				LOG.info(String.format("%s IS GENERIC CLIENT, FIND AND SET GENERIC CLIENT NUMBER", idOperation));
				orderDto.getClient().setNoClient(genericClientNumber.longValue());
			}

			LOG.info(String.format("%s INIT convert OrderDto To F47011", idOperation));
			F47011 f47011 = orderJDEConverter.orderDtoToF47011(orderDto, genericClientNumber);
			LOG.info(String.format("%s F47011: ", idOperation, f47011.toString()));
			LOG.info(String.format("%s SAVE F47011", idOperation));
			f47011Repository.save(f47011);

			for (OrderDetailDto orderDetailDto : orderDto.getOrderDetail()) {

				LOG.info(String.format("%s INIT convert OrderDetailDto To F47012", idOperation));
				F47012 f47012 = orderJDEConverter.orderDetailDtoToF47012(orderDetailDto, orderDto);
				LOG.info(String.format("%s SAVE F47012", idOperation));
				f47012Repository.save(f47012);

				LOG.info(String.format("%s UPDATE STOCK", idOperation));
				this.updateItemAvailavility(orderDetailDto);
			}

			LOG.info(String.format("%s INIT convert Address To F4706", idOperation));
			F4706 f4706 = orderJDEConverter.addressDtoToF4706(orderDto);
			LOG.info(String.format("%s SAVE F4706", idOperation));
			f4706Repository.save(f4706);

			if (orderDto.getObservations() != null) {
				if (!orderDto.getObservations().isEmpty()) {
					LOG.info(String.format("%s SAVE OBSERVATIONS IN F4714", idOperation));
					F4714 f4714 = orderJDEConverter.generateObservationByOrderDto(orderDto);
					LOG.info(String.format("%s SAVE F4714", idOperation));
					f4714Repository.save(f4714);
				}
			}

			LOG.info(String.format("%s END PROCESS", idOperation));
			return true;

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN saveOrder(). EXCEPTION: %s", idOperation, e.getMessage()));
			return false;
		}

	}

	@Override
	public Long getBathcFolioByExternalServiceByNNSY(String nnsy, String idOperation) {

		try {
			LOG.info(String.format("%s INIT getBathcFolioByExternalServiceByNNSY() with precision validation", idOperation));
			LOG.info(String.format("%s PARAMS[ nnsy: %s ] ", idOperation, nnsy));

			RestTemplate restTemplate = new RestTemplate();
			String urlbase = urlbatchFolio;
			urlbase = urlbase + companyCode + "/" + nnsy;
			
			LOG.info("Validating external service request for batch folio with nnsy: " + nnsy);
			ResponseEntity<BigDecimal> response = restTemplate.getForEntity(urlbase, BigDecimal.class);
			BigDecimal batchFolio = response.getBody();
			
			if (batchFolio != null) {
				// Validar precisión del batch folio recibido
				validateMonetaryPrecision(batchFolio);
				// Aplicar redondeo SAT si es necesario
				batchFolio = applySATRounding(batchFolio);
				LOG.info("Batch folio validated and rounded: " + batchFolio);
			} else {
				LOG.warn("Batch folio response is null - external service may be unavailable");
			}
			
			LOG.info(String.format("%s RETURN BATCH-FOLIO: " + batchFolio, idOperation));
			urlbase = "";
			return batchFolio.longValue();
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getBathcFolioByExternalServiceByNNSY(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}

	}

	public void updateItemAvailavility(OrderDetailDto orderDetailDto) {

		BigDecimal ot1p = orderDetailDto.getRequestAmount().multiply(BigDecimal.valueOf(100));
		String pid = "POS";
		Long upmj = dateUtil.getCurrentJulianDate();
		String user = "POS";
		Long tday = dateUtil.getCurrentTimeJulianDate();
		BigDecimal itm = orderDetailDto.getArticleNumber();
		String mcu = orderDetailDto.getWarehouseCode().trim();

		f41021Repository.updateItemAvailabilityByItmAndMcu(ot1p, pid, upmj, user, tday, itm, mcu);
	}

	/**
	 * Método que envía los datos de las órdenes a la cola de RabbitMQ para el
	 * registro de órdenes en JDE que han sido cobradas
	 */
	@Override
	public void sendOrderToSave(PaymentOrderJDE paymentOrderJDE) {
		try {
			if (rabbitTemplate == null) {
				LOG.warn("RabbitTemplate is not available. Order will not be sent to RabbitMQ queue.");
				return;
			}
			rabbitTemplate.convertAndSend(queueCreateOrder, paymentOrderJDE);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN sendOrderToSave() : Exception: %s", e.getMessage()));
		}
	}

	@Override
	public BigDecimal getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(String orderType,
			String companyNumber, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber() with precision validation",
					idOperation));
			LOG.info(String.format("%s PARAMS[ orderType: %s , companyNumber: %s ] ", idOperation, orderType,
					companyNumber));
			
			RestTemplate restTemplate = new RestTemplate();
			String urlbase = urlNextOrderNumber;
			urlbase = urlbase + companyNumber + "/" + companyCode + "/" + orderType;
			
			LOG.info("Validating external service request for order number with orderType: " + orderType + ", companyNumber: " + companyNumber);
			ResponseEntity<BigDecimal> response = restTemplate.getForEntity(urlbase, BigDecimal.class);
			BigDecimal ordeNumber = response.getBody();
			
			if (ordeNumber != null) {
				// Validar precisión del número de orden recibido
				validateMonetaryPrecision(ordeNumber);
				// Aplicar redondeo SAT si es necesario
				ordeNumber = applySATRounding(ordeNumber);
				LOG.info("Order number validated and rounded: " + ordeNumber);
			} else {
				LOG.warn("Order number response is null - external service may be unavailable");
			}
			
			LOG.info(String.format("%s RETURN ORDER-NUMBER: " + ordeNumber, idOperation));
			urlbase = "";
			return ordeNumber;
		} catch (Exception e) {
			LOG.error(String.format(
					"%s ERROR IN getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public List<InvoiceRecordDto> searchInvoiceRecordsByOrden(OrderDto orderDto,
			OrderConfigurationDto orderConfigurationDto, String idOperation) {
		try {
			LOG.info(String.format("%s INIT searchInvoiceRecordsByOrden()", idOperation));

			List<OrderDetailDto> orderDetailList = orderDto.getOrderDetail();
			String nxtr = orderConfigurationDto.getStateOneValidCreditNote();
			String lttr = orderConfigurationDto.getStateTwoValidCreditNote();

			List<InvoiceRecordDto> invoiceRecordList = new ArrayList<>();

			for (OrderDetailDto orderDetailDto : orderDetailList) {

				InvoiceRecordDto invoiceRecord = new InvoiceRecordDto();

				List<F4211> f4211List = f4211Repository.findByNxtrAndLttrAndKcooAndDocoAndDctoAndItm(
						orderDto.getCompanyNumber(), orderDto.getOrderNumber(), orderDto.getOrderCode(),
						orderDetailDto.getArticleNumber().longValue(), nxtr, lttr);

				if (f4211List.size() > 0) {
					Double quantity = 0.0;
					for (F4211 f4211 : f4211List) {
						quantity = quantity + (f4211.getSdsoqs() / 100);
					}
					invoiceRecord = invoiceRecord.generateExistsRecords(orderDetailDto.getArticleCode(), BigDecimal.valueOf(quantity));
				} else {

					List<F42119> f42119List = f42119Repository.findByKcooAndDocoAndDctoAndItm(
							orderDto.getCompanyNumber(), orderDto.getOrderNumber(), orderDto.getOrderCode(),
							orderDetailDto.getArticleNumber().longValue());

					if (f42119List.size() > 0) {
						Double quantity = 0.0;
						for (F42119 f42119 : f42119List) {
							quantity = quantity + (f42119.getSdsoqs() / 100);
						}
						invoiceRecord = invoiceRecord.generateExistsRecords(orderDetailDto.getArticleCode(), BigDecimal.valueOf(quantity));
					} else {

						invoiceRecord = invoiceRecord.generateEmptyRecords(orderDetailDto.getArticleCode());
					}
				}
				invoiceRecordList.add(invoiceRecord);
			}

			return invoiceRecordList;

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN searchInvoiceRecordsByOrden(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public void sendCreditNoteToSaveByCompanyCoode(PaymentOrderJDE paymentOrderJDE) {
		try {
			if (rabbitTemplate == null) {
				LOG.warn("RabbitTemplate is not available. Credit note will not be sent to RabbitMQ queue.");
				return;
			}
			rabbitTemplate.convertAndSend(queueCreateCreditNote, paymentOrderJDE);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN sendCreditNoteToSaveByCompanyCoode() : Exception: %s", e.getMessage()));
		}
	}

	@Override
	public void resendNotesToQueue(PaymentOrderJDE paymentOrderJDE) {

	}

	@Override
	public void sendOrderToSaveInvoice(PaymentOrderJDE paymentOrderJDE) {
		try {
			if (rabbitTemplate == null) {
				LOG.warn("RabbitTemplate is not available. Invoice will not be sent to RabbitMQ queue.");
				return;
			}
			rabbitTemplate.convertAndSend(queueCreateInvoiceRecord, paymentOrderJDE);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN sendOrderToSaveInvoice() : Exception: %s", e.getMessage()));
		}
	}

	/**
	 * NO IMPLEMENTADO PARA ESTA COMPAÑIA
	 */
	@Override
	public InvoiceReferenceDto getInvoiceRecordByBranchCodeAndCompanyCode(String branchCode, String companyNumber,
			String idOperation) {
		// TODO Auto-generated method stub
		return null;
	}
}