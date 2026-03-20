package mx.com.endtoend.application.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.strategy.ports.api.StrategyServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/strategy")
public class StrategyController {
	
	@Autowired
	private StrategyServicePort strategyServicePort;
	
	private final Logger LOG = LoggerFactory.getLogger(StrategyController.class);

	
	@GetMapping("/list")
	public ResponseEntity<ResponseModel> strategyList(){
		LOG.info("ACTION: STRATEGY CLIENT NAME");
		ResponseModel responseModel = strategyServicePort.strategyList();
		return ResponseEntity.status(HttpStatus.OK)
				.body(responseModel);
		
	}
}
