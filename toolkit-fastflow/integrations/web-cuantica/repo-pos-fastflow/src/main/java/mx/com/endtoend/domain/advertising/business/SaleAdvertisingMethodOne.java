package mx.com.endtoend.domain.advertising.business;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.advertising.business.validations.SaleAdvertisingValidation;
import mx.com.endtoend.domain.advertising.dto.SaleAdvertisingInterfaceService;
import mx.com.endtoend.domain.advertising.ports.AdvertisingPersistencePort;
import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.smart.bussiness.functions.sales.AdvertisingMathOperation;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.AdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.AdvertisingSummaryCostDto;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SaleAdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.constants.StatusOrder;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.payments.OrderPaymentDto;

public class SaleAdvertisingMethodOne implements SaleAdvertisingInterface {

	private static final BigDecimal IVA = new BigDecimal(0.16);

	private static final String ORDER_CODE = "VA";

	private AdvertisingPersistencePort advertisingPersistencePort;

	private OrderConfigurationPersistencePort orderConfigurationPersistencePort;

	private UserConfigurationPersistencePort userConfigurationPersistencePort;

	private PaymentJDEServicePort jdePaymentJDEServicePort;

	private CompanyPersistencePort companyPersistencePort;

	public SaleAdvertisingMethodOne(SaleAdvertisingInterfaceService advertisingInterfaceService) {

		this.advertisingPersistencePort = advertisingInterfaceService.getAdvertisingPersistencePort();
		this.orderConfigurationPersistencePort = advertisingInterfaceService.getOrderConfigurationPersistencePort();
		this.userConfigurationPersistencePort = advertisingInterfaceService.getUserConfigurationPersistencePort();
		this.jdePaymentJDEServicePort = advertisingInterfaceService.getJdePaymentJDEServicePort();
		this.companyPersistencePort = advertisingInterfaceService.getCompanyPersistencePort();
	}

	private SaleAdvertisingValidation saleAdvertisingValidation = new SaleAdvertisingValidation();
	private AdvertisingMathOperation advertisingMathOperation = new AdvertisingMathOperation();

	private final Logger LOG = LoggerFactory.getLogger(SaleAdvertisingMethodOne.class);

	@Override
	public ResponseModel createAdvertisingByMethod(AdvertisingDto advertisingDto, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT createAdvertisingByMethod() ", idOperation));
		String validations = saleAdvertisingValidation.validOperativeDataToGenerateOrder(advertisingDto);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAMS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}

		ResponseModel responseConfiguration = orderConfigurationPersistencePort
				.viewOrderConfigurationDetailByOrderCodeAndCompanyCode(ORDER_CODE, companyCode, idOperation);
		OrderConfigurationDto orderConfiguration = (OrderConfigurationDto) responseConfiguration.getData();

		if (orderConfiguration == null)
			throw new ValidationError("ORDER CONFIGURATION IS EMPTY");

		ResponseModel responseEmployeeConfig = userConfigurationPersistencePort
				.findUserConfigurationByEmailAndBranchCode(advertisingDto.getEmployeeEmail(), companyCode,
						advertisingDto.getBranchCode(), idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseEmployeeConfig.getData();
		if (employeeDto == null)
			throw new ValidationError("EMPLOYEE CONFIGURATION IS EMPTY");

		AdvertisingSummaryCostDto advertisingSummaryCost = advertisingMathOperation
				.obtainSummaryCostOperation(advertisingDto);

		ResponseModel responseCompany = companyPersistencePort.findByCode(companyCode, idOperation);
		CompanyDto companyDto = (CompanyDto) responseCompany.getData();

		ResponseModel responseOrderNumber = jdePaymentJDEServicePort.getConsecutiveOrderNumberByCompanyCode(ORDER_CODE,
				companyCode, companyDto.getCompanyNumber(), idOperation);

		BigDecimal orderNumber = (BigDecimal) responseOrderNumber.getData();

		SaleAdvertisingDto saleAdvertising = generateOperativeData(orderConfiguration, employeeDto,
				advertisingSummaryCost, advertisingDto, orderNumber);

		ResponseModel responseAvdersitingCreate = advertisingPersistencePort
				.createAdvertisingByCompanyCode(saleAdvertising, companyCode, idOperation);
		SaleAdvertisingDto saleAdvertisingCreated = (SaleAdvertisingDto) responseAvdersitingCreate.getData();

		OrderPaymentDto orderPayment = new OrderPaymentDto(saleAdvertisingCreated.getOrderNumber(),
				saleAdvertisingCreated.getOrderCode(), saleAdvertisingCreated.getBranchCode(),
		saleAdvertisingCreated.getAmountTotal(), saleAdvertisingCreated.getAmountTotal(), "",
				saleAdvertisingCreated.getClientNumber());

		return new ResponseModel(orderPayment);
	}

	public SaleAdvertisingDto generateOperativeData(OrderConfigurationDto orderConfiguration, EmployeeDto employeeDto,
			AdvertisingSummaryCostDto advertisingSummaryCost, AdvertisingDto advertisingDto, BigDecimal orderNumber) {

		String[] wordArray = advertisingDto.getMessage().split(" ");
		Long totalWord = (long) wordArray.length;

		SaleAdvertisingDto saleAdvertising = new SaleAdvertisingDto(ORDER_CODE, orderNumber,
				advertisingDto.getBranchCode(), employeeDto.getId(), advertisingDto.getClientNumber(),
				advertisingDto.getMessage(), advertisingDto.getPricePerWord(), advertisingDto.getPublishedDay(),
				advertisingDto.getArticleNumber(), "CODE9999", advertisingDto.getDescription(), totalWord, IVA,
				advertisingSummaryCost.getIva(), advertisingSummaryCost.getSubTotal(),
				advertisingSummaryCost.getAmountTotal(), StatusOrder.CREATE_SALE_ORDER.getValue());

		return saleAdvertising;
	}

	@Override
	public ResponseModel viewAdvertisingListByMethod(String companyCode, String idOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseModel viewAdvertisingDetailByIdAndMethod(Long id, String companyCode, String idOperation) {
		// TODO Auto-generated method stub
		return null;
	}

}
