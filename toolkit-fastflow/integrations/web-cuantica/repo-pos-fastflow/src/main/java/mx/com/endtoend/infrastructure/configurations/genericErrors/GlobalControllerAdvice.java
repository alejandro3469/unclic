package mx.com.endtoend.infrastructure.configurations.genericErrors;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mx.com.endtoend.domain.commons.constants.Constants;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.SemiFullFunction;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	private final Logger LOG = LoggerFactory.getLogger(GlobalControllerAdvice.class);

	@ExceptionHandler({ ValidationError.class })
	public ResponseEntity<ResponseModel> validationError(Exception ex) {
		List<String> list = new ArrayList<String>();
		return this.buildErrorresponseEntity(ex.getMessage(), Constants.VALIDATION_ERROR, list);
	}

	@ExceptionHandler({ SemiFullFunction.class })
	public ResponseEntity<ResponseModel> semiFullFunction(SemiFullFunction ex) {
		return this.buildErrorresponseEntity(ex.getMessageError(), Constants.SEMI_FULL_FUNCTION, ex.getData());
	}

	@ExceptionHandler(GlobalError.class)
	public ResponseEntity<ResponseModel> globalError(Exception ex) {
		return this.buildErrorresponseEntity(ex.getMessage(), Constants.GLOBAL_EXCEPTION, "");
	}

	private ResponseEntity<ResponseModel> buildErrorresponseEntity(String field, int responseCode, Object data) {
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(field, responseCode, data));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> requestExceptionHandler(Exception e) {
		ResponseModel responseModel = new ResponseModel();

		LOG.info("INVALID REQUEST: CAUSE -> " + e.getMessage() + " " + e.getLocalizedMessage());

		responseModel.setData(null);
		responseModel.setField(e.getMessage());
		responseModel.setResponseCode(400);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

}