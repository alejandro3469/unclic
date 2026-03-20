package mx.com.endtoend.infrastructure.services.jde.payments.common.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.infrastructure.services.jde.payments.common.factory.PaymentJdeRepositoryFactory;
import mx.com.endtoend.infrastructure.services.jde.payments.common.repository.GenericPaymentJdeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.creditNote.dto.InvoiceRecordDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

/**
 * Clase encargada de la obtención de los repositorios de las compañías
 * configuradas en el sistema para la obtención y almacenamiento de datos
 * 
 * @author ddcasas
 *
 */
@Service
public class JdePaymentService implements PaymentJDEServicePort {

	@Autowired
	private PaymentJdeRepositoryFactory paymentJdeRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(JdePaymentService.class);

	/**
	 * Valida la precisión decimal de un PaymentOrderJDE según estándares SAT
	 * 
	 * @param paymentOrderJDE objeto a validar
	 * @param idOperation ID de operación para logging
	 */
	private void validatePaymentOrderPrecision(PaymentOrderJDE paymentOrderJDE, String idOperation) {
		LOG.info(String.format("%s INIT validatePaymentOrderPrecision()", idOperation));
		
		if (paymentOrderJDE == null) {
			throw new IllegalArgumentException("PaymentOrderJDE cannot be null");
		}
		
		// Validar OrderTotal si existe
		if (paymentOrderJDE.getOrder().getOrderTotal() != null && 
			paymentOrderJDE.getOrder().getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal orderTotal = paymentOrderJDE.getOrder().getOrderTotal();
			if (!PrecisionValidator.isValidMonetaryRange(orderTotal)) {
				LOG.error(String.format("%s ERROR: OrderTotal fuera de rango válido: %s", idOperation, orderTotal));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderTotal, 2)) {
				LOG.error(String.format("%s ERROR: OrderTotal con escala incorrecta: %s", idOperation, orderTotal));
				throw new GlobalError();
			}
		}
		
		// Validar SubTotal si existe
		if (paymentOrderJDE.getOrder().getSubTotal() != null && 
			paymentOrderJDE.getOrder().getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal subTotal = paymentOrderJDE.getOrder().getSubTotal();
			if (!PrecisionValidator.isValidMonetaryRange(subTotal)) {
				LOG.error(String.format("%s ERROR: SubTotal fuera de rango válido: %s", idOperation, subTotal));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(subTotal, 2)) {
				LOG.error(String.format("%s ERROR: SubTotal con escala incorrecta: %s", idOperation, subTotal));
				throw new GlobalError();
			}
		}
		
		// Validar IVATotal si existe
		if (paymentOrderJDE.getOrder().getIvaTotal() != null && 
			paymentOrderJDE.getOrder().getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal ivaTotal = paymentOrderJDE.getOrder().getIvaTotal();
			if (!PrecisionValidator.isValidMonetaryRange(ivaTotal)) {
				LOG.error(String.format("%s ERROR: IVATotal fuera de rango válido: %s", idOperation, ivaTotal));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(ivaTotal, 2)) {
				LOG.error(String.format("%s ERROR: IVATotal con escala incorrecta: %s", idOperation, ivaTotal));
				throw new GlobalError();
			}
		}
		
		LOG.info(String.format("%s SUCCESS: PaymentOrderJDE validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a un PaymentOrderJDE
	 * 
	 * @param paymentOrderJDE objeto a redondear
	 * @param idOperation ID de operación para logging
	 * @return PaymentOrderJDE con valores redondeados según SAT
	 */
	private PaymentOrderJDE applySATRounding(PaymentOrderJDE paymentOrderJDE, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		if (paymentOrderJDE == null) {
			return paymentOrderJDE;
		}
		
		// Aplicar redondeo SAT a OrderTotal
		if (paymentOrderJDE.getOrder().getOrderTotal() != null && 
			paymentOrderJDE.getOrder().getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedOrderTotal = DecimalPrecisionUtils.roundToTwoDecimals(paymentOrderJDE.getOrder().getOrderTotal());
			paymentOrderJDE.getOrder().setOrderTotal(roundedOrderTotal);
			LOG.info(String.format("%s OrderTotal redondeado: %s", idOperation, roundedOrderTotal));
		}
		
		// Aplicar redondeo SAT a SubTotal
		if (paymentOrderJDE.getOrder().getSubTotal() != null && 
			paymentOrderJDE.getOrder().getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedSubTotal = DecimalPrecisionUtils.roundToTwoDecimals(paymentOrderJDE.getOrder().getSubTotal());
			paymentOrderJDE.getOrder().setSubTotal(roundedSubTotal);
			LOG.info(String.format("%s SubTotal redondeado: %s", idOperation, roundedSubTotal));
		}
		
		// Aplicar redondeo SAT a IVATotal
		if (paymentOrderJDE.getOrder().getIvaTotal() != null && 
			paymentOrderJDE.getOrder().getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedIvaTotal = DecimalPrecisionUtils.roundToTwoDecimals(paymentOrderJDE.getOrder().getIvaTotal());
			paymentOrderJDE.getOrder().setIvaTotal(roundedIvaTotal);
			LOG.info(String.format("%s IVATotal redondeado: %s", idOperation, roundedIvaTotal));
		}
		
		LOG.info(String.format("%s SUCCESS: PaymentOrderJDE redondeado según SAT", idOperation));
		return paymentOrderJDE;
	}

	@Override
	public ResponseModel saveOrder(OrderDto orderDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT saveOrder()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderDto: %s , companyCode: %s ]", idOperation, orderDto.toString(),
				companyCode));

		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		boolean saveOk = paymentRepository.saveOrder(orderDto, idOperation);

