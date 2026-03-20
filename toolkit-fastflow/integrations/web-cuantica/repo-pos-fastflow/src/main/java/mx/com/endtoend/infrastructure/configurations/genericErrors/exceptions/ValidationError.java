package mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions;

public class ValidationError extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public ValidationError(String message) {
        super(message);
    }

}