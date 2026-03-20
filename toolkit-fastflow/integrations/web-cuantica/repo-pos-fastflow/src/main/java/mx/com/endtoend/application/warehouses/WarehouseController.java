package mx.com.endtoend.application.warehouses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.warehouses.ports.api.WarehouseServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

	@Autowired
	private WarehouseServicePort warehouseServicePort;
	
	private final Logger LOG = LoggerFactory.getLogger(WarehouseController.class);
	
	@GetMapping("/view/{companyCode}")
	public ResponseEntity<?> getWarehouseListByCompanyCode(@PathVariable String companyCode){
		
		LOG.info(String.format(" INIT getWarehouseListByCompanyCode()"));
		LOG.info(String.format(" PARAMS: [ companyCode: %s ]", companyCode));
		
		ResponseModel responseModel = warehouseServicePort.findAllByCompanyCode(companyCode);
		return new ResponseEntity<>(responseModel,HttpStatus.OK);	
	}
}
