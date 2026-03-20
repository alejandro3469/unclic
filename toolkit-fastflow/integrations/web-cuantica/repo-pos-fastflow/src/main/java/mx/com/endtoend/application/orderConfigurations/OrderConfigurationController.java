package mx.com.endtoend.application.orderConfigurations;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.orderConfigurations.ports.api.OrderConfigurationServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

/**
 * Controlador para la configuración de los datos operativos de los distintos
 * tipos de ordenes de las compañias registradas en el sistema.
 * 
 * @author ddcasas
 *
 */

@RestController
@RequestMapping("/order-config")
public class OrderConfigurationController {

	@Autowired
	private OrderConfigurationServicePort orderConfigurationServicePort;

	@Autowired
	private CompanyServicePort companyServicePort;

	private static String module = "ORDER_CONFIGURATION";

	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(OrderConfigurationController.class);

	/**
	 * Método para configurar los datos operaciones de las ordenes del sistema por
	 * código de compañia.
	 * 
	 * @param orderConfigurationDto
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createOrderConfiguration(@RequestBody OrderConfigurationDto orderConfigurationDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT createOrderConfiguration() ", idOperation));

		LOG.info(String.format("%s PARAMS: [ OrderConfigurationDto: %s , companyCode: %s , branchCode: %s ]",
				idOperation, orderConfigurationDto.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("INCOMPLETE-CONFIGURATION");
		}

		ResponseModel responseModel = orderConfigurationServicePort.createOrderConfigurationByCompanyCode(
				orderConfigurationDto, companyCode, method.getCode(), idOperation);

		LOG.info(String.format("%s CREATE ORDER-CONFIGURATION END ", idOperation));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para actualizar la configuración de los datos operaciones de las
	 * ordenes del sistema por código de compañia y id.
	 * 
	 * @param orderConfigurationDto
	 * @param orderId
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@PutMapping("/update/{orderId}/{companyCode}/{branchCode}")
	public ResponseEntity<?> updateOrderConfigurationById(@RequestBody OrderConfigurationDto orderConfigurationDto,
			@PathVariable Long orderId, @PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT updateOrderConfigurationById()  ", idOperation));

		LOG.info(String.format(
				"%s PARAMS: [ OrderConfigurationDto: %s , orderId: %s , companyCode: %s , branchCode: %s ]",
				idOperation, orderConfigurationDto.toString(), orderId.toString(), companyCode, branchCode));

		orderConfigurationDto.setId(orderId);

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("INCOMPLETE-CONFIGURATION");
		}

		ResponseModel responseModel = orderConfigurationServicePort.updateOrderConfigurationByCompanyCode(
				orderConfigurationDto, companyCode, method.getCode(), idOperation);

		LOG.info(String.format("%s UPDATE ORDER-CONFIGURATION END", idOperation));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para ver el detalle de la configuración de una orden por Id y código
	 * de compañia.
	 * 
	 * @param orderId
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/view/{orderId}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getOrderConfigurationByIdAndCompanyCode(@PathVariable Long orderId,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getOrderConfigurationByIdAndCompanyCode() ", idOperation));

		LOG.info(String.format("%s PARAMS: [ orderId: %s , companyCode: %s , branchCode: %s ]", idOperation,
				orderId.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("INCOMPLETE-CONFIGURATION");
		}

		ResponseModel responseModel = orderConfigurationServicePort
				.viewOrderConfigurationDetailByIdAndCompanyCode(orderId, companyCode, method.getCode(), idOperation);

		LOG.info(String.format("%s VIEW ORDER-CONFIGURATION END", idOperation));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	/**
	 * Método para ver el detalle de la configuración de una orden por código de
	 * orden y código de compañia.
	 * 
	 * @param orderCode
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/view/order-code/{orderCode}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getOrderConfigurationByOrderCodeAndCompanyCode(@PathVariable String orderCode,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getOrderConfigurationByIdAndCompanyCode() ", idOperation));

		LOG.info(String.format("%s PARAMS: [ orderCode: %s , companyCode: %s , branchCode: %s ]", idOperation,
				orderCode, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("INCOMPLETE-CONFIGURATION");
		}

		ResponseModel responseModel = orderConfigurationServicePort
				.viewOrderConfigurationDetailByOredrCodeAndCompanyCode(orderCode, companyCode, method.getCode(),
						idOperation);

		LOG.info(String.format("%s VIEW ORDER-CONFIGURATION END", idOperation));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	/**
	 * Método para ver la lista de todas las configuraciones de ordenes registradas
	 * en el sistema por código de compañia.
	 * 
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/view/all/{companyCode}/{branchCode}")
	public ResponseEntity<?> getOrderConfigurationListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getOrderConfigurationListByCompanyCode() ", idOperation));

		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("INCOMPLETE-CONFIGURATION");
		}

		ResponseModel responseModel = orderConfigurationServicePort.getOrderConfigurationListByCompanyCode(companyCode,
				method.getCode(), idOperation);

		LOG.info(String.format("%s VIEW ALL ORDER-CONFIGURATION END", idOperation));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

}