		return new ResponseModel(saveOk);
	}

	@Override
	public ResponseModel getBathcFolioByCompanyCode(String nnsy, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getBathcFolioByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [nnsy: %s , companyCode: %s ]", idOperation, nnsy, companyCode));

		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		Long batchFolio = paymentRepository.getBathcFolioByExternalServiceByNNSY(nnsy, idOperation);

		return new ResponseModel(batchFolio);
	}

	@Override
	public void sendOrderToSave(PaymentOrderJDE paymentOrderJDE, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT sendOrderToSave()", idOperation));
		LOG.info(String.format("%s PARAMS: [paymentOrderJDE: %s , companyCode: %s ]", idOperation,
				paymentOrderJDE.toString(), companyCode));

		// Validar precisión decimal del PaymentOrderJDE
		validatePaymentOrderPrecision(paymentOrderJDE, idOperation);
		
		// Aplicar redondeo SAT
		paymentOrderJDE = applySATRounding(paymentOrderJDE, idOperation);

		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		paymentRepository.sendOrderToSave(paymentOrderJDE);
	}

	@Override
	public void sendOrdersToSaveToQueue(List<PaymentOrderJDE> paymentOrderJDEList, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT sendOrdersToSaveToQueue()", idOperation));
		LOG.info(String.format("PARAMS: [paymentOrderJDEList size: %d , companyCode: %s ]", idOperation, paymentOrderJDEList.size(), companyCode));

		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		for (PaymentOrderJDE paymentOrder : paymentOrderJDEList) {
			try {
				LOG.info(String.format("%s Processing PaymentOrderJDE: %s", idOperation, paymentOrder));
				
				// Validar precisión decimal del PaymentOrderJDE
				validatePaymentOrderPrecision(paymentOrder, idOperation);
				
				// Aplicar redondeo SAT
				paymentOrder = applySATRounding(paymentOrder, idOperation);
				
				paymentRepository.sendOrderToSave(paymentOrder);
				LOG.info(String.format("%s Successfully sent PaymentOrderJDE: %s", idOperation, paymentOrder));
			} catch (Exception ex) {
				LOG.error(String.format("%s ERROR while sending PaymentOrderJDE: %s. EXCEPTION: %s",
						idOperation, paymentOrder, ex.getMessage()));
				throw new GlobalError();
			}
		}
	}

	@Override
	public ResponseModel getConsecutiveOrderNumberByCompanyCode(String orderType, String companyCode,
			String companyNumber, String idOperation) {
		LOG.info(String.format("%s INIT getConsecutiveOrderNumberByCompanyCode()", idOperation));

		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		BigDecimal orderNumber = paymentRepository
				.getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(orderType, companyNumber, idOperation);
		return new ResponseModel(orderNumber);
	}

	@Override
	public ResponseModel searchInvoiceRecordsByOrderAndCompanyCode(OrderDto orderDto,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchInvoiceRecordsByOrderAndCompanyCode()", idOperation));

		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<InvoiceRecordDto> invoiceRecordList = paymentRepository.searchInvoiceRecordsByOrden(orderDto,
				orderConfigurationDto, idOperation);
		return new ResponseModel(invoiceRecordList);
	}

	@Override
	public void sendCreditNoteToSaveByCompanyCoode(PaymentOrderJDE paymentOrderJDE, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT sendCreditNoteToSaveByCompanyCoode()", idOperation));
		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		paymentRepository.sendCreditNoteToSaveByCompanyCoode(paymentOrderJDE);
	}

	@Override
	public void resendNotesToQueue(PaymentOrderJDE paymentOrderJDE, String companyCode,
								   String idOperation) {
		LOG.info(String.format("%s INIT resendNotesToQueue()", idOperation));
		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		paymentRepository.resendNotesToQueue(paymentOrderJDE);
	}

	@Override
	public void sendOrderToSaveInvoiceByCompanyCoode(PaymentOrderJDE paymentOrderJDE, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT sendOrderToSaveInvoiceByCompanyCoode()", idOperation));
		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		paymentRepository.sendOrderToSaveInvoice(paymentOrderJDE);
	}

	@Override
	public ResponseModel getInvoiceRecordByBranchCodeAndCompanyCode(String branchCode, String companyCode,
			String companyNumber, String idOperation) {

		LOG.info(String.format("%s INIT getInvoiceRecordByBranchCodeAndCompanyCode()", idOperation));
		GenericPaymentJdeRepository paymentRepository = paymentJdeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		InvoiceReferenceDto invoiceReferenceDto = paymentRepository
				.getInvoiceRecordByBranchCodeAndCompanyCode(branchCode, companyNumber, idOperation);

		return new ResponseModel(invoiceReferenceDto);
	}
}