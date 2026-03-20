package mx.com.endtoend.application.validService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.validService.services.StatusSystemServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/status")
public class StatusController implements HealthIndicator{

	@Value("${app.version}")
	String version;

	@Value("${environment.active}")
	String environment;

	@Value("${eureka.instance.instance-id}")
	String eurekaInstance;

	@Autowired
	private StatusSystemServicePort statusSystemServicePort;
	
	private String statusApp = "UP";

	@GetMapping("/aplication-layer")
	public ResponseEntity<?> validStatusApplication() {

		String response = String.format(
				"\n Service Active \n Version: %s , \n Environment: %s , \n Date: %s \n Eureka Instance: %s", version,
				environment, new Date(), eurekaInstance);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@GetMapping("/connection")
	public ResponseEntity<?> validStatusDBApplication() {
		ResponseModel response = statusSystemServicePort.getStatusDBConnection();
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
	}
	
	@GetMapping("/up")
	public ResponseEntity<?> changeStatusUp() {
		statusApp = "UP";
		health();
		return new ResponseEntity<String>(statusApp, HttpStatus.OK);
	}
	
	@GetMapping("/down")
	public ResponseEntity<?> changeStatusDown() {
		statusApp = "DOWN";
		health();
		return new ResponseEntity<String>(statusApp, HttpStatus.OK);
	}
	
	@Override
	public Health health() {
		Health.Builder status = Health.up();
		if (statusApp.equals("DOWN")) {
			status = Health.down();
		}
		return status.build();
	}
}
