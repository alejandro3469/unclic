package mx.com.endtoend.infrastructure.services.jde.payments.carredana.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.infrastructure.warehouse.common.entities.F0006;
import mx.com.endtoend.infrastructure.warehouse.carredana.repositories.F0006CarredanaRepository;
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
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F41021FCarRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.orders.carredana.business.OrderFCarredanaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.common.repository.GenericPaymentJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.converters.OrderFCaredanaJedConverter;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities.F47011;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities.F47012;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities.F4706;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities.F4714;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository.F42119FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository.F4211FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository.F47011FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository.F47012FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository.F4706FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository.F4714FCarRepository;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;
// TODO: Refactor de jdeRepositories
@Service
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class PaymentFCarredanaJdeRepository implements GenericPaymentJdeRepository {

	@Autowired
	private OrderFCaredanaJedConverter orderJDEConverter;

	@Autowired
	private OrderFCarredanaJdeRepository orderCalzadaJdeRepository;

	@Autowired
	private F0006CarredanaRepository f0006Repository;

	@Autowired
	private F47011FCarRepository f47011Repository;

	@Autowired
	private F47012FCarRepository f47012Repository;

	@Autowired
	private F4706FCarRepository f4706Repository;

	@Autowired
	private F4714FCarRepository f4714Repository;

	@Autowired
	private F41021FCarRepository f41021Repository;

	@Autowired
	private F4211FCarRepository f4211Repository;

	@Autowired
	private F42119FCarRepository f42119Repository;

	@Autowired(required = false)
	private RabbitTemplate rabbitTemplate;

	private DateUtil dateUtil = new DateUtil();

	@Value("${app.carredana.jde.batchFolio.generator.url}")
	String urlbatchFolio;

	@Value("${app.carredana.jde.consecutive.generator.url}")
	String urlNextOrderNumber;
	
	@Value("${app.carredana.jde.company.code}")
	String companyCode;

	@Value("${app.carredana.queue.jde.order.name}")
	private String queueCreateOrder;

	@Value("${app.carredana.queue.jde.credit.note.name}")
	private String queueCreateCreditNote;

	@Value("${app.carredana.queue.jde.invoice.name}")
	private String queueCreateInvoiceRecord;

	private final Logger LOG = LoggerFactory.getLogger(PaymentFCarredanaJdeRepository.class);

	/**
	 * Valida la precisión decimal de PaymentOrderJDE antes de enviar a RabbitMQ
	 * @param paymentOrderJDE objeto a validar
	 * @throws IllegalArgumentException si la validación falla
	 */
	private void validatePaymentOrderPrecision(PaymentOrderJDE paymentOrderJDE) {
		if (paymentOrderJDE == null) {
			throw new IllegalArgumentException("PaymentOrderJDE cannot be null");
		}
		
		// Validar OrderTotal si existe
		if (paymentOrderJDE.getOrder().getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal orderTotal = paymentOrderJDE.getOrder().getOrderTotal();
			if (!PrecisionValidator.isValidMonetaryRange(orderTotal)) {
				throw new IllegalArgumentException("OrderTotal out of SAT range: " + orderTotal);
			}
			if (!PrecisionValidator.isValidScale(orderTotal, 2)) {
				throw new IllegalArgumentException("OrderTotal must have exactly 2 decimal places: " + orderTotal);
			}
		}
		
		// Validar SubTotal si existe
		if (paymentOrderJDE.getOrder().getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal subTotal = paymentOrderJDE.getOrder().getSubTotal();
			if (!PrecisionValidator.isValidMonetaryRange(subTotal)) {
				throw new IllegalArgumentException("SubTotal out of SAT range: " + subTotal);
			}
			if (!PrecisionValidator.isValidScale(subTotal, 2)) {
				throw new IllegalArgumentException("SubTotal must have exactly 2 decimal places: " + subTotal);
			}
		}
		
		// Validar IVATotal si existe
		if (paymentOrderJDE.getOrder().getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal ivaTotal = paymentOrderJDE.getOrder().getIvaTotal();
			if (!PrecisionValidator.isValidMonetaryRange(ivaTotal)) {
				throw new IllegalArgumentException("IVATotal out of SAT range: " + ivaTotal);
			}
			if (!PrecisionValidator.isValidScale(ivaTotal, 2)) {
				throw new IllegalArgumentException("IVATotal must have exactly 2 decimal places: " + ivaTotal);
			}
		}
		
		LOG.debug("PaymentOrderJDE precision validation passed");
	}

	/**
	 * Aplica redondeo SAT a los campos monetarios de PaymentOrderJDE
	 * @param paymentOrderJDE objeto a procesar
	 * @return objeto con redondeo SAT aplicado
	 */
	private PaymentOrderJDE applySATRounding(PaymentOrderJDE paymentOrderJDE) {
		if (paymentOrderJDE == null) {
			return paymentOrderJDE;
		}
		
		// Aplicar redondeo SAT a OrderTotal
		if (paymentOrderJDE.getOrder().getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedTotal = DecimalPrecisionUtils.roundToTwoDecimals(paymentOrderJDE.getOrder().getOrderTotal());
			paymentOrderJDE.getOrder().setOrderTotal(roundedTotal);
		}
		
		// Aplicar redondeo SAT a SubTotal
		if (paymentOrderJDE.getOrder().getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedSubTotal = DecimalPrecisionUtils.roundToTwoDecimals(paymentOrderJDE.getOrder().getSubTotal());
			paymentOrderJDE.getOrder().setSubTotal(roundedSubTotal);
		}
		
		// Aplicar redondeo SAT a IVATotal
		if (paymentOrderJDE.getOrder().getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedIVATotal = DecimalPrecisionUtils.roundToTwoDecimals(paymentOrderJDE.getOrder().getIvaTotal());
			paymentOrderJDE.getOrder().setIvaTotal(roundedIVATotal);
		}
		
		LOG.debug("SAT rounding applied to PaymentOrderJDE");
		return paymentOrderJDE;
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
			LOG.info(String.format("%s INIT getBathcFolioByExternalServiceByNNSY()", idOperation));
			LOG.info(String.format("%s PARAMS[ nnsy: %s ] ", idOperation, nnsy));

			RestTemplate restTemplate = new RestTemplate();
			String urlbase = urlbatchFolio;
			urlbase = urlbase + companyCode + "/" + nnsy;
			ResponseEntity<BigDecimal> response = restTemplate.getForEntity(urlbase, BigDecimal.class);
			BigDecimal batchFolio = response.getBody();
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
			// Validar precisión decimal antes de enviar
			validatePaymentOrderPrecision(paymentOrderJDE);
			
			// Aplicar redondeo SAT si es necesario
			paymentOrderJDE = applySATRounding(paymentOrderJDE);
			
			if (rabbitTemplate == null) {
				LOG.warn("RabbitTemplate is not available. Order will not be sent to RabbitMQ queue.");
				return;
			}
			LOG.info(String.format("Sending order to RabbitMQ with precision validation: OrderTotal=%s", 
					paymentOrderJDE.getOrder().getOrderTotal()));
			
			rabbitTemplate.convertAndSend(queueCreateOrder, paymentOrderJDE);
			
			LOG.info("Order successfully sent to RabbitMQ queue: " + queueCreateOrder);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN sendOrderToSave() : Exception: %s", e.getMessage()));
		}
	}

	@Override
	public BigDecimal getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(String orderType,
			String companyNumber, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber()",
					idOperation));
			LOG.info(String.format("%s PARAMS[ orderType: %s , companyNumber: %s ] ", idOperation, orderType,
					companyNumber));
			RestTemplate restTemplate = new RestTemplate();
			String urlbase = urlNextOrderNumber;
			urlbase = urlbase + companyNumber + "/" + companyCode + "/" + orderType;
			ResponseEntity<BigDecimal> response = restTemplate.getForEntity(urlbase, BigDecimal.class);
			BigDecimal ordeNumber = response.getBody();
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
			List<InvoiceRecordDto> invoiceRecordList = new ArrayList<>();
			for (OrderDetailDto orderDetailDto : orderDetailList) {
				InvoiceRecordDto invoiceRecord = new InvoiceRecordDto();
				List<F47012> f47012SearchList = f47012Repository.findAllByKooAndDocoAndDctoAndItm(
						orderDto.getCompanyNumber(), orderDto.getOrderNumber().longValue(), orderDto.getOrderCode(),
						orderDetailDto.getArticleNumber().longValue());
				if (f47012SearchList.size() > 0) {
					Double quantity = 0.0;
					for (F47012 f47012 : f47012SearchList) {
						quantity += (f47012.getSzsoqs() / 100);
					}
					invoiceRecord = invoiceRecord.generateExistsRecords(orderDetailDto.getArticleCode(), BigDecimal.valueOf(quantity));
				} else {
					invoiceRecord = invoiceRecord.generateEmptyRecords(orderDetailDto.getArticleCode());
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
			// Validar precisión decimal antes de enviar
			validatePaymentOrderPrecision(paymentOrderJDE);
			
			// Aplicar redondeo SAT si es necesario
			paymentOrderJDE = applySATRounding(paymentOrderJDE);
			
			if (rabbitTemplate == null) {
				LOG.warn("RabbitTemplate is not available. Credit note will not be sent to RabbitMQ queue.");
				return;
			}
			LOG.info(String.format("Sending credit note to RabbitMQ with precision validation: OrderTotal=%s", 
					paymentOrderJDE.getOrder().getOrderTotal()));
			
			rabbitTemplate.convertAndSend(queueCreateCreditNote, paymentOrderJDE);
			
			LOG.info("Credit note successfully sent to RabbitMQ queue: " + queueCreateCreditNote);
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
			// Validar precisión decimal antes de enviar
			validatePaymentOrderPrecision(paymentOrderJDE);
			
			// Aplicar redondeo SAT si es necesario
			paymentOrderJDE = applySATRounding(paymentOrderJDE);
			
			if (rabbitTemplate == null) {
				LOG.warn("RabbitTemplate is not available. Invoice will not be sent to RabbitMQ queue.");
				return;
			}
			LOG.info(String.format("Sending invoice to RabbitMQ with precision validation: OrderTotal=%s", 
					paymentOrderJDE.getOrder().getOrderTotal()));
			
			rabbitTemplate.convertAndSend(queueCreateInvoiceRecord, paymentOrderJDE);
			
			LOG.info("Invoice successfully sent to RabbitMQ queue: " + queueCreateInvoiceRecord);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN sendOrderToSaveInvoice() : Exception: %s", e.getMessage()));
		}
	}

	@Override
	public InvoiceReferenceDto getInvoiceRecordByBranchCodeAndCompanyCode(String branchCode, String companyNumber,
																		  String idOperation) {
		try {
			LOG.info(String.format("%s INIT getInvoiceRecordByBranchCodeAndCompanyCode()", idOperation));
			F0006 f0006 = (F0006) f0006Repository.findByMcu(branchCode.trim());
			String dctoJde = f0006.getMcrp03();
			BigDecimal docoJde = this.getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(dctoJde,
					companyNumber, idOperation);
			InvoiceReferenceDto invoiceRecordDto = new InvoiceReferenceDto(docoJde, dctoJde);
			return invoiceRecordDto;
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN getInvoiceRecordByBranchCodeAndCompanyCode() : Exception: %s",
					e.getMessage()));
			return null;
		}
	}
}