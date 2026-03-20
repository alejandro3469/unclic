package mx.com.endtoend.infrastructure.paymentsCredit.common.adapter;

import java.math.BigDecimal;

import mx.com.endtoend.infrastructure.paymentsCredit.common.factory.PaymentCreditRepositoryFactory;
import mx.com.endtoend.infrastructure.paymentsCredit.common.repository.PaymentCreditRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleStausDto;
import mx.com.endtoend.domain.paymentsCredit.dto.PaymentCreditTicketDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.ports.PaymentCreditPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class PaymentCreditJpaAdapter implements PaymentCreditPersistencePort {

	@Autowired
	private PaymentCreditRepositoryFactory paymentCreditRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(PaymentCreditJpaAdapter.class);

	@Override
	public ResponseModel saveCreditSaleRequest(CreditSaleRequestDto creditSaleRequestDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT saveCreditSaleRequest()", idOperation));
		PaymentCreditRepositoryInterface paymentRepository = paymentCreditRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		Boolean saved = paymentRepository.saveCreditSaleRequest(creditSaleRequestDto, idOperation);
		return new ResponseModel(saved);

	}

	@Override
	public ResponseModel saveCreditSaleResponse(CreditSaleResponseDto creditSaleResponseDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT saveCreditSaleResponse()", idOperation));
		PaymentCreditRepositoryInterface paymentRepository = paymentCreditRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		Boolean saved = paymentRepository.saveCreditSaleResponse(creditSaleResponseDto, idOperation);
		return new ResponseModel(saved);
	}

	@Override
	public ResponseModel savePaymentStateRequest(StatusSaleRequestDto statusSaleRequestDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT savePaymentStateRequest()", idOperation));
		PaymentCreditRepositoryInterface paymentRepository = paymentCreditRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		Boolean saved = paymentRepository.savePaymentStateRequest(statusSaleRequestDto, idOperation);
		return new ResponseModel(saved);
	}

	@Override
	public ResponseModel savePaymentStateResponse(StatusSaleResponseDto statusSaleResponseDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT savePaymentStateResponse()", idOperation));
		PaymentCreditRepositoryInterface paymentRepository = paymentCreditRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		Boolean saved = paymentRepository.savePaymentStateResponse(statusSaleResponseDto, idOperation);
		return new ResponseModel(saved);
	}

	@Override
	public ResponseModel getPaymentStateByOrderNumberAndCode(BigDecimal orderNumber, String orderCode,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getPaymentStateByOrderNumberAndCode()", idOperation));
		PaymentCreditRepositoryInterface paymentRepository = paymentCreditRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditSaleStausDto creditSaleStaus = paymentRepository.getPaymentStateByOrderNumberAndCode(orderNumber,
				orderCode, idOperation);
		return new ResponseModel(creditSaleStaus);
	}

	@Override
	public ResponseModel generateCreditPaymentTicket(PaymentCreditTicketDto paymentCreditTicketDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getPaymentStateByOrderNumberAndCode()", idOperation));
		PaymentCreditRepositoryInterface paymentRepository = paymentCreditRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return paymentRepository.generateCreditPaymentTicket(paymentCreditTicketDto, idOperation);
	}

	@Override
	public ResponseModel cancelCreditPaymentByOrderNumberAndCode(BigDecimal orderNumber, String orderCode,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT cancelCreditPaymentByOrderNumberAndCode()", idOperation));
		PaymentCreditRepositoryInterface paymentRepository = paymentCreditRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		Boolean canceled = paymentRepository.cancelCreditPaymentByOrderNumberAndCode(orderNumber, orderCode,
				idOperation);
		return new ResponseModel(canceled);
	}

}
