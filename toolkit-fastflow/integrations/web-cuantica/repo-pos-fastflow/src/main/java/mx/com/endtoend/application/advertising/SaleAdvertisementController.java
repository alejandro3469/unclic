package mx.com.endtoend.application.advertising;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.advertising.dto.SaleAdvertisingInterfaceService;
import mx.com.endtoend.domain.advertising.ports.AdvertisingServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.AdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/sale-advertising")
public class SaleAdvertisementController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private AdvertisingServicePort advertisingServicePort;

	@Autowired
	private OrderConfigurationPersistencePort orderConfigurationPersistencePort;

	@Autowired
	private UserConfigurationPersistencePort userConfigurationPersistencePort;

	@Autowired
	private PaymentJDEServicePort jdePaymentJDEServicePort;

	@Autowired
	private CompanyPersistencePort companyPersistencePort;

	private String module = "ADVERTISING";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(SaleAdvertisementController.class);

	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateSaleAdvertisingByCompanyCode(@RequestBody AdvertisingDto advertisingDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT generateSaleAdvertisingByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ advertisingDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				advertisingDto.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("INCOMPLETE-CONFIGURATION");
		}

		SaleAdvertisingInterfaceService saleAdvertisingInterfaceService = new SaleAdvertisingInterfaceService(
				orderConfigurationPersistencePort, userConfigurationPersistencePort, jdePaymentJDEServicePort,
				companyPersistencePort);
		ResponseModel responseModel = advertisingServicePort.createAdvertisingByMethod(saleAdvertisingInterfaceService,
				advertisingDto, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
