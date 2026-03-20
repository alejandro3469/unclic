package mx.com.endtoend.domain.recharges.business;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.com.endtoend.domain.clients.dto.ClientIdDto;
import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.recharges.dto.RechargeSaleDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.AddressDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

/**
 * Clase encargda de generar los datos operativos de cobros y ordenes asociados
 * a las ventas de tiempo aire
 * 
 * @author ddcasas
 */

public class RechargeGeneratorService {

	private static final BigDecimal IVA = new BigDecimal(BigInteger.valueOf(16));
	private static final String GENERAL_DESCRIPTION = "RECARGA TIEMPO AIRE";

	/**
	 * Método para generar los datos operativos de la orden asociada a las ventas de
	 * tiempo aire.
	 * 
	 * @param orderConfigurationDto
	 * @param employeeDto
	 * @param companyDto
	 * @param clientDto
	 * @param statusDto
	 * @param orderNumber
	 * @param rechargeSaleDto
	 * @return OrderDto
	 */
	public OrderDto generateOrderDtoByRecharge(OrderConfigurationDto orderConfigurationDto, EmployeeDto employeeDto,
			CompanyDto companyDto, ClientIdDto clientDto, StatusDto statusDto, BigDecimal orderNumber,
			RechargeSaleDto rechargeSaleDto) {

		OrderDto order = new OrderDto();
		ClientDto client = new ClientDto();
		client.setId(clientDto.getId());

		order.setOrderId(null);
		order.setOrderNumber(orderNumber);
		order.setBatchFolio(null);
		order.setOrderCode(orderConfigurationDto.getSaleType().getCode());
		order.setBranchCode(employeeDto.getBranchCode());
		order.setCompanyNumber(companyDto.getCompanyNumber());
		order.setCurrency("MXN");
		order.setExchangeRate(BigDecimal.valueOf(1.0));
		order.setCreationDate(new Date());
		order.setRequestDate(new Date());
		order.setValidityDate(new Date());
		order.setClient(client);
		order.setClientTax(IVA);
		order.setClientReference("");
		order.setUserNumber(employeeDto.getUserNumber());
		order.setIdUser(employeeDto.getUserId());
		order.setEmployeeEmail(rechargeSaleDto.getEmployeeEmail());
		order.setSubTotal(BigDecimal.ZERO);
		order.setIvaTotal(BigDecimal.ZERO);
		order.setOrderTotal(BigDecimal.ZERO);
		order.setPendingPayment(BigDecimal.ZERO);
		order.setDiscountTotal(BigDecimal.ZERO);
		order.setStatus(statusDto);
		order.setRetentionCode("");
		order.setOrderType(orderConfigurationDto.getSaleType().getType());
		order.setTempMigStatus("0");
		order.setIsUpdated(false);
		order.setObservations("RECHARGE");
		order.setIsRetentionOrder(false);
		order.setCfdiType("");

		List<AddressDto> addresses = generateAddress(clientDto);
		order.setAddresses(addresses);

		order.setTaxes(null);

		List<OrderDetailDto> orderDetailList = new ArrayList<>();

		OrderDetailDto orderDetail = new OrderDetailDto();

		orderDetail.setId(null);
		orderDetail.setLineNumber(1);
		orderDetail.setLineType(rechargeSaleDto.getCompanyRecharge().getLineType());
		orderDetail.setStorageType(rechargeSaleDto.getCompanyRecharge().getStorageType());
		orderDetail.setWarehouseCode(employeeDto.getBranchCode());
		orderDetail.setArticleNumber(rechargeSaleDto.getCompanyRecharge().getArticleNumber());
		orderDetail.setDescriptionOne(rechargeSaleDto.getCompanyRecharge().getDescription());
		orderDetail.setDescriptionTwo(GENERAL_DESCRIPTION);
		orderDetail.setUnitMeasurement("PZ");
		orderDetail.setPrimaryUnitMeasure("PZ");
		orderDetail.setRequestAmount(BigDecimal.ZERO);

		/*Double unitPrice = (rechargeSaleDto.getCompanyRecharge().getAmount().doubleValue() / 1.16);
		unitPrice = (double) Math.round(unitPrice * 100) / 100;*/
		BigDecimal amount = rechargeSaleDto.getCompanyRecharge().getAmount();
		BigDecimal divisor = new BigDecimal("1.16");
		BigDecimal unitPrice = amount.divide(divisor, 2, RoundingMode.HALF_UP);

		orderDetail.setModifiedUnitPrice(unitPrice);
		orderDetail.setUnitPrice(unitPrice);
		
		orderDetail.setFinalUnitPrice(BigDecimal.ZERO);
		orderDetail.setLineCodeOne(orderConfigurationDto.getLineCodeOne());
		orderDetail.setLineCodeTwo(orderConfigurationDto.getLineCodeTwo());
		orderDetail.setDiscountSeller(BigDecimal.ZERO);
		orderDetail.setUerNumberSeller(employeeDto.getUserNumber());
		orderDetail.setArticleCode(rechargeSaleDto.getCompanyRecharge().getArticleCode());
		orderDetail.setPriceType("0");
		orderDetail.setIsRetentionArticle(false);
		orderDetail.setRetentionCode("");
		orderDetail.setAlternateDescription(GENERAL_DESCRIPTION);
		orderDetail.setApplyTax("Y");
		orderDetail.setTaxValueOne(IVA);
		orderDetail.setTaxValueTwo(BigDecimal.valueOf(-1));
		orderDetail.setTaxValueThree(BigDecimal.valueOf(-1));
		orderDetail.setTaxValueFour(BigDecimal.valueOf(-1));
		orderDetail.setTaxValueFive(BigDecimal.valueOf(-1));
		orderDetail.setTaxValueByDefault(IVA);
		orderDetail.setConversionFactor(BigDecimal.ZERO);
		orderDetail.setIsCustumArticle(true);

		orderDetailList.add(orderDetail);

		order.setOrderDetail(orderDetailList);

		order.setTimeActive(null);
		order.setIsConverted(false);
		order.setEmails(null);
		order.setIsInvoiceTop(false);
		order.setInvoiceAmount(BigDecimal.ZERO);
		order.setIsNewOrder(false);
		order.setInvoiceReference(null);
		order.setIsCreditNoteComplete(false);

		return order;
	}

