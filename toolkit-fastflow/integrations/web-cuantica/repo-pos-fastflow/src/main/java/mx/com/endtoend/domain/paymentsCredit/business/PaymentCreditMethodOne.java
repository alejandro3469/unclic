package mx.com.endtoend.domain.paymentsCredit.business;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditPaymentInterfaceService;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.dto.PaymentCreditTicketDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.domain.paymentsCredit.ports.PaymentCreditPersistencePort;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

public class PaymentCreditMethodOne implements PaymentCreditInterface {

	private PaymentCreditPersistencePort paymentCreditPersistencePort;

	private PaymentPersistencePort paymentPersistencePort;

	private String companyCode;

	private String idOperation;

	public PaymentCreditMethodOne(CreditPaymentInterfaceService creditPaymentInterfaceService) {
		this.paymentCreditPersistencePort = creditPaymentInterfaceService.getPaymentCreditPersistencePort();
		this.paymentPersistencePort = creditPaymentInterfaceService.getPaymentPersistencePort();
		this.companyCode = creditPaymentInterfaceService.getCompanyCode();
		this.idOperation = creditPaymentInterfaceService.getIdOperation();
	}

	private final Logger LOG = LoggerFactory.getLogger(PaymentCreditMethodOne.class);

	@Override
	public ResponseModel saveCreditSaleRequest(CreditSaleRequestDto creditSaleRequestDto) {
		LOG.info(String.format("%s INIT saveCreditSaleRequest()", idOperation));
		ResponseModel saveData = paymentCreditPersistencePort.saveCreditSaleRequest(creditSaleRequestDto, companyCode,
				idOperation);
		Boolean saved = (Boolean) saveData.getData();
		if (!saved)
			return new ResponseModel(false);
		return new ResponseModel(true);
	}

	@Override
	public ResponseModel saveCreditSaleResponse(CreditSaleResponseDto creditSaleResponseDto) {
		LOG.info(String.format("%s INIT saveCreditSaleResponse()", idOperation));
		ResponseModel saveData = paymentCreditPersistencePort.saveCreditSaleResponse(creditSaleResponseDto, companyCode,
				idOperation);
		Boolean saved = (Boolean) saveData.getData();
		if (!saved)
			return new ResponseModel(false);
		return new ResponseModel(true);
	}

	@Override
	public ResponseModel savePaymentStateRequest(StatusSaleRequestDto saleStatusSaleRequestDto) {
		LOG.info(String.format("%s INIT savePaymentStateRequest()", idOperation));
		ResponseModel saveData = paymentCreditPersistencePort.savePaymentStateRequest(saleStatusSaleRequestDto,
				companyCode, idOperation);
		Boolean saved = (Boolean) saveData.getData();
		if (!saved)
			return new ResponseModel(false);
		return new ResponseModel(true);
	}

	@Override
	public ResponseModel savePaymentStateResponse(StatusSaleResponseDto statusSaleResponseDto) {
		LOG.info(String.format("%s INIT savePaymentStateResponse()", idOperation));
		ResponseModel saveData = paymentCreditPersistencePort.savePaymentStateResponse(statusSaleResponseDto,
				companyCode, idOperation);
		Boolean saved = (Boolean) saveData.getData();
		if (!saved)
			return new ResponseModel(false);
		return new ResponseModel(true);
	}

	@Override
	public ResponseModel getPaymentStateByOrderNumberAndCode(BigDecimal orderNumber, String orderCode) {
		LOG.info(String.format("%s INIT savePaymentStateResponse()", idOperation));
		ResponseModel responseSearchData = paymentCreditPersistencePort.getPaymentStateByOrderNumberAndCode(orderNumber,
				orderCode, companyCode, idOperation);
		if (responseSearchData.getData() == null)
			throw new ValidationError("ORDER NOT HAVE CREDIT RECORDS");
		return responseSearchData;
	}

	@Override
	public ResponseModel generateCreditPaymentTicketByOrderNumberAndCode(BigDecimal orderNumber, String orderCode) {
		LOG.info(String.format("%s INIT generateCreditPaymentTicketByOrderNumberAndCode()", idOperation));
		ResponseModel responseSearchPayment = paymentPersistencePort
				.getPaymentDetailByOrderNumberAndOrderCodeAndCompanyCode(orderNumber, orderCode, companyCode,
						idOperation);
		PaymentDto paymentDto = (PaymentDto) responseSearchPayment.getData();
		if (paymentDto == null)
			throw new ValidationError("ORDER HAVEN´T PAYMENT");

		Double amountApplied = 0.0;
		List<CreditPaymentDto> creditPaymentList = paymentDto.getCreditPaymentList();
		if (creditPaymentList != null) {
			for (CreditPaymentDto creditPaymentDto : creditPaymentList) {
				amountApplied += creditPaymentDto.getAmountApplied().doubleValue();
			}
		}

		if (amountApplied == 0.0)
			throw new ValidationError("PAYMENT HAVEN´T CREDIT RECORD");

		ResponseModel responseGetOrder = paymentPersistencePort.getOrderByCompanyCodeAndParams(orderNumber, orderCode,
				companyCode, idOperation);
		OrderDto orderDto = (OrderDto) responseGetOrder.getData();

		ResponseModel responseGetBranch = paymentPersistencePort
				.getBranchDetailByBranchCodeAndCompanyCode(orderDto.getBranchCode(), companyCode, idOperation);
		BranchDto branchDto = (BranchDto) responseGetBranch.getData();

		ResponseModel responseGetUserVendor = paymentPersistencePort
				.getUserInformationByEmailAndCompanyCode(paymentDto.getEmployeEmail(), companyCode, idOperation);
		UserDto userSale = (UserDto) responseGetUserVendor.getData();

		PaymentCreditTicketDto paymentCreditTicketDto = new PaymentCreditTicketDto(paymentDto, branchDto, userSale,
				BigDecimal.valueOf(amountApplied));

		ResponseModel responseTicket = paymentCreditPersistencePort.generateCreditPaymentTicket(paymentCreditTicketDto,
				companyCode, idOperation);

		return responseTicket;
	}

	@Override
	public ResponseModel cancelCreditPaymentByOrderNumberAndCode(BigDecimal orderNumber, String orderCode) {
		LOG.info(String.format("%s INIT cancelCreditPaymentByOrderNumberAndCode()", idOperation));
		ResponseModel saveData = paymentCreditPersistencePort.cancelCreditPaymentByOrderNumberAndCode(orderNumber,
				orderCode, companyCode, idOperation);
		Boolean saved = (Boolean) saveData.getData();
		if (!saved)
			return new ResponseModel(false);
		return new ResponseModel(true);
	}

}
