package mx.com.endtoend.domain.paymentsCredit.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.paymentsCredit.business.PaymentCreditFactory;
import mx.com.endtoend.domain.paymentsCredit.business.PaymentCreditInterface;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditPaymentInterfaceService;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.ports.PaymentCreditPersistencePort;
import mx.com.endtoend.domain.paymentsCredit.ports.PaymentCreditServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase encargada para obtener la implementación concreta de la interfaz
 * {@link PaymentCreditInterface} para la administración del registro de
 * oepraciones de cobros con el sistema BDS
 */
public class PaymentCreditServiceImpl implements PaymentCreditServicePort {

	private PaymentCreditPersistencePort paymentCreditPersistencePort;

	public PaymentCreditServiceImpl(PaymentCreditPersistencePort paymentCreditPersistencePort) {
		this.paymentCreditPersistencePort = paymentCreditPersistencePort;
	}

	private PaymentCreditFactory paymentCreditFactory = new PaymentCreditFactory();

	private final Logger LOG = LoggerFactory.getLogger(PaymentCreditServiceImpl.class);

	@Override
	public ResponseModel saveCreditSaleRequestByMethod(CreditPaymentInterfaceService creditPaymentInterfaceService,
			CreditSaleRequestDto creditSaleRequestDto, String method) {
		LOG.info(String.format("%s INIT saveCreditSaleRequestByMethod()",
				creditPaymentInterfaceService.getIdOperation()));
		creditPaymentInterfaceService.setPaymentCreditPersistencePort(paymentCreditPersistencePort);
		PaymentCreditInterface paymentCredit = paymentCreditFactory
				.getImplementationByMethodCode(creditPaymentInterfaceService, method);
		if (paymentCredit == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					creditPaymentInterfaceService.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = paymentCredit.saveCreditSaleRequest(creditSaleRequestDto);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel saveCreditSaleResponseByMethod(CreditPaymentInterfaceService creditPaymentInterfaceService,
			CreditSaleResponseDto creditSaleResponseDto, String method) {
		LOG.info(String.format("%s INIT saveCreditSaleResponseByMethod()",
				creditPaymentInterfaceService.getIdOperation()));
		creditPaymentInterfaceService.setPaymentCreditPersistencePort(paymentCreditPersistencePort);
		PaymentCreditInterface paymentCredit = paymentCreditFactory
				.getImplementationByMethodCode(creditPaymentInterfaceService, method);
		if (paymentCredit == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					creditPaymentInterfaceService.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = paymentCredit.saveCreditSaleResponse(creditSaleResponseDto);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel savePaymentStateRequestByMethod(CreditPaymentInterfaceService creditPaymentInterfaceService,
			StatusSaleRequestDto saleStatusSaleRequestDto, String method) {
		LOG.info(String.format("%s INIT savePaymentStateRequestByMethod()",
				creditPaymentInterfaceService.getIdOperation()));
		creditPaymentInterfaceService.setPaymentCreditPersistencePort(paymentCreditPersistencePort);
		PaymentCreditInterface paymentCredit = paymentCreditFactory
				.getImplementationByMethodCode(creditPaymentInterfaceService, method);
		if (paymentCredit == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					creditPaymentInterfaceService.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = paymentCredit.savePaymentStateRequest(saleStatusSaleRequestDto);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel savePaymentStateResponseByMethod(CreditPaymentInterfaceService creditPaymentInterfaceService,
			StatusSaleResponseDto statusSaleResponseDto, String method) {
		LOG.info(String.format("%s INIT savePaymentStateResponseByMethod()",
				creditPaymentInterfaceService.getIdOperation()));
		creditPaymentInterfaceService.setPaymentCreditPersistencePort(paymentCreditPersistencePort);
		PaymentCreditInterface paymentCredit = paymentCreditFactory
				.getImplementationByMethodCode(creditPaymentInterfaceService, method);
		if (paymentCredit == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					creditPaymentInterfaceService.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = paymentCredit.savePaymentStateResponse(statusSaleResponseDto);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel getPaymentStateByOrderNumberAndCodeByMethod(
			CreditPaymentInterfaceService creditPaymentInterfaceService, BigDecimal orderNumber, String orderCode,
			String method) {
		LOG.info(String.format("%s INIT getPaymentStateByOrderNumberAndCodeByMethod()",
				creditPaymentInterfaceService.getIdOperation()));
		creditPaymentInterfaceService.setPaymentCreditPersistencePort(paymentCreditPersistencePort);
		PaymentCreditInterface paymentCredit = paymentCreditFactory
				.getImplementationByMethodCode(creditPaymentInterfaceService, method);
		if (paymentCredit == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					creditPaymentInterfaceService.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = paymentCredit.getPaymentStateByOrderNumberAndCode(orderNumber,
				orderCode);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel generateCreditPaymentTicketByOrderNumberAndCodeByMethod(
			CreditPaymentInterfaceService creditPaymentInterfaceService, BigDecimal orderNumber, String orderCode,
			String method) {
		LOG.info(String.format("%s INIT generateCreditPaymentTicketByOrderNumberAndCodeByMethod()",
				creditPaymentInterfaceService.getIdOperation()));
		creditPaymentInterfaceService.setPaymentCreditPersistencePort(paymentCreditPersistencePort);
		PaymentCreditInterface paymentCredit = paymentCreditFactory
				.getImplementationByMethodCode(creditPaymentInterfaceService, method);
		if (paymentCredit == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					creditPaymentInterfaceService.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = paymentCredit
				.generateCreditPaymentTicketByOrderNumberAndCode(orderNumber, orderCode);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel cancelCreditPaymentByOrderNumberAndCodeByMethod(
			CreditPaymentInterfaceService creditPaymentInterfaceService, BigDecimal orderNumber, String orderCode,
			String method) {
		LOG.info(String.format("%s INIT cancelCreditPaymentByOrderNumberAndCodeByMethod()",
				creditPaymentInterfaceService.getIdOperation()));
		creditPaymentInterfaceService.setPaymentCreditPersistencePort(paymentCreditPersistencePort);
		PaymentCreditInterface paymentCredit = paymentCreditFactory
				.getImplementationByMethodCode(creditPaymentInterfaceService, method);
		if (paymentCredit == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					creditPaymentInterfaceService.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = paymentCredit.cancelCreditPaymentByOrderNumberAndCode(orderNumber,
				orderCode);
		return responseFromImplementation;
	}

}