	private List<AddressDto> generateAddress(ClientIdDto clientDto) {
		List<AddressDto> addresses = new ArrayList<>();
		AddressDto addressFis = new AddressDto();
		addressFis.setId(null);
		addressFis.setStreet(clientDto.getDirection().getStreet());
		addressFis.setColony(clientDto.getDirection().getColony());
		addressFis.setState(clientDto.getDirection().getStateCode());
		addressFis.setDelegation(clientDto.getDirection().getDelegationCode());
		addressFis.setDelegationCode(clientDto.getDirection().getDelegationCode());
		addressFis.setInteriorNumber(clientDto.getDirection().getNoInterior());
		addressFis.setOutdoorNumber(clientDto.getDirection().getNoOutdoor());
		addressFis.setCp(clientDto.getDirection().getCp());
		addressFis.setCity(clientDto.getDirection().getCity());
		addressFis.setAddressType("Fiscal");
		addressFis.setFlat(clientDto.getDirection().getFlatCode());
		addressFis.setFlatCode(clientDto.getDirection().getFlatCode());
		addressFis.setCoordinate(clientDto.getDirection().getCoordinatesCode());
		addressFis.setCoordinateCode(clientDto.getDirection().getCoordinatesCode());
		addresses.add(addressFis);

		AddressDto addressEnv = new AddressDto();
		addressEnv.setId(null);
		addressEnv.setStreet(clientDto.getDirection().getStreet());
		addressEnv.setColony(clientDto.getDirection().getColony());
		addressEnv.setState(clientDto.getDirection().getStateCode());
		addressEnv.setDelegation(clientDto.getDirection().getDelegationCode());
		addressEnv.setDelegationCode(clientDto.getDirection().getDelegationCode());
		addressEnv.setInteriorNumber(clientDto.getDirection().getNoInterior());
		addressEnv.setOutdoorNumber(clientDto.getDirection().getNoOutdoor());
		addressEnv.setCp(clientDto.getDirection().getCp());
		addressEnv.setCity(clientDto.getDirection().getCity());
		addressEnv.setAddressType("Envío");
		addressEnv.setFlat(clientDto.getDirection().getFlatCode());
		addressEnv.setFlatCode(clientDto.getDirection().getFlatCode());
		addressEnv.setCoordinate(clientDto.getDirection().getCoordinatesCode());
		addressEnv.setCoordinateCode(clientDto.getDirection().getCoordinatesCode());
		addresses.add(addressEnv);
		return addresses;
	}

	public PaymentDto generatePaymentDtoByRecharge(OpeningOperationDto openingOperation, OrderDto orderDto,
			RechargeSaleDto rechargeSaleDto) {

		PaymentDto paymentDto = new PaymentDto();

		paymentDto.setOpeningCashId(openingOperation.getOpeningId());
		paymentDto.setPaymentDate(new Date());
		paymentDto.setClientNumber(rechargeSaleDto.getClientNumber());
		paymentDto.setEmployeEmail(rechargeSaleDto.getEmployeeEmail());
		paymentDto.setUserId(orderDto.getIdUser());
		paymentDto.setUserNumber(orderDto.getUserNumber());
		paymentDto.setPaymentState(StatusOrder.FULL_PAYMENT.getValue());
		paymentDto.setOrderNumber(orderDto.getOrderNumber());
		paymentDto.setOrderCode(orderDto.getOrderCode());
		paymentDto.setOrderTotal(orderDto.getOrderTotal());
		paymentDto.setPendingPayment(orderDto.getOrderTotal());
		paymentDto.setIsPrinted(false);

		setPaymentMethods(rechargeSaleDto, paymentDto);

		return paymentDto;
	}

	private void setPaymentMethods(RechargeSaleDto rechargeSaleDto, PaymentDto paymentDto) {
		if (rechargeSaleDto.getPayments().getPaymentCashList() != null) {
			paymentDto.setPaymentCashList(rechargeSaleDto.getPayments().getPaymentCashList());
		}

		if (rechargeSaleDto.getPayments().getCheckPaymentList() != null) {
			paymentDto.setCheckPaymentList(rechargeSaleDto.getPayments().getCheckPaymentList());
		}

		if (rechargeSaleDto.getPayments().getCreditCardPaymentList() != null) {
			paymentDto.setCreditCardPaymentList(rechargeSaleDto.getPayments().getCreditCardPaymentList());
		}

		if (rechargeSaleDto.getPayments().getTransferPaymentList() != null) {
			paymentDto.setTransferPaymentList(rechargeSaleDto.getPayments().getTransferPaymentList());
		}
	}

}
